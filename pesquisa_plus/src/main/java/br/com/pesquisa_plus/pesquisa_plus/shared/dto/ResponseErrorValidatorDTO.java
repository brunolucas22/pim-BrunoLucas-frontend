package br.com.pesquisa_plus.pesquisa_plus.shared.dto;

import java.util.Date;
import java.util.List;

public class ResponseErrorValidatorDTO {
  private int statusCode;
  private Date timestamp;
  private List<String> list;


  public ResponseErrorValidatorDTO(int statusCode, Date timestamp, List<String> list) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.list = list;
  }


public int getStatusCode() {
	return statusCode;
}


public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
}


public Date getTimestamp() {
	return timestamp;
}


public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
}


public List<String> getList() {
	return list;
}


public void setList(List<String> list) {
	this.list = list;
}


 
}