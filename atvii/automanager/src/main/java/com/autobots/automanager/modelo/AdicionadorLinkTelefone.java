package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone>{
	
	@Override
	public void adicionarLink(List<Telefone> listaTelefones) {
		for(Telefone telefone : listaTelefones) {
			Long id = telefone.getId();
			Link linkProprio = WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder
									.methodOn(TelefoneControle.class)
									.obterTelefone(id))
							.withSelfRel();
			telefone.add(linkProprio);
		}
		
	}
	
	@Override
	public void adicionarLink(Telefone objeto) {
		Link linkListaTelefone = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.obterTelefones())
				.withRel("Lista de todos os telefones");
		objeto.add(linkListaTelefone);
		
		Link linkAtualizarTelefone = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.atualizarTelefone(null))
				.withRel("Atualizar telefone");
		objeto.add(linkAtualizarTelefone);
		
		Link linkExcluirTelefone = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.excluirTelefone(null))
				.withRel("Excluir telefone");
		objeto.add(linkExcluirTelefone);
		
		Link linkCadastrarTelefone = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.cadastrarTelefone(null))
				.withRel("Cadastrar novo telefone");
		objeto.add(linkCadastrarTelefone);
	}
}
