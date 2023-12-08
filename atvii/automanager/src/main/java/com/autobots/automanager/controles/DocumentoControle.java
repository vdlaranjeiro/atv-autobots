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

import com.autobots.automanager.dto.DadosCadastroDocumento;
import com.autobots.automanager.dto.DadosExclusaoDocumento;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.AdicionadorLinkDocumento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoExcluidor;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documentos")
public class DocumentoControle {
	
	@Autowired
	private DocumentoRepositorio repositorioDocumento;
	
	@Autowired
	private DocumentoSelecionador selecionador;
	
	@Autowired
	private DocumentoAtualizador atualizador;
	
	@Autowired
	private DocumentoExcluidor excluidor;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Documento>> obterDocumentos() {
		List<Documento> documentos = repositorioDocumento.findAll();
		if(documentos.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documentos);
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorioDocumento.findAll();
		Documento documento = selecionador.selecionar(documentos, id);
		if(documento == null) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(documento);
			ResponseEntity<Documento> resposta = new ResponseEntity<>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarDocumento(@RequestBody DadosCadastroDocumento dadosCadastroDocumento) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosCadastroDocumento.idCliente() != null) {
			Cliente cliente = repositorioCliente.getById(dadosCadastroDocumento.idCliente());
			if(cliente != null) {
				cliente.getDocumentos().add(dadosCadastroDocumento.documento());
				repositorioCliente.save(cliente);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacaoDocumento) {
		HttpStatus status = HttpStatus.CONFLICT;
		Documento documento = repositorioDocumento.getById(atualizacaoDocumento.getId());
		if(documento != null) {
			atualizador.atualizar(documento, atualizacaoDocumento);
			repositorioDocumento.save(documento);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirDocumento(@RequestBody DadosExclusaoDocumento dadosExclusaoDocumento) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(dadosExclusaoDocumento.idCliente() != null && dadosExclusaoDocumento.documento().getId() != null) {
			Documento documento = repositorioDocumento.getById(dadosExclusaoDocumento.documento().getId());
			Cliente cliente = repositorioCliente.getById(dadosExclusaoDocumento.idCliente());
			if(cliente != null && documento != null) {
				excluidor.excluir(cliente, documento);
				repositorioCliente.save(cliente);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		}
		return new ResponseEntity<>(status);
	}
}
