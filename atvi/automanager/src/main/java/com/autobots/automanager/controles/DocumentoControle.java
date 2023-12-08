package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/listar")
	public List<Documento> obterDocumentos() {
		List<Documento> documentos = repositorioDocumento.findAll();
		return documentos;
	}
	
	@GetMapping("/documento/{id}")
	public Documento obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorioDocumento.findAll();
		return selecionador.selecionar(documentos, id);
	}
	
	@PostMapping("/cadastrar")
	public void cadastrarDocumento(@RequestBody DadosCadastroDocumento dadosCadastroDocumento) {
		if(dadosCadastroDocumento.idCliente() != null) {
			Cliente cliente = repositorioCliente.getById(dadosCadastroDocumento.idCliente());
			cliente.getDocumentos().add(dadosCadastroDocumento.documento());
			repositorioCliente.save(cliente);
		}
	}
	
	@PutMapping("/atualizar")
	public void atualizarDocumento(@RequestBody Documento atualizacaoDocumento) {
		Documento documento = repositorioDocumento.getById(atualizacaoDocumento.getId());
		atualizador.atualizar(documento, atualizacaoDocumento);
		repositorioDocumento.save(documento);
	}
	
	@DeleteMapping("/excluir")
	public void excluirDocumento(@RequestBody DadosExclusaoDocumento dadosExclusaoDocumento) {
		Documento documento = repositorioDocumento.getById(dadosExclusaoDocumento.documento().getId());
		Cliente cliente = repositorioCliente.getById(dadosExclusaoDocumento.idCliente());
		
		excluidor.excluir(cliente, documento);
		repositorioCliente.save(cliente);
	}
}
