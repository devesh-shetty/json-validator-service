package com.devesh.shetty.model;

import java.io.Serializable;

public class Schema implements Serializable {

  private static final long serialVersionUID = 909090909212L;

  private byte[] fileData;

  private String schemaId;

  public Schema(byte[] fileData, String schemaId) {
    super();
    this.fileData = fileData;
    this.schemaId = schemaId;
  }

  public Schema(String schemaId) {
    this.schemaId = schemaId;
  }

  public String getSchemaId() {
    return schemaId;
  }

  public void setSchemaId(String schemaId) {
    this.schemaId = schemaId;
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

}
