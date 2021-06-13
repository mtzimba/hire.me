package com.github.mtzimba.urlshortener.controller;

/**
 * @author Matheus Cardoso
 */
public enum ErrorEnum {

	CUSTOM_ALIAS_ALREADY_EXISTS("001", "CUSTOM ALIAS ALREADY EXISTS"),
	SHORTENED_URL_NOT_FOUND("002", "SHORTENED URL NOT FOUND"),
	INVALID_URL("003", "INVALID URL");
	
	private String codigo;
	private String descricao;
	
	private ErrorEnum(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
