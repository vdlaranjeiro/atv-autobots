package com.autobots.automanager.modelo;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.DocumentoControle;

import com.autobots.automanager.entidades.Documento;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento>{
	
	@Override
	public void adicionarLink(List<Documento> listaDocumentos) {
		for(Documento documento : listaDocumentos) {
			Long id = documento.getId();
			Link linkProprio = WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder
									.methodOn(DocumentoControle.class)
									.obterDocumento(id))
							.withSelfRel();
			documento.add(linkProprio);
		}
		
	}
	
	@Override
	public void adicionarLink(Documento objeto) {
		Link linkListaDocumentos = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder
								.methodOn(DocumentoControle.class)
								.obterDocumentos())
						.withRel("Lista de todos os documentos");
		objeto.add(linkListaDocumentos);
		
		Link linkAtualizarDocumento = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.atualizarDocumento(null))
				.withRel("Atualizar documento");
		objeto.add(linkAtualizarDocumento);
		
		Link linkExcluirDocumento = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.excluirDocumento(null))
				.withRel("Excluir documento");
		objeto.add(linkExcluirDocumento);
		
		Link linkCadastrarDocumento = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.cadastrarDocumento(null))
				.withRel("Cadastrar documento");
		objeto.add(linkCadastrarDocumento);
	}
}
