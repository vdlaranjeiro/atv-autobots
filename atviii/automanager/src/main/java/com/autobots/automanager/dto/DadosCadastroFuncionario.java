package com.autobots.automanager.dto;

import java.util.Set;

import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Venda;

public record DadosCadastroFuncionario(
		String nome,
		String nomeSocial,
		Set<Telefone> telefones,
		Endereco endereco,
		Set<Documento> documentos,
		Set<Email> emails,
		CredencialCodigoBarra credencialCodigoBarra,
		CredencialUsuarioSenha credencialUsuarioSenha,
		Set<Venda> vendas) {

}