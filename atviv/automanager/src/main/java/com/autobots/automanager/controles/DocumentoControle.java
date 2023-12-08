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

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.hateoas.AdicionadorLinkDocumento;
import com.autobots.automanager.modelos.AtualizadorDocumento;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {
	
	@Autowired
	private RepositorioDocumento repositorioDocumento;
	
	@Autowired 
	private AtualizadorDocumento atualizador;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Documento>> obterDocumentos(){
		List<Documento> documentos = repositorioDocumento.findAll();
		if(documentos != null) {
			adicionadorLink.adicionarLink(documentos);
			return new ResponseEntity<List<Documento>>(documentos, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Documento>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/documento/{idDocumento}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable Long idDocumento) {
		Documento documento = repositorioDocumento.findById(idDocumento).orElse(null);
		if(documento != null) {
			adicionadorLink.adicionarLink(documento);
			return new ResponseEntity<Documento>(documento, HttpStatus.FOUND);	
		} else {
			return new ResponseEntity<Documento>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar/{idUsuario}")
	public ResponseEntity<?> cadastrarDocumento(@RequestBody Documento documento, @PathVariable Long idUsuario) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(TipoDocumento.tipoValido(documento.getTipo())) {
			Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
			if(usuario != null) {
				usuario.getDocumentos().add(documento);
				repositorioUsuario.save(usuario);
				status = HttpStatus.CREATED;
			} else {
				return new ResponseEntity<>("usuário não encontrado", HttpStatus.NOT_FOUND);
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento documentoAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(documentoAtualizacao.getId() != null) {
			if(TipoDocumento.tipoValido(documentoAtualizacao.getTipo())) {
				Documento documento = repositorioDocumento.findById(documentoAtualizacao.getId()).orElse(null);
				if(documento != null) {
					atualizador.atualizar(documento, documentoAtualizacao);
					repositorioDocumento.save(documento);
					status = HttpStatus.OK;
				} else {
					status = HttpStatus.NOT_FOUND;
				}
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@DeleteMapping("/excluir/{idUsuario}")
	public ResponseEntity<?> excluirDocumento(@RequestBody Documento documentoExclusao, @PathVariable Long idUsuario) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(documentoExclusao != null) {
			Documento documento = repositorioDocumento.findById(documentoExclusao.getId()).orElse(null);
			if(documento != null) {
				Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
				if(usuario != null) {
					usuario.getDocumentos().remove(documento);
					repositorioDocumento.delete(documento);
					status = HttpStatus.OK;
				} else {
					status = HttpStatus.NOT_FOUND;
				}
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
}
