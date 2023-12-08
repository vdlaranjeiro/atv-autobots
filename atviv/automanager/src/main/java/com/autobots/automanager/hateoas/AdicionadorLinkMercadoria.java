package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {
	
	@Override
	public void adicionarLink(List<Mercadoria> mercadorias) {
		for(Mercadoria mercadoria : mercadorias) {
			Long id = mercadoria.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadoria(id))
					.withSelfRel();
			mercadoria.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Mercadoria mercadoria) {
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadorias())
					.withRel("Lista de mercadorias");
			mercadoria.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.atualizarMercadoria(null))
					.withRel("Atualizar mercadoria");
			mercadoria.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.excluirMercadoria(null))
					.withRel("Excluir mercadoria");
			mercadoria.add(linkExcluir);
			
			
			Link linkCadastrarUsuario = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.cadastrarMercadoriaFornecedor(null, null))
					.withRel("Cadastrar nova mercadoria para fornecedor");
			mercadoria.add(linkCadastrarUsuario);
			
			Link linkCadastrarEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.cadastrarMercadoriaEmpresa(null, null))
					.withRel("Cadastrar nova mercadoria para empresa");
			mercadoria.add(linkCadastrarEmpresa);
			
			
	}
}
