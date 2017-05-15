package com.devesh.shetty.service;

import java.io.IOException;

import com.devesh.shetty.exception.BusinessException;
import com.devesh.shetty.exception.InvalidJsonException;
import com.devesh.shetty.exception.NoSuchSchemaException;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

public interface ISchemaValidatorService {

  Schema fetchSchemaBySchemaId(String schemaId) throws BusinessException, NoSuchSchemaException;
  
  ActionResponse saveSchema(Schema schema) throws BusinessException, NoSuchSchemaException, InvalidJsonException;

  ActionResponse validateSchema(String schemaId, Schema jsonDocumentToBeValidated)
      throws BusinessException, InvalidJsonException, IOException, ProcessingException;
  
}
