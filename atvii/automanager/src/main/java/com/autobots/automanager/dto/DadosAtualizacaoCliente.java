package com.autobots.automanager.dto;

import java.util.Date;

public record DadosAtualizacaoCliente(
		Long id,
		String nome,
		String nomeSocial,
		Date dataNascimento) {

}