package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Documento;

@Component
public class AtualizadorDocumento {
	
	public Documento atualizar(Documento documento, Documento documentoAtualizacao) {
		if(documentoAtualizacao.getTipo() != null) {
			documento.setTipo(documentoAtualizacao.getTipo());
		}
		if(documentoAtualizacao.getNumero() != null) {
			documento.setNumero(documentoAtualizacao.getNumero());
		}
		if(documentoAtualizacao.getDataEmissao() != null) {
			documento.setDataEmissao(documentoAtualizacao.getDataEmissao());
		}
		return documento;
	}
}
