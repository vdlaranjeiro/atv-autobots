package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Servico;

@Component
public class AtualizadorServico {
	
	public void atualizarServico(Servico servico, Servico servicoAtualizacao) {
		if(servicoAtualizacao.getNome() != null) {
			servico.setNome(servicoAtualizacao.getNome());
		}
		if(servicoAtualizacao.getDescricao() != null) {
			servico.setDescricao(servicoAtualizacao.getDescricao());
		}
		if(servicoAtualizacao.getValor() != null) {
			servico.setValor(servicoAtualizacao.getValor());
		}
	}
}
