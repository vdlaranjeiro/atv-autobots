package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Endereco;

@Component
public class AtualizadorEndereco {
	public void atualizar(Endereco endereco, Endereco atualizacao) {
		if (atualizacao != null) {
			if (atualizacao.getEstado() != null) {
				endereco.setEstado(atualizacao.getEstado());
			}
			if (atualizacao.getCidade() != null) {
				endereco.setCidade(atualizacao.getCidade());
			}
			if (atualizacao.getBairro() != null) {
				endereco.setBairro(atualizacao.getBairro());
			}
			if (atualizacao.getRua() != null) {
				endereco.setRua(atualizacao.getRua());
			}
			if (atualizacao.getNumero() != null) {
				endereco.setNumero(atualizacao.getNumero());
			}
			if (atualizacao.getInformacoesAdicionais() != null) {
				endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
			}
		}
	}
}
