package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Mercadoria;

@Component
public class AtualizadorMercadoria {
	
	public void atualizarMercadoria(Mercadoria mercadoria, Mercadoria mercadoriaAtualizacao) {
		if(mercadoriaAtualizacao.getNome() != null) {
			mercadoria.setNome(mercadoriaAtualizacao.getNome());
		}
		if(mercadoriaAtualizacao.getDescricao() != null) {
			mercadoria.setDescricao(mercadoriaAtualizacao.getDescricao());
		}
		if(mercadoriaAtualizacao.getValor() != null) {
			mercadoria.setValor(mercadoriaAtualizacao.getValor());
		}
		if(mercadoriaAtualizacao.getFabricacao() != null) {
			mercadoria.setFabricacao(mercadoriaAtualizacao.getFabricacao());
		}
		if(mercadoriaAtualizacao.getValidade() != null) {
			mercadoria.setValidade(mercadoriaAtualizacao.getValidade());
		}
		mercadoria.setOriginal(mercadoriaAtualizacao.getOriginal());
	}
}
