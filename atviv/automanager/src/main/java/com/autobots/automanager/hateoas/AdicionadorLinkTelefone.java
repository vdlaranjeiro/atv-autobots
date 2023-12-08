package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone>{
	
	@Override
	public void adicionarLink(List<Telefone> telefones) {
		for(Telefone telefone : telefones) {
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
	public void adicionarLink(Telefone telefone) {
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.obterTelefones())
					.withRel("Lista de telefones");
			telefone.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.atualizarTelefone(null))
					.withRel("Atualizar telefone");
			telefone.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.excluirTelefone(null))
					.withRel("Excluir telefone");
			telefone.add(linkExcluir);
			
			
			Link linkCadastrarUsuario = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.cadastrarTelefoneUsuario(null, null))
					.withRel("Cadastrar novo telefone para usuário");
			telefone.add(linkCadastrarUsuario);
			
			Link linkCadastrarEmpresa = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.cadastrarTelefoneEmpresa(null, null))
					.withRel("Cadastrar novo telefone para empresa");
			telefone.add(linkCadastrarEmpresa);
			
			
	}
}
