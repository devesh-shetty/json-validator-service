package com.devesh.shetty.service;

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

@Component
public class SchemaDetailService implements ISchemaDetailService{

  public static final Logger LOG = Logger.getLogger(SchemaDetailService.class);

  @Autowired
  private ISchemaDao schemaDao;
  
  @Override 
  public Schema fetchSchemaBySchemaId(String schemaId) throws BusinessException, NoSuchSchemaException{
    try {
      return schemaDao.load(schemaId);
      
    } catch (NoSuchFileException e){
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
  public ActionResponse saveSchema(Schema schema) throws BusinessException, NoSuchSchemaException, InvalidJsonException {
    try {
      schemaDao.save(schema);
      return new ActionResponse(Constants.ACTION_UPLOAD, schema.getSchemaId(), Constants.STATUS_SUCCESS);
      
    } catch (NoSuchFileException e){
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
  
  
  
}
