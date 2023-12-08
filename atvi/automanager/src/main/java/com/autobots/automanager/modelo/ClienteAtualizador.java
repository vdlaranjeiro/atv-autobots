package com.autobots.automanager.modelo;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosAtualizacaoCliente;
import com.autobots.automanager.entidades.Cliente;

@Component
public class ClienteAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizarDados(Cliente cliente, DadosAtualizacaoCliente atualizacao) {
		if (!verificador.verificar(atualizacao.nome())) {
			cliente.setNome(atualizacao.nome());
		}
		if (!verificador.verificar(atualizacao.nomeSocial())) {
			cliente.setNomeSocial(atualizacao.nomeSocial());
		}
		if (!(atualizacao.dataNascimento() == null)) {
			cliente.setDataNascimento(atualizacao.dataNascimento());
		}
	}
}
