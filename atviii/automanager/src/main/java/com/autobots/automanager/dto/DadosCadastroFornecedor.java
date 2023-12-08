package com.autobots.automanager.dto;

import java.util.Set;

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;

public record DadosCadastroFornecedor(
		String nome,
		String nomeSocial,
		Set<Telefone> telefones,
		Endereco endereco,
		Set<Documento> documentos,
		Set<Email> emails,
		CredencialUsuarioSenha credencial,
		Set<Mercadoria> mercadorias) {

}