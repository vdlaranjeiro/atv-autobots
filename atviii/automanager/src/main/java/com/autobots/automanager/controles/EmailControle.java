package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.hateoas.AdicionadorLinkEmail;
import com.autobots.automanager.repositorios.RepositorioEmail;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("emails")
public class EmailControle {
	
	@Autowired
	RepositorioEmail repositorioEmail;
	
	@Autowired
	AdicionadorLinkEmail adicionadorLink;
	
	@Autowired
	RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Email>> obterEmails() {
		List<Email> emails = repositorioEmail.findAll();
		if(emails != null) {
			adicionadorLink.adicionarLink(emails);
			return new ResponseEntity<List<Email>>(emails, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Email>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/email/{idEmail}")
	public ResponseEntity<Email> obterEmail(@PathVariable Long idEmail) {
		Email email = repositorioEmail.findById(idEmail).orElse(null);
		if(email != null) {
			adicionadorLink.adicionarLink(email);
			return new ResponseEntity<Email>(email, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Email>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cadastrar/{idUsuario}")
	public ResponseEntity<?> cadastrarEmail(@RequestBody Email email, @PathVariable Long idUsuario) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(email != null) {
			Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
			if(usuario != null) {
				usuario.getEmails().add(email);
				repositorioUsuario.save(usuario);
				status = HttpStatus.CREATED;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEmail(@RequestBody Email emailAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(emailAtualizacao.getId() != null) {
			Email email = repositorioEmail.findById(emailAtualizacao.getId()).orElse(null);
			if(email != null) {
				email.setEndereco(emailAtualizacao.getEndereco());
				repositorioEmail.save(email);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir/{idUsuario}") 
	public ResponseEntity<?> excluirEmail(@PathVariable Long idUsuario, @RequestBody Email emailExclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
		if(usuario != null) {
			for(Email email : usuario.getEmails()) {
				if(email.getId() == emailExclusao.getId()) {
					usuario.getEmails().remove(email);
					repositorioUsuario.save(usuario);
					status = HttpStatus.OK;
					break;
				}
			}
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
}
