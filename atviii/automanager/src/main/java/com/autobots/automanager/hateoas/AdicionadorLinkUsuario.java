package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario>{
	
	@Override
	public void adicionarLink(List<Usuario> usuarios) {
		for(Usuario usuario : usuarios) {
			Long id = usuario.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.obterUsuario(id))
					.withSelfRel();
			usuario.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Usuario usuario) {
			Long id = usuario.getId();
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.obterUsuarios())
					.withRel("Lista de usuários");
			usuario.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.atualizarUsuario(null))
					.withRel("Atualizar usuário");
			usuario.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.excluirUsuario(id))
					.withRel("Excluir usuário");
			usuario.add(linkExcluir);
			
			Link linkCadastrarCliente = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.cadastrarCliente(null, null))
					.withRel("Cadastrar cliente");
			usuario.add(linkCadastrarCliente);
			
			Link linkCadastrarFuncionario = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.cadastrarFuncionario(null, null))
					.withRel("Cadastrar funcionario");
			usuario.add(linkCadastrarFuncionario);
			
			Link linkCadastrarFornecedor = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.cadastrarFornecedor(null, null))
					.withRel("Cadastrar fornecedor");
			usuario.add(linkCadastrarFornecedor);
			
			if(usuario.getPerfis().contains(PerfilUsuario.CLIENTE)) {
				Link linkVeiculos = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(UsuarioControle.class)
								.veiculosPorUsuario(id))
						.withRel("Veículos do usuário");
				usuario.add(linkVeiculos);
			} else if(usuario.getPerfis().contains(PerfilUsuario.FUNCIONARIO)) {
				Link linkVendas = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(UsuarioControle.class)
								.vendaPorFuncionario(id))
						.withRel("Vendas do usuário");
				usuario.add(linkVendas);
			} else if(usuario.getPerfis().contains(PerfilUsuario.FORNECEDOR)) {
				Link linkMercadorias = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(UsuarioControle.class)
								.mercadoriaPorFornecedor(id))
						.withRel("Mercadorias do usuário");
				usuario.add(linkMercadorias);
			}
			
	}
}
