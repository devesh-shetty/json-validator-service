package com.devesh.shetty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.devesh.shetty.dao.ISchemaDao;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;
import com.devesh.shetty.util.Constant;

@Component
public class SchemaDetailService implements ISchemaDetailService{

  @Autowired
  private ISchemaDao schemaDao;
  
  @Override 
  public Schema fetchSchemaBySchemaId(String schemaId){
    return schemaDao.load(schemaId);
  }

  @Override
  public ActionResponse saveSchema(Schema schema) {
    schemaDao.save(schema);
    return new ActionResponse(Constant.ACTION_UPLOAD, schema.getFileName(), HttpStatus.OK);
  }
  
  
  
}
