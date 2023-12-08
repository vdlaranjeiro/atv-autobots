package com.autobots.automanager.dto;

import java.util.Date;
import java.util.Set;

public record DadosCadastroVenda(
		Date cadastro,
		Long idEmpresa,
		Long idCliente,
		Long idFuncionario,
		Set<Long> idsMercadorias,
		Set<Long> idsServicos,
		Long idVeiculo) {
}
