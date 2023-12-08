package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco>{
	
	@Override
	public void adicionarLink(List<Endereco> listaEnderecos) {
		for(Endereco endereco : listaEnderecos) {
			Long id = endereco.getId();
			Link linkProprio = WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder
									.methodOn(EnderecoControle.class)
									.obterEndereco(id))
							.withSelfRel();
			endereco.add(linkProprio);
		}
		
	}
	
	@Override
	public void adicionarLink(Endereco objeto) {
		Link linkListaEndereco = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(EnderecoControle.class)
								.obterEnderecos())
						.withRel("Lista de todos os endereços");
		objeto.add(linkListaEndereco);
		
		Link linkAtualizarEndereco = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.atualizarEndereco(null))
				.withRel("Atualizar endereço");
		objeto.add(linkAtualizarEndereco);
		
		Link linkExcluirEndereco = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.excluirEndereco(null))
				.withRel("Excluir endereço");
		objeto.add(linkExcluirEndereco);
		
		Link linkCadastrarEndereco = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.cadastrarEndereco(null))
				.withRel("Cadastrar novo endereço");
		objeto.add(linkCadastrarEndereco);
	}
}
