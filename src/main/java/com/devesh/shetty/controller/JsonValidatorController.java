package com.devesh.shetty.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.devesh.shetty.model.Schema;
import com.devesh.shetty.service.ISchemaDetailService;

@RestController
public class JsonValidatorController {

  private static final Logger LOG = Logger.getLogger(JsonValidatorController.class);

  @Autowired
  private ISchemaDetailService schemaDetailService;

  /**
   * Return the schema mapped by schemaId
   * 
   * @param schemaId
   * @return schema
   */
  @GetMapping("/schema/{schemaId}")
  public Schema fetchSchemaBySchemaId(@PathVariable String schemaId) {
    return schemaDetailService.fetchSchemaBySchemaId(schemaId);
  }

  @RequestMapping(value = "/schema/{schemaId}", method = RequestMethod.PUT)
  public ResponseEntity<?> handleFileUpload(@PathVariable String schemaId, @RequestParam(value = "file", required = true) MultipartFile file) {
    try {
      Schema schema = new Schema(file.getBytes(), schemaId);
      getSchemaDetailService().saveSchema(schema);
      return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);
    } catch (RuntimeException e) {
      LOG.error("Error while uploading.", e);
      throw e;
    } catch (Exception e) {
      LOG.error("Error while uploading.", e);
      throw new RuntimeException(e);
    }
  }

  public ISchemaDetailService getSchemaDetailService() {
    return schemaDetailService;
  }

  public void setSchemaDetailService(ISchemaDetailService schemaDetailService) {
    this.schemaDetailService = schemaDetailService;
  }
}
