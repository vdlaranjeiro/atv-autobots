package com.autobots.automanager.modelos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosUsuario;
import com.autobots.automanager.dto.DadosVeiculo;
import com.autobots.automanager.dto.DadosVeiculosPorUsuario;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;

@Component
public class ListagemVeiculosPorUsuario {
	
	@Autowired
	public DadosVeiculosPorUsuario listarVeiculosPorUsuario(Usuario clienteListagem) {
		DadosUsuario cliente = new DadosUsuario(
				clienteListagem.getId(), 
				clienteListagem.getNome(),
				clienteListagem.getNomeSocial(),
				clienteListagem.getPerfis(),
				clienteListagem.getDocumentos(),
				clienteListagem.getLinks());
		
		Set<DadosVeiculo> veiculos = new HashSet<DadosVeiculo>();
		for(Veiculo veiculo : clienteListagem.getVeiculos()) {
			DadosVeiculo dadosVeiculo = new DadosVeiculo(
					veiculo.getId(),
					veiculo.getTipo(),
					veiculo.getModelo(),
					veiculo.getPlaca(),
					veiculo.getVendas());
			veiculos.add(dadosVeiculo);
		}
		
		DadosVeiculosPorUsuario dadosVeiculoPorUsuario = new DadosVeiculosPorUsuario(cliente, veiculos);
		return dadosVeiculoPorUsuario;
	}
}
