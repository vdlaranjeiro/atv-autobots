package com.autobots.automanager.modelos;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.DadosCadastroVenda;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@Service
public class CadastrarVenda {
	
	@Autowired
	private RepositorioMercadoria repositorioMercadoria;
	
	@Autowired
	private RepositorioServico repositorioServico;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	
	public Venda cadastrarVenda(DadosCadastroVenda dadosVenda, Empresa empresa) {
		Venda venda = new Venda();
		System.out.println(dadosVenda);
		Usuario cliente = repositorioUsuario.findById(dadosVenda.idCliente()).orElse(null);
		Usuario funcionario = repositorioUsuario.findById(dadosVenda.idFuncionario()).orElse(null);
		Set<Mercadoria> mercadorias = this.buscarMercadorias(dadosVenda.idsMercadorias());
		Set<Servico> servicos = this.buscarServicos(dadosVenda.idsServicos());
		Veiculo veiculo = repositorioVeiculo.findById(dadosVenda.idVeiculo()).orElse(null);
		
		venda.setCadastro(Calendar.getInstance().getTime());
		
		if(cliente != null && cliente.getPerfis().contains(PerfilUsuario.CLIENTE) && empresa.getUsuarios().contains(cliente)) {
			venda.setCliente(cliente);
			
			if(veiculo != null && veiculo.getProprietario().getId() == cliente.getId()) {
				venda.setVeiculo(veiculo);
			}
		}
		if(funcionario != null && funcionario.getPerfis().contains(PerfilUsuario.FUNCIONARIO) && empresa.getUsuarios().contains(funcionario)) {
			venda.setFuncionario(funcionario);
		}
		if(!mercadorias.isEmpty() || !servicos.isEmpty()) {
			for(Mercadoria mercadoria : mercadorias) {
				venda.getMercadorias().add(mercadoria);
			}
			for(Servico servico : servicos) {
				venda.getServicos().add(servico);			
			}
		}
		return venda;
	}
	
	private Set<Mercadoria> buscarMercadorias(Set<Long> idsMercadorias) {
		Set<Mercadoria> mercadorias = new HashSet<Mercadoria>();
		for(Long id : idsMercadorias) {
			Mercadoria mercadoria = repositorioMercadoria.findById(id).orElse(null);
			if(mercadoria != null) {
				mercadorias.add(mercadoria);
			}
		}
		return mercadorias;
	}
	
	private Set<Servico> buscarServicos(Set<Long> idsServicos) {
		Set<Servico> servicos = new HashSet<Servico>();
		for(Long id : idsServicos) {
			Servico servico = repositorioServico.findById(id).orElse(null);
			if(servico != null) {
				servicos.add(servico);
			}
		}
		return servicos;
	}
}
