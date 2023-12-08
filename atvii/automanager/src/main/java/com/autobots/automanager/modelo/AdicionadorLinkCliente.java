package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.entidades.Cliente;

@Component
public class AdicionadorLinkCliente implements AdicionadorLink<Cliente> {
	
	@Override
	public void adicionarLink(List<Cliente> listaClientes) {
		for(Cliente cliente : listaClientes) {
			Long id = cliente.getId();
			Link linkProprio = WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder
									.methodOn(ClienteControle.class)
									.obterCliente(id))
							.withSelfRel();
			cliente.add(linkProprio);
		}
		
	}
	
	@Override
	public void adicionarLink(Cliente objeto) {
		Link linkListaClientes = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.obterClientes())
				.withRel("Lista de todos os clientes");
		objeto.add(linkListaClientes);
		
		Link linkAtualizarCliente = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.atualizarCliente(null))
				.withRel("Atualizar informações");
		objeto.add(linkAtualizarCliente);
		
		Link linkExcluirCliente = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.excluirCliente(null))
				.withRel("Excluir cliente");
		objeto.add(linkExcluirCliente);
		
		Link linkCadastrarCliente = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ClienteControle.class)
						.cadastrarCliente(null))
				.withRel("Cadastrar um novo cliente");
		objeto.add(linkCadastrarCliente);
	}
}
