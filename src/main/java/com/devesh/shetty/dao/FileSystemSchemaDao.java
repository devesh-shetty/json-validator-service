package com.devesh.shetty.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.devesh.shetty.exception.DataAccessException;
import com.devesh.shetty.exception.InvalidDataException;
import com.devesh.shetty.model.Schema;
import com.devesh.shetty.util.Constants;
import com.devesh.shetty.util.ValidationUtils;

@Component
public class FileSystemSchemaDao implements ISchemaDao {

  public static final Logger LOG = Logger.getLogger(FileSystemSchemaDao.class);

  @PostConstruct
  public void init() {
    createDirectory(Constants.SCHEMA_DIRECTORY);
  }

  @Override
  public Schema save(Schema schema) throws DataAccessException, NoSuchFileException, InvalidDataException {
    return saveFileData(schema);
  }

  private Schema saveFileData(Schema schema) throws DataAccessException, NoSuchFileException, InvalidDataException {
    String path = getDirectoryPath(schema);
    // BufferedOutputStream stream = null;
    try (BufferedOutputStream stream = new BufferedOutputStream(
        new FileOutputStream(new File(new File(path), schema.getSchemaId() + ".json")))) {

      stream.write(schema.getFileData());
      
    } catch (IOException e) {
      LOG.error("Error in performing operations on the file", e);
      throw new DataAccessException(e.getMessage());
      
    }
    
    Schema storedSchema = loadFromFileSystem(schema.getSchemaId());
    try {
      ValidationUtils.isJSONValid(storedSchema.getFileData());
      return storedSchema;
    } catch (IOException e) {
      LOG.error("Invalid data received", e);
      throw new InvalidDataException(e.getMessage());
    }

  }
  
  

  @Override
  public Schema load(String schemaId) throws DataAccessException, NoSuchFileException {
    return loadFromFileSystem(schemaId);
  }

  private Schema loadFromFileSystem(String schemaId) throws DataAccessException, NoSuchFileException {
    Path path = Paths.get(getFilePath(schemaId));
    Schema schema = new Schema(schemaId);

    try {
      schema.setFileData(Files.readAllBytes(path));
      return schema;

    } catch (NoSuchFileException e) {
      String message = "schema with id: " + schemaId + " does not exist";
      LOG.error(message, e);
      throw e;

    } catch (IOException e) {
      String message = "Error while loading schema with id: " + schemaId;
      LOG.error(message, e);
      throw new DataAccessException(message);
      
    }

  }

  @Override
  public String getDocumentPath(String schemaId){
    return getFilePath(schemaId);
  }
  
  private String getFilePath(String schemaId) {
    String dirPath = getDirectoryPath(schemaId);
    StringBuilder sb = new StringBuilder();
    sb.append(dirPath).append(File.separator).append(schemaId + ".json");
    return sb.toString();
  }

  private String createDirectory(Schema schema) {
    String path = getDirectoryPath(schema);
    createDirectory(path);
    return path;
  }

  private String getDirectoryPath(Schema schema) {
    return getDirectoryPath(schema.getSchemaId());
  }

  private String getDirectoryPath(String fileName) {
    StringBuilder sb = new StringBuilder();
    // sb.append(Constant.SCHEMA_DIRECTORY).append(File.separator).append(fileName);
    sb.append(Constants.SCHEMA_DIRECTORY);
    String path = sb.toString();
    return path;
  }

  private void createDirectory(String path) {
    File file = new File(path);
    file.mkdirs();
  }

}
