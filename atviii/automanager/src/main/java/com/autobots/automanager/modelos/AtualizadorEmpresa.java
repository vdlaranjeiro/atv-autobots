package com.autobots.automanager.modelos;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosAtualizacaoEmpresa;
import com.autobots.automanager.entidades.Empresa;

@Component
public class AtualizadorEmpresa {
	
	public void atualizarDados(Empresa empresa, DadosAtualizacaoEmpresa atualizacao) {
		if(atualizacao.razaoSocial() != null) {
			empresa.setRazaoSocial(atualizacao.razaoSocial());
		}
		
		if(atualizacao.nomeFantasia() != null) {
			empresa.setNomeFantasia(atualizacao.nomeFantasia());
		}
	}
}

