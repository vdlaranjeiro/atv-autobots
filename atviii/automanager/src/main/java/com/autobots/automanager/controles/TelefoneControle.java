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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.hateoas.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.AtualizadorTelefone;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioTelefone;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("telefones")
public class TelefoneControle {
	
	@Autowired
	private RepositorioTelefone repositorioTelefone;
	
	@Autowired
	private AtualizadorTelefone atualizador;
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefones = repositorioTelefone.findAll();
		if(telefones != null) {
			adicionadorLink.adicionarLink(telefones);
			return new ResponseEntity<List<Telefone>>(telefones, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Telefone>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable Long id) {
		Telefone telefone = repositorioTelefone.findById(id).orElse(null);
		if(telefone != null) {
			adicionadorLink.adicionarLink(telefone);
			return new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Telefone>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cadastrar/empresa/{idEmpresa}")
	public ResponseEntity<?> cadastrarTelefoneEmpresa(@PathVariable Long idEmpresa, @RequestBody Telefone telefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(telefone != null) {
			Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
			if(empresa != null) {
				empresa.getTelefones().add(telefone);
				repositorioEmpresa.save(empresa);
				status = HttpStatus.CREATED;
			} else {
				status =  HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PostMapping("/cadastrar/usuario/{idUsuario}")
	public ResponseEntity<?> cadastrarTelefoneUsuario(@PathVariable Long idUsuario, @RequestBody Telefone telefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(telefone != null) {
			Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
			if(usuario != null) {
				usuario.getTelefones().add(telefone);
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
	public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone telefoneAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(telefoneAtualizacao.getId() != null) {
			Telefone telefone = repositorioTelefone.findById(telefoneAtualizacao.getId()).orElse(null);
			if(telefone != null) {
				atualizador.atualizar(telefone, telefoneAtualizacao);
				repositorioTelefone.save(telefone);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir/{idTelefone}")
	public ResponseEntity<?> excluirTelefone(@PathVariable Long idTelefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		Telefone telefone = repositorioTelefone.findById(idTelefone).orElse(null);
		if(telefone != null) {
			List<Empresa> empresas = repositorioEmpresa.findAll();
			List<Usuario> usuarios = repositorioUsuario.findAll();
			
			for(Empresa empresa : empresas) {
				for(Telefone telefoneEmpresa : empresa.getTelefones()) {
					if(telefoneEmpresa.getId() == idTelefone) {
						empresa.getTelefones().remove(telefoneEmpresa);
						repositorioEmpresa.save(empresa);
						status = HttpStatus.OK;
						break;
					}
					
				}
			}
			
			for(Usuario usuario : usuarios) {
				for(Telefone telefoneUsuario : usuario.getTelefones()) {
					if(telefoneUsuario.getId() == idTelefone) {
						usuario.getTelefones().remove(telefoneUsuario);
						repositorioUsuario.save(usuario);
						status = HttpStatus.OK;
						break;
					}
				}
			}
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
}
