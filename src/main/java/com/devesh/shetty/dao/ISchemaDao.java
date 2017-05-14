package com.devesh.shetty.dao;

import com.devesh.shetty.model.Schema;

public interface ISchemaDao {

  public String save(Schema schema);

  Schema load(String schemaId);
  
}
