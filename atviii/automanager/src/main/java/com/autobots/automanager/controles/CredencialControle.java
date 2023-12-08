package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.CredencialCodigoBarra;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.modelos.AtualizadorCredencial;
import com.autobots.automanager.repositorios.RepositorioCredencialCodigoBarra;
import com.autobots.automanager.repositorios.RepositorioCredencialUsuarioSenha;

@RestController
@RequestMapping("/credenciais")
public class CredencialControle {
	
	@Autowired
	private RepositorioCredencialUsuarioSenha repositorioUsuarioSenha;
	
	@Autowired
	private RepositorioCredencialCodigoBarra repositorioCodigoBarra;
	
	@Autowired
	private AtualizadorCredencial atualizador;
	
	@PutMapping("/usuario-senha/atualizar")
	public ResponseEntity<?> atualizarCredencialUsuarioSenha(@RequestBody CredencialUsuarioSenha credencialAtualizacao) {
		if(credencialAtualizacao.getId() != null) {
			CredencialUsuarioSenha credencial = repositorioUsuarioSenha.findById(credencialAtualizacao.getId()).orElse(null);
			if(credencial != null) {
				atualizador.atualizarCredencialUsuarioSenha(credencial, credencialAtualizacao);
				repositorioUsuarioSenha.save(credencial);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/codigo-barra/atualizar")
	public ResponseEntity<?> atualizarCredencialCodigoBarra(@RequestBody CredencialCodigoBarra credencialAtualizacao) {
		if(credencialAtualizacao.getId() != null) {
			CredencialCodigoBarra credencial = repositorioCodigoBarra.findById(credencialAtualizacao.getId()).orElse(null);
			if(credencial != null) {
				atualizador.atualizarCredencialCodigoBarra(credencial, credencialAtualizacao);
				repositorioCodigoBarra.save(credencial);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
