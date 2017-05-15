package com.devesh.shetty.service;

import com.devesh.shetty.exception.BusinessException;
import com.devesh.shetty.exception.InvalidJsonException;
import com.devesh.shetty.exception.NoSuchSchemaException;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;

public interface ISchemaDetailService {

  Schema fetchSchemaBySchemaId(String schemaId) throws BusinessException, NoSuchSchemaException;
  
  ActionResponse saveSchema(Schema schema) throws BusinessException, NoSuchSchemaException, InvalidJsonException;
}
