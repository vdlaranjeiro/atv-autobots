package com.autobots.automanager.dto;

import java.util.Set;

public record DadosMercadoriaPorFornecedor(
		DadosUsuario fornecedor,
		Set<DadosMercadoria> mercadorias) {
}
