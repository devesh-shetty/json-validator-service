package com.devesh.shetty.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.devesh.shetty.model.Schema;
import com.devesh.shetty.util.Constant;

@Component
public class FileSystemSchemaDao implements ISchemaDao {

  @PostConstruct
  public void init() {
    createDirectory(Constant.SCHEMA_DIRECTORY);
  }

  @Override
  public String save(Schema schema) {
    try {
      saveFileData(schema);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return "yes";
  }

  private void saveFileData(Schema schema) throws IOException {
    String path = getDirectoryPath(schema);
    BufferedOutputStream stream = new BufferedOutputStream(
        new FileOutputStream(new File(new File(path), schema.getFileName() + ".json")));
    stream.write(schema.getFileData());
    stream.close();
  }

  @Override
  public Schema load(String schemaId) {
    try {
      return loadFromFileSystem(schemaId);
    } catch (IOException e) {
      String message = "Error while loading document with id: " + schemaId;
      // LOG.error(message, e);
      throw new RuntimeException(message, e);
    }

  }

  private Schema loadFromFileSystem(String schemaId) throws IOException {
    Path path = Paths.get(getFilePath(schemaId));
    Schema schema = new Schema(schemaId);
    schema.setFileData(Files.readAllBytes(path));
    return schema;
  }

  private String getFilePath(String schemaId) {
    String dirPath = getDirectoryPath(schemaId);
    StringBuilder sb = new StringBuilder();
    sb.append(dirPath).append(File.separator).append(schemaId+".json");
    return sb.toString();
  }

  private String createDirectory(Schema schema) {
    String path = getDirectoryPath(schema);
    createDirectory(path);
    return path;
  }

  private String getDirectoryPath(Schema schema) {
    return getDirectoryPath(schema.getFileName());
  }

  private String getDirectoryPath(String fileName) {
    StringBuilder sb = new StringBuilder();
    // sb.append(Constant.SCHEMA_DIRECTORY).append(File.separator).append(fileName);
    sb.append(Constant.SCHEMA_DIRECTORY);
    String path = sb.toString();
    return path;
  }

  private void createDirectory(String path) {
    File file = new File(path);
    file.mkdirs();
  }

}
