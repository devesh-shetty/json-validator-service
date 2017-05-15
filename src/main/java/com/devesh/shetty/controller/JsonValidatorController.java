package com.devesh.shetty.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devesh.shetty.exception.BusinessException;
import com.devesh.shetty.exception.NoSuchSchemaException;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;
import com.devesh.shetty.service.ISchemaDetailService;
import com.devesh.shetty.util.Constant;

@RestController
public class JsonValidatorController {

  private static final Logger LOG = Logger.getLogger(JsonValidatorController.class);

  @Autowired
  private ISchemaDetailService schemaDetailService;

  /**
   * Return the json mapped by schemaId
   * 
   * @param schemaId
   * @return resource
   */
  @GetMapping("/schema/{schemaId}")
  public ResponseEntity<?> fetchSchemaBySchemaId(@PathVariable String schemaId) {
    Schema schema = null;
    try {
      schema = schemaDetailService.fetchSchemaBySchemaId(schemaId);
      
    } catch (BusinessException e) {
      String message = "Error while retrieving  schema with id: " + schemaId;
      LOG.error(message, e);
      return new ResponseEntity<String>("Unable to fetch the file", HttpStatus.INTERNAL_SERVER_ERROR);
      
    } catch (NoSuchSchemaException e) {
      String message = "Schema not found: " + schemaId;
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constant.ACTION_FETCH, schemaId, Constant.STATUS_ERROR,
          "Schema does not exist");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.NOT_FOUND);
      
    }

    // return the resource stream of the json
    ByteArrayResource resource = new ByteArrayResource(schema.getFileData());
    return ResponseEntity.ok().contentLength(schema.getFileData().length)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

  }

  @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.PUT)
  public ResponseEntity<ActionResponse> handleFileUpload(@PathVariable String schemaId,
      @RequestParam(value = "file", required = true) MultipartFile file) {

    Schema schema = null;
    try {
      schema = new Schema(file.getBytes(), schemaId);
    } catch (IOException e) {
      LOG.error("Failed to access the file", e);
    }

    try {
      return new ResponseEntity<ActionResponse>(getSchemaDetailService().saveSchema(schema), HttpStatus.OK);
    } catch (BusinessException e) {
      LOG.error("Failed to save the file", e);
      ActionResponse actionResponse = new ActionResponse(Constant.ACTION_UPLOAD, schemaId, Constant.STATUS_ERROR,
          "Failed to save the file");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

  }

  public ISchemaDetailService getSchemaDetailService() {
    return schemaDetailService;
  }

  public void setSchemaDetailService(ISchemaDetailService schemaDetailService) {
    this.schemaDetailService = schemaDetailService;
  }
}
