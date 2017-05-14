package com.devesh.shetty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devesh.shetty.dao.ISchemaDao;
import com.devesh.shetty.model.Schema;

@Component
public class SchemaDetailService implements ISchemaDetailService{

  @Autowired
  private ISchemaDao schemaDao;
  
  @Override 
  public Schema fetchSchemaBySchemaId(String schemaId){
    return schemaDao.load(schemaId);
  }

  @Override
  public String saveSchema(Schema schema) {
    return schemaDao.save(schema);
  }
  
  
  
}
