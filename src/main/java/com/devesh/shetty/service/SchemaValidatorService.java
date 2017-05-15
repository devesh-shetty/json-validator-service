package com.devesh.shetty.service;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devesh.shetty.dao.ISchemaDao;
import com.devesh.shetty.exception.BusinessException;
import com.devesh.shetty.exception.DataAccessException;
import com.devesh.shetty.exception.InvalidDataException;
import com.devesh.shetty.exception.InvalidJsonException;
import com.devesh.shetty.exception.NoSuchSchemaException;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;
import com.devesh.shetty.util.Constants;
import com.devesh.shetty.util.ValidationUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

@Component
public class SchemaValidatorService implements ISchemaValidatorService {

  public static final Logger LOG = Logger.getLogger(SchemaValidatorService.class);

  @Autowired
  private ISchemaDao schemaDao;

  @Override
  public Schema fetchSchemaBySchemaId(String schemaId) throws BusinessException, NoSuchSchemaException {
    try {
      return schemaDao.load(schemaId);

    } catch (NoSuchFileException e) {
      String message = "schema with id: " + schemaId + " does not exist";
      LOG.error(message, e);
      throw new NoSuchSchemaException(message);

    } catch (DataAccessException e) {
      String message = "Error while retrieving  schema with id: " + schemaId;
      LOG.error(message, e);
      throw new BusinessException(message);

    }
  }

  @Override
  public ActionResponse saveSchema(Schema schema)
      throws BusinessException, NoSuchSchemaException, InvalidJsonException {
    try {
      schemaDao.save(schema);
      return new ActionResponse(Constants.ACTION_UPLOAD, schema.getSchemaId(), Constants.STATUS_SUCCESS);

    } catch (NoSuchFileException e) {
      String message = "schema with id: " + schema.getSchemaId() + " failed to be stored";
      LOG.error(message, e);
      throw new NoSuchSchemaException(message);

    } catch (DataAccessException e) {
      LOG.error("Unable to save the schema", e);
      throw new BusinessException(e.getMessage());

    } catch (InvalidDataException e) {
      LOG.error("Invalid JSON file received", e);
      throw new InvalidJsonException(e.getMessage());

    }
  }

  @Override
  public ActionResponse validateSchema(String schemaId, Schema jsonDocumentToBeValidated)
      throws BusinessException, InvalidJsonException, IOException, ProcessingException {

    saveSchema(jsonDocumentToBeValidated);
    final String schemaPath = schemaDao.getDocumentPath(schemaId);
    final String documentToBeValidatedPath = schemaDao.getDocumentPath(jsonDocumentToBeValidated.getSchemaId());

    final JsonNode loadedSchema = ValidationUtils.loadResource(schemaPath);
    final JsonNode schemaToBeChecked = ValidationUtils.loadResource(documentToBeValidatedPath);

    final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

    final JsonSchema schema = factory.getJsonSchema(loadedSchema);

    ProcessingReport report = schema.validate(schemaToBeChecked);

    ActionResponse response = null;

    if (report.isSuccess()) {
      response = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_SUCCESS);
    } else {

      String msg = "";
      for (final ProcessingMessage reportLine : report) {
        msg += reportLine.toString() + "\n";
      }
      response = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_ERROR, msg);
    }

    return response;
  }

}
