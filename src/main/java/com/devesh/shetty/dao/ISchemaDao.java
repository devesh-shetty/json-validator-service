package com.devesh.shetty.dao;

import java.nio.file.NoSuchFileException;

import com.devesh.shetty.exception.DataAccessException;
import com.devesh.shetty.model.Schema;

public interface ISchemaDao {

  public void save(Schema schema) throws DataAccessException;

  Schema load(String schemaId) throws DataAccessException, NoSuchFileException;
  
}
