package com.devesh.shetty.dao;

import java.nio.file.NoSuchFileException;

import com.devesh.shetty.exception.DataAccessException;
import com.devesh.shetty.exception.InvalidDataException;
import com.devesh.shetty.model.Schema;

public interface ISchemaDao {

  public Schema save(Schema schema) throws DataAccessException, NoSuchFileException, InvalidDataException;

  Schema load(String schemaId) throws DataAccessException, NoSuchFileException;

  String getDocumentPath(String schemaId);
  
}
