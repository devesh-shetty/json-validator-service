# JSON Validator Service

JSON Validator Service is a REST-service for validating JSON documents against JSON Schemas.

## Initial setup

Add the following dependencies to pom.xml

```
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <scope>runtime</scope>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
<dependency>
  <groupId>com.github.fge</groupId>
  <artifactId>json-schema-validator</artifactId>
  <version>2.2.6</version>
</dependency>
 ```

Run maven
```
mvn clean install
```

## Usage

* PUT /schema/SCHEMAID - Upload JSON Schema with unique SCHEMAID

```
curl -F file=@"config-schema.json" http://localhost:8080/schema/document -XPUT
```
