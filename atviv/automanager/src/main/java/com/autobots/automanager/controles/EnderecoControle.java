package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.hateoas.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.AtualizadorEndereco;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {
	
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	
	@Autowired
	private AtualizadorEndereco atualizador;
	
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		if(enderecos != null) {
			adicionadorLink.adicionarLink(enderecos);
			return new ResponseEntity<List<Endereco>>(enderecos, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Endereco>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/endereco/{idEndereco}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable Long idEndereco) {
		Endereco endereco = repositorioEndereco.findById(idEndereco).orElse(null);
		if(endereco != null) {
			adicionadorLink.adicionarLink(endereco);
			return new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Endereco>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/cadastrar/empresa/{idEmpresa}")
	public ResponseEntity<?> cadastrarEnderecoEmpresa(@RequestBody Endereco endereco, @PathVariable Long idEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
		if(empresa != null) {
			empresa.setEndereco(endereco);
			repositorioEmpresa.save(empresa);
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar/usuario/{idUsuario}")
	public ResponseEntity<?> cadastrarEnderecoUsuario(@RequestBody Endereco endereco, @PathVariable Long idUsuario) {
		HttpStatus status = HttpStatus.CONFLICT;
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
		if(usuario != null) {
			usuario.setEndereco(endereco);
			repositorioUsuario.save(usuario);
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco enderecoAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(enderecoAtualizacao.getId() != null) {
			Endereco endereco = repositorioEndereco.findById(enderecoAtualizacao.getId()).orElse(null);
			if(endereco != null) {
				atualizador.atualizar(endereco, enderecoAtualizacao);
				repositorioEndereco.save(endereco);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@DeleteMapping("/excluir/{idEndereco}")
	public ResponseEntity<?> excluirEndereco(@PathVariable Long idEndereco) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco endereco = repositorioEndereco.findById(idEndereco).orElse(null);
		if(endereco != null) {
			List<Empresa> empresas = repositorioEmpresa.findAll();
			List<Usuario> usuarios = repositorioUsuario.findAll();
			
			for(Empresa empresa : empresas) {
				if(empresa.getEndereco().getId() == idEndereco) {
					empresa.setEndereco(null);
					repositorioEmpresa.save(empresa);
					status = HttpStatus.OK;
					break;
				}
			}
			
			for(Usuario usuario : usuarios) {
				if(usuario.getEndereco().getId() == idEndereco) {
					usuario.setEndereco(null);
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
