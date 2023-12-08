package com.autobots.automanager.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.entidades.Veiculo;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<Veiculo>{
	
	@Override
	public void adicionarLink(List<Veiculo> veiculos) {
		for(Veiculo veiculo : veiculos) {
			Long id = veiculo.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.obterVeiculo(id))
					.withSelfRel();
			veiculo.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Veiculo veiculo) {
			Long id = veiculo.getId();
			Link linkLista = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.obterVeiculos())
					.withRel("Lista de veiculos");
			veiculo.add(linkLista);
			
			Link linkAtualizar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.atualizarVeiculo(null))
					.withRel("Atualizar veiculo");
			veiculo.add(linkAtualizar);
			
			Link linkExcluir = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.excluirVeiculo(id))
					.withRel("Excluir veiculo");
			veiculo.add(linkExcluir);
			
			
			Link linkCadastrar = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoControle.class)
							.cadastrarVeiculo(null, null))
					.withRel("Cadastrar novo veiculo");
			veiculo.add(linkCadastrar);
			
			
	}
}
