package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entidades.Empresa;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa>{
	
	@Override
	public void adicionarLink(List<Empresa> empresas) {
		for(Empresa empresa : empresas) {
			Long id = empresa.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresa(id))
					.withSelfRel();
			empresa.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Empresa empresa) {
			Long id = empresa.getId();
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresas())
					.withRel("Lista de empresas");
			empresa.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.atualizarEmpresa(null))
					.withRel("Atualizar empresa");
			empresa.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.excluirEmpresa(id))
					.withRel("Excluir empresa");
			empresa.add(linkExcluir);
			
			Link linkServicos = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.servicoPorEmpresa(id))
					.withRel("Serviços da empresa");
			empresa.add(linkServicos);
			
			Link linkMercadorias = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.mercadoriaPorEmpresa(id))
					.withRel("Mercadorias da empresa");
			empresa.add(linkMercadorias);
			
			Link linkVendas = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.vendasPorEmpresa(id))
					.withRel("Vendas da empresa");
			empresa.add(linkVendas);
			
			Link linkCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.cadastrarEmpresa(null))
					.withRel("Cadastrar nova empresa");
			empresa.add(linkCadastrar);
			
			
	}
}
