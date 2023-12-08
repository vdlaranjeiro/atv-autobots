package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Telefone;

@Component
public class AtualizadorTelefone {
	
	public void atualizar(Telefone telefone, Telefone atualizacao) {
		if (atualizacao != null) {
			if (atualizacao.getDdd() != null) {
				telefone.setDdd(atualizacao.getDdd());
			}
			if (atualizacao.getNumero() != null) {
				telefone.setNumero(atualizacao.getNumero());
			}
		}
	}
}
