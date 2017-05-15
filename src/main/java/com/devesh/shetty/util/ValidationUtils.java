package com.devesh.shetty.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ValidationUtils {

  public static void isJSONValid(byte[] jsonData) throws JsonProcessingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.readTree(jsonData);

  }

}