package com.autobots.automanager.dto;

import java.util.Set;

public record DadosMercadoriaPorEmpresa(
		DadosListagemEmpresa empresa,
		Set<DadosMercadoria> mercadorias) {
}
