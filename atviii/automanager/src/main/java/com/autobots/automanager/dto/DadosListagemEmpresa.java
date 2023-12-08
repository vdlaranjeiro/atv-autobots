package com.autobots.automanager.dto;

import java.util.Date;
import java.util.Set;

import org.springframework.hateoas.Links;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;

public record DadosListagemEmpresa(
		Long id,
		String razaoSocial,
		String nomeFantasia,
		Set<Telefone> telefones,
		Endereco endereco,
		Date cadastro,
		Links links) {
}
