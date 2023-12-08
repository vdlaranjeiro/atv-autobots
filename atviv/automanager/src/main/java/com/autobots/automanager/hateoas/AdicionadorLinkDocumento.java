package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento>{
	
	@Override
	public void adicionarLink(List<Documento> documentos) {
		for(Documento documento : documentos) {
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
	public void adicionarLink(Documento documento) {
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentoControle.class)
							.obterDocumentos())
					.withRel("Lista de documentos");
			documento.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentoControle.class)
							.atualizarDocumento(null))
					.withRel("Atualizar documento");
			documento.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentoControle.class)
							.excluirDocumento(null, null))
					.withRel("Excluir documento");
			documento.add(linkExcluir);
			
			
			Link linkCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentoControle.class)
							.cadastrarDocumento(null, null))
					.withRel("Cadastrar novo documento");
			documento.add(linkCadastrar);
			
			
	}
}
