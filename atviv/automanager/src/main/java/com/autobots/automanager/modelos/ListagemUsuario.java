package com.autobots.automanager.modelos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosListagemUsuario;
import com.autobots.automanager.entidades.Usuario;

@Component
public class ListagemUsuario {
	
	public List<DadosListagemUsuario> listarUsuarios(List<Usuario> usuarios) {
		List<DadosListagemUsuario> listaFinal = new ArrayList<DadosListagemUsuario>();
		for(Usuario usuario : usuarios) {
			if(usuario.isAtivo()) {
				DadosListagemUsuario dadosUsuario = new DadosListagemUsuario(
						usuario.getId(),
						usuario.getNome(),
						 usuario.getNomeSocial(),
						 usuario.getPerfis(),
						 usuario.getTelefones(),
						 usuario.getEndereco(),
						 usuario.getDocumentos(),
						 usuario.getEmails(),
						 usuario.getLinks()
				);
				listaFinal.add(dadosUsuario);
			}
		}
		return listaFinal;
	}
}
