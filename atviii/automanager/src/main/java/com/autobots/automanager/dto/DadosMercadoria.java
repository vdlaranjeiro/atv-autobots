package com.autobots.automanager.dto;

import java.util.Date;

public record DadosMercadoria(
		Long id,
		String nome,
		String descricao,
		Double valor,
		Date cadastro,
		Date fabricacao,
		Date validade,
		boolean original) {

}
