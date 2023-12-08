package com.autobots.automanager.dto;

import com.autobots.automanager.enumeracoes.TipoVeiculo;

public record DadosAtualizacaoVeiculo (
		Long id,
		TipoVeiculo tipo,
		String modelo,
		String placa) {

}
