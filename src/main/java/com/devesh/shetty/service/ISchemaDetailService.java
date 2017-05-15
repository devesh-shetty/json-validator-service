package com.devesh.shetty.service;

import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;

public interface ISchemaDetailService {

  Schema fetchSchemaBySchemaId(String schemaId);
  
  ActionResponse saveSchema(Schema schema);
}
