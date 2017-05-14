package com.devesh.shetty.model;

import java.io.Serializable;

public class Schema implements Serializable {

  private static final long serialVersionUID = 909090909212L;

  private byte[] fileData;

  private String fileName;

  public Schema(byte[] fileData, String fileName) {
    super();
    this.fileData = fileData;
    this.fileName = fileName;
  }

  public Schema(String schemaId) {
    this.fileName = schemaId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public byte[] getFileData() {
    return fileData;
  }

  public void setFileData(byte[] fileData) {
    this.fileData = fileData;
  }

}
