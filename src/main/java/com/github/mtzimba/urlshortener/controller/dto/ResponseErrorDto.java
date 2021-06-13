package com.github.mtzimba.urlshortener.controller.dto;

import com.github.mtzimba.urlshortener.controller.ErrorEnum;

public class ResponseErrorDto {

	private String alias;
	private String err_code;
	private String description;
	
	public ResponseErrorDto(String alias, ErrorEnum erro) {
		this.alias = alias;
		this.err_code = erro.getCodigo();
		this.description = erro.getDescricao();
	}

	public String getAlias() {
		return alias;
	}

	public String getErr_code() {
		return err_code;
	}

	public String getDescription() {
		return description;
	}
	
}
