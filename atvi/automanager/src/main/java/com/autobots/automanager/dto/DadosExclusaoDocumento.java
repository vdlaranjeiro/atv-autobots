package com.autobots.automanager.dto;

import com.autobots.automanager.entidades.Documento;

public record DadosExclusaoDocumento(
		Long idCliente,
		Documento documento) {

}
