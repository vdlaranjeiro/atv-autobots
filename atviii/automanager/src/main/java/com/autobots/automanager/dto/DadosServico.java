package com.autobots.automanager.dto;

public record DadosServico(
		Long id,
		String nome,
		String descricao,
		Double valor,
		boolean original) {
}
