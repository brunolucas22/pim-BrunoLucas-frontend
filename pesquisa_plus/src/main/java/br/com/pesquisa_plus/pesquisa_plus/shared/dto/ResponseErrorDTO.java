package br.com.pesquisa_plus.pesquisa_plus.shared.dto;

import java.util.Date;


public class ResponseErrorDTO {
	
	private int statusCode;

	private String type;

	private Date timestamp;

	private String message;

	private String description;

	public ResponseErrorDTO(int statusCode, String type, Date timestamp, String message, String description) {
		this.statusCode = statusCode;
		this.type = type;
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getType() {
		return type;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

}