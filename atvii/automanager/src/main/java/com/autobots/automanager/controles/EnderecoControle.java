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

import com.autobots.automanager.dto.DadosCadastroEndereco;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {
	
	@Autowired
	private EnderecoRepositorio repositorioEndereco;
	
	@Autowired
	private EnderecoSelecionador selecionador;
	
	@Autowired
	private EnderecoAtualizador atualizador;
	
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Endereco>> obterEnderecos() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		if(enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		Endereco endereco = selecionador.selecionar(enderecos, id);
		if(endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarEndereco(@RequestBody DadosCadastroEndereco dadosCadastroEndereco) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosCadastroEndereco.idCliente() != null) {
			Cliente cliente = repositorioCliente.getById(dadosCadastroEndereco.idCliente());
			if(cliente != null) {
				cliente.setEndereco(dadosCadastroEndereco.endereco());
				repositorioCliente.save(cliente);
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
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacaoEndereco) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco endereco = repositorioEndereco.getById(atualizacaoEndereco.getId());
		if(endereco != null) {
			atualizador.atualizar(endereco, atualizacaoEndereco);
			repositorioEndereco.save(endereco);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir/{clienteId}")
	public ResponseEntity<?> excluirEndereco(@PathVariable Long clienteId) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente cliente = repositorioCliente.getById(clienteId);
		if(cliente != null) {
			cliente.setEndereco(null);
			repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
}
