package com.autobots.automanager.dto;

import java.util.Set;

import org.springframework.hateoas.Links;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

public record DadosUsuario(
		Long id,
		String nome,
		String nomeSocial,
		Set<PerfilUsuario> perfil,
		Set<Documento> documentos,
		Links links) {
}
