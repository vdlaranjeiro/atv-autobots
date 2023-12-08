package com.autobots.automanager.dto;

import java.util.Set;

public record DadosVeiculosPorUsuario(
		DadosUsuario cliente,
		Set<DadosVeiculo> veiculos) {
}
