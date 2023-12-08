package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;

@Component
public class AtualizadorCredencial {
	
	public void atualizarCredencialUsuarioSenha(CredencialUsuarioSenha credencial, CredencialUsuarioSenha atualizacao) {
		if(atualizacao.getNomeUsuario() != null) {
			credencial.setNomeUsuario(atualizacao.getNomeUsuario());
		}
		if(atualizacao.getSenha() != null) {
			credencial.setSenha(atualizacao.getSenha());
		}
	}
	
	public void atualizarCredencialCodigoBarra(CredencialCodigoBarra credencial, CredencialCodigoBarra atualizacao) {
		if(atualizacao.getCodigo() != null) {
			credencial.setCodigo(atualizacao.getCodigo());
		}
	}
}
