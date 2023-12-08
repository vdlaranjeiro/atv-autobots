package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosAtualizacaoUsuario;
import com.autobots.automanager.entidades.Usuario;

@Component
public class AtualizadorUsuario {
	
	public Usuario atualizar(Usuario usuario, DadosAtualizacaoUsuario dadosAtualizacao) {
		if(dadosAtualizacao.nome() != null) {
			usuario.setNome(dadosAtualizacao.nome());
		}
		if(dadosAtualizacao.nomeSocial() != null) {
			usuario.setNomeSocial(dadosAtualizacao.nomeSocial());
		}
		return usuario;
	}
}
