package com.autobots.automanager.dto;

import java.util.Set;

public record DadosServicoPorEmpresa(
		DadosListagemEmpresa empresa,
		Set<DadosServico> servicos) {
}
