package com.devesh.shetty.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;

public final class ValidationUtils {

  private ValidationUtils(){}
  
  public static void isJSONValid(byte[] jsonData) throws JsonProcessingException, IOException {
    final ObjectMapper mapper = new ObjectMapper();
    mapper.readTree(jsonData);

  }
  
  /**
   * Load one resource from the given path as a {@link JsonNode}
   * @param jsonPath path of the resource
   * @return a JSON document
   * @throws IOException when resource is not found
   */
  public static JsonNode loadResource(String documentPath) throws IOException{
    return JsonLoader.fromPath(documentPath);
  }
  

}