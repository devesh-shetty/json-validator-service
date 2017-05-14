package com.devesh.shetty.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.devesh.shetty.util.Constant;

/**
 *An entity corresponding to {@link Constant}.SCHEMA_DETAIL_TABLE_NAME 
 * 
 * @author deveshshetty
 */
@Entity
@Table(name = Constant.SCHEMA_DETAIL_TABLE_NAME)
public class SchemaDetail implements Serializable{

  private static final long serialVersionUID = 90909090L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @Column(name = Constant.SCHEMA_ID_COLUMN_NAME)
  private String schemaId;
  
  @Column(name = Constant.SCHEMA_PATH_COULMN_NAME)
  private String schemaPath;
  
  
  
}
