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

import com.autobots.automanager.dto.DadosCadastroTelefone;
import com.autobots.automanager.dto.DadosExclusaoTelefone;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.AdicionadorLinkTelefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneExcluidor;
import com.autobots.automanager.modelo.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefones")
public class TelefoneControle {
	
	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	
	@Autowired
	private TelefoneSelecionador selecionador;
	
	@Autowired
	private TelefoneAtualizador atualizador;
	
	@Autowired
	private TelefoneExcluidor excluidor;
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Telefone>> obterTelefones() {
		List<Telefone> telefones = repositorioTelefone.findAll();
		if(telefones.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(telefones);
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<List<Telefone>>(telefones, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorioTelefone.findAll();
		Telefone telefone = selecionador.selecionar(telefones, id);
		if(telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(telefone);
			ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarTelefone(@RequestBody DadosCadastroTelefone dadosCadastroTelefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosCadastroTelefone.idCliente() != null) {
			Cliente cliente = repositorioCliente.getById(dadosCadastroTelefone.idCliente());
			if(cliente != null) {
				cliente.getTelefones().add(dadosCadastroTelefone.telefone());
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
	public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacaoTelefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		Telefone telefone = repositorioTelefone.getById(atualizacaoTelefone.getId());
		if(telefone != null) {
			atualizador.atualizar(telefone, atualizacaoTelefone);
			repositorioTelefone.save(telefone);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirTelefone(@RequestBody DadosExclusaoTelefone exclusaoTelefone) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(exclusaoTelefone.idCliente() != null && exclusaoTelefone.telefone().getId() != null) {
			Telefone telefone = repositorioTelefone.getById(exclusaoTelefone.telefone().getId());
			Cliente cliente = repositorioCliente.getById(exclusaoTelefone.idCliente());
			if(cliente != null && telefone != null) {
				excluidor.excluir(cliente, telefone);
				repositorioCliente.save(cliente);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		}
		return new ResponseEntity<>(status);
	}
}
