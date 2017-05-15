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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devesh.shetty.exception.BusinessException;
import com.devesh.shetty.exception.InvalidJsonException;
import com.devesh.shetty.exception.NoSuchSchemaException;
import com.devesh.shetty.model.ActionResponse;
import com.devesh.shetty.model.Schema;
import com.devesh.shetty.service.ISchemaValidatorService;
import com.devesh.shetty.util.Constants;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;

@RestController
public class JsonValidatorController {

  private static final Logger LOG = Logger.getLogger(JsonValidatorController.class);

  @Autowired
  private ISchemaValidatorService schemaDetailService;

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
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_FETCH, schemaId, Constants.STATUS_ERROR,
          "Schema does not exist");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.NOT_FOUND);

    }

    // return the resource stream of the json
    ByteArrayResource resource = new ByteArrayResource(schema.getFileData());
    return ResponseEntity.ok().contentLength(schema.getFileData().length)
        .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);

  }

  @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.PUT)
  public ResponseEntity<ActionResponse> handleSchemaUpload(@PathVariable String schemaId,
      @RequestParam(value = "file", required = true) MultipartFile file) {

    Schema schema = null;
    try {
      schema = new Schema(file.getBytes(), schemaId);

    } catch (IOException e) {
      LOG.error("Failed to access the file", e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_UPLOAD, schemaId, Constants.STATUS_ERROR,
          "Failed to access the file");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.BAD_REQUEST);
      
    }

    try {
      return new ResponseEntity<ActionResponse>(getSchemaDetailService().saveSchema(schema), HttpStatus.OK);

    } catch (BusinessException e) {
      LOG.error("Failed to save the file", e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_UPLOAD, schemaId, Constants.STATUS_ERROR,
          "Failed to save the file");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.SERVICE_UNAVAILABLE);

    } catch (NoSuchSchemaException e) {
      String message = "Schema not stored: " + schemaId;
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_FETCH, schemaId, Constants.STATUS_ERROR,
          "Schema failed to be stored");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.NOT_FOUND);

    } catch (InvalidJsonException e) {
      String message = "Invalid JSON recieved for schemaId: " + schemaId;
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_FETCH, schemaId, Constants.STATUS_ERROR,
          "Invalid JSON");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.BAD_REQUEST);

    }
     
  }

  @RequestMapping(value = "/validate/{schemaId}", method = RequestMethod.PUT)
  public ResponseEntity<ActionResponse> checkSchema(@PathVariable String schemaId,
      @RequestParam(value = "file", required = true) MultipartFile file) {

    Schema schema = null;
    try {
      schema = new Schema(file.getBytes(), "validate-document");

    } catch (IOException e) {
      LOG.error("Failed to access the file", e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_UPLOAD, schemaId, Constants.STATUS_ERROR,
          "Failed to access the file");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.BAD_REQUEST);
      
    }

    try {
      return new ResponseEntity<ActionResponse>(getSchemaDetailService().validateSchema(schemaId, schema),
          HttpStatus.OK);

    } catch (BusinessException e) {
      LOG.error("Failed to save the file", e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_ERROR,
          "Failed to save the file");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.SERVICE_UNAVAILABLE);

    } catch (NoSuchSchemaException e) {
      String message = "Schema not stored: " + schemaId;
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_ERROR,
          "Schema failed to be stored");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.NOT_FOUND);

    } catch (InvalidJsonException e) {
      String message = "Invalid JSON recieved for schemaId: " + schemaId;
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_ERROR,
          "Invalid JSON");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.BAD_REQUEST);

    } catch (IOException e) {
      String message = "Failed to retrieve JSON schema with : " + schemaId;
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_ERROR,
          "Error occured");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.BAD_REQUEST);
      
    } catch (ProcessingException e) {
      String message = "Invalid JSON recieved";
      LOG.error(message, e);
      ActionResponse actionResponse = new ActionResponse(Constants.ACTION_VALIDATE, schemaId, Constants.STATUS_ERROR,
          "Invalid JSON");
      return new ResponseEntity<ActionResponse>(actionResponse, HttpStatus.BAD_REQUEST);
      
    }

  }
  

  public ISchemaValidatorService getSchemaDetailService() {
    return schemaDetailService;
  }

  public void setSchemaDetailService(ISchemaValidatorService schemaDetailService) {
    this.schemaDetailService = schemaDetailService;
  }
}
