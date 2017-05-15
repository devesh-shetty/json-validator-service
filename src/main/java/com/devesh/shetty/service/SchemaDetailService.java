package com.devesh.shetty.service;

import java.nio.file.NoSuchFileException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devesh.shetty.dao.ISchemaDao;
import com.devesh.shetty.exception.BusinessException;
import com.devesh.shetty.exception.DataAccessException;
import com.devesh.shetty.exception.NoSuchSchemaException;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;
import com.devesh.shetty.util.Constant;

@Component
public class SchemaDetailService implements ISchemaDetailService{

  public static final Logger LOG = Logger.getLogger(SchemaDetailService.class);

  @Autowired
  private ISchemaDao schemaDao;
  
  @Override 
  public Schema fetchSchemaBySchemaId(String schemaId) throws BusinessException, NoSuchSchemaException{
    try {
      return schemaDao.load(schemaId);
    } 
    catch (NoSuchFileException e){
      String message = "schema with id: " + schemaId + " does not exist";
      LOG.error(message, e);
      throw new NoSuchSchemaException(message);
    }
    catch (DataAccessException e) {
      String message = "Error while retrieving  schema with id: " + schemaId;
      LOG.error(message, e);
      throw new BusinessException(message);
    }
  }

  @Override
  public ActionResponse saveSchema(Schema schema) throws BusinessException {
    try {
      schemaDao.save(schema);
      return new ActionResponse(Constant.ACTION_UPLOAD, schema.getFileName(), Constant.STATUS_SUCCESS);
    } catch (DataAccessException e) {
      LOG.error("Unable to save the schema", e);
      throw new BusinessException(e.getMessage());
    }
  }
  
  
  
}
