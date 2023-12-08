package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco>{
	
	@Override
	public void adicionarLink(List<Endereco> enderecos) {
		for(Endereco endereco : enderecos) {
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
	public void adicionarLink(Endereco endereco) {
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.obterEnderecos())
					.withRel("Lista de endereços");
			endereco.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.atualizarEndereco(null))
					.withRel("Atualizar endereço");
			endereco.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.excluirEndereco(null))
					.withRel("Excluir endereço");
			endereco.add(linkExcluir);
			
			
			Link linkCadastrarUsuario = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.cadastrarEnderecoUsuario(null, null))
					.withRel("Cadastrar novo endereço para usuário");
			endereco.add(linkCadastrarUsuario);
			
			Link linkCadastrarEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.cadastrarEnderecoEmpresa(null, null))
					.withRel("Cadastrar novo endereço para empresa");
			endereco.add(linkCadastrarEmpresa);
			
			
	}
}
