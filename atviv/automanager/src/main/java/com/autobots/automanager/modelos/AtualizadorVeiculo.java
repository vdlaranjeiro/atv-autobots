package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosAtualizacaoVeiculo;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.TipoVeiculo;

@Component
public class AtualizadorVeiculo {

	public Veiculo atualizar(Veiculo veiculo, DadosAtualizacaoVeiculo veiculoAtualizacao) {
		if(veiculoAtualizacao.tipo() != null && TipoVeiculo.tipoValido(veiculoAtualizacao.tipo())) {
			veiculo.setTipo(veiculoAtualizacao.tipo());
		}
		if(veiculoAtualizacao.modelo() != null) {
			veiculo.setModelo(veiculoAtualizacao.modelo());
		}
		if(veiculoAtualizacao.placa() != null) {
			veiculo.setPlaca(veiculoAtualizacao.placa());
		}
		return veiculo;
	}
}
