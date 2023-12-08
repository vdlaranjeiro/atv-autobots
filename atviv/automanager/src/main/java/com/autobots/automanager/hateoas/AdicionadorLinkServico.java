package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.entidades.Servico;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<Servico> {
	
	@Override
	public void adicionarLink(List<Servico> servicos) {
		for(Servico servico : servicos) {
			Long id = servico.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.obterServico(id))
					.withSelfRel();
			servico.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Servico servico) {
			Long id = servico.getId();
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.obterServicos())
					.withRel("Lista de serviços");
			servico.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.atualizarServico(null))
					.withRel("Atualizar serviço");
			servico.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.excluirServico(id))
					.withRel("Excluir serviço");
			servico.add(linkExcluir);
			
			
			Link linkCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.cadastrarServico(null, null))
					.withRel("Cadastrar novo serviço");
			servico.add(linkCadastrar);
			
			
	}

}
