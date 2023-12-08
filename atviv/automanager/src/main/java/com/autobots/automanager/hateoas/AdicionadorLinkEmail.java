package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;


import com.autobots.automanager.controles.EmailControle;
import com.autobots.automanager.entidades.Email;

@Component
public class AdicionadorLinkEmail implements AdicionadorLink<Email>{
	
	@Override
	public void adicionarLink(List<Email> emails) {
		for(Email email : emails) {
			Long id = email.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.obterEmail(id))
					.withSelfRel();
			email.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Email email) {
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.obterEmails())
					.withRel("Lista de emails");
			email.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.atualizarEmail(null))
					.withRel("Atualizar email");
			email.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.excluirEmail(null, null))
					.withRel("Excluir email");
			email.add(linkExcluir);
			
			
			Link linkCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmailControle.class)
							.cadastrarEmail(null, null))
					.withRel("Cadastrar novo email");
			email.add(linkCadastrar);
			
			
	}
}
