package com.github.mtzimba.urlshortener.controller;

/**
 * @author Matheus Cardoso
 */
public enum ErroEnum {

	CUSTOM_ALIAS_ALREADY_EXISTS("001", "CUSTOM ALIAS ALREADY EXISTS"),
	SHORTENED_URL_NOT_FOUND("002", "SHORTENED URL NOT FOUND");
	
	private String codigo;
	private String descricao;
	
	private ErroEnum(String codigo, String descricao) {
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
