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

import com.autobots.automanager.dto.DadosCadastroEndereco;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
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
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/listar")
	public List<Endereco> obterEnderecos() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		return enderecos;
	}
	
	@GetMapping("/endereco/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		return selecionador.selecionar(enderecos, id);
	}
	
	@PostMapping("/cadastrar")
	public void cadastrarEndereco(@RequestBody DadosCadastroEndereco dadosCadastroEndereco) {
		Cliente cliente = repositorioCliente.getById(dadosCadastroEndereco.idCliente());
		cliente.setEndereco(dadosCadastroEndereco.endereco());
		repositorioCliente.save(cliente);
	}
	
	@PutMapping("/atualizar")
	public void atualizarEndereco(@RequestBody Endereco atualizacaoEndereco) {
		Endereco endereco = repositorioEndereco.getById(atualizacaoEndereco.getId());
		atualizador.atualizar(endereco, atualizacaoEndereco);
		repositorioEndereco.save(endereco);
	}
	
	@DeleteMapping("/excluir/{clienteId}")
	public void excluirEndereco(@PathVariable Long clienteId) {
		Cliente cliente = repositorioCliente.getById(clienteId);
		cliente.setEndereco(null);
		repositorioCliente.save(cliente);	
	}
}
