package com.autobots.automanager.dto;

import java.util.Set;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.TipoVeiculo;

public record DadosVeiculo(
		Long id,
		TipoVeiculo tipo,
		String modelo,
		String placa,
		Set<Venda> vendas){

}
