package com.devesh.shetty.exception;

import java.nio.file.NoSuchFileException;

public class NoSuchSchemaException extends NoSuchFileException {

  private static final long serialVersionUID = 3070341789123408495L;

  public NoSuchSchemaException(String file) {
    super(file);
    
  }

}
