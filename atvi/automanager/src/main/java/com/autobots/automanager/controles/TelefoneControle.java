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

import com.autobots.automanager.dto.DadosCadastroTelefone;
import com.autobots.automanager.dto.DadosExclusaoTelefone;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
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
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/listar")
	public List<Telefone> obterTelefones() {
		List<Telefone> telefones = repositorioTelefone.findAll();
		return telefones;
	}
	
	@GetMapping("/telefone/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorioTelefone.findAll();
		return selecionador.selecionar(telefones, id);
	}
	
	@PostMapping("/cadastrar")
	public void cadastrarTelefone(@RequestBody DadosCadastroTelefone dadosCadastroTelefone) {
		if(dadosCadastroTelefone.idCliente() != null) {
			Cliente cliente = repositorioCliente.getById(dadosCadastroTelefone.idCliente());
			cliente.getTelefones().add(dadosCadastroTelefone.telefone());
			repositorioCliente.save(cliente);
		}
		
		
	}
	
	@PutMapping("/atualizar")
	public void atualizarTelefone(@RequestBody Telefone atualizacaoTelefone) {
		Telefone telefone = repositorioTelefone.getById(atualizacaoTelefone.getId());
		atualizador.atualizar(telefone, atualizacaoTelefone);
		repositorioTelefone.save(telefone);
	}
	
	@DeleteMapping("/excluir")
	public void excluirTelefone(@RequestBody DadosExclusaoTelefone exclusaoTelefone) {
		Telefone telefone = repositorioTelefone.getById(exclusaoTelefone.telefone().getId());
		Cliente cliente = repositorioCliente.getById(exclusaoTelefone.idCliente());
		
		excluidor.excluir(cliente, telefone);
		repositorioCliente.save(cliente);
	}
}
