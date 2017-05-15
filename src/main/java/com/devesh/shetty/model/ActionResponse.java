package com.devesh.shetty.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ActionResponse implements Serializable {

  private static final long serialVersionUID = 909090909212L;

  private String action;
  
  private String id;
  
  private String status;
  
  private String message;

  public ActionResponse(String action, String id, String status) {
    super();
    this.action = action;
    this.id = id;
    this.status = status;
  }

  public ActionResponse(String action, String id, String status, String message) {
    super();
    this.action = action;
    this.id = id;
    this.status = status;
    this.message = message;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  
  
}
