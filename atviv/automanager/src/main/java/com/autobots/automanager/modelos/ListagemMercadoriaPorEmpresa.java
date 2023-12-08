package com.autobots.automanager.modelos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosListagemEmpresa;
import com.autobots.automanager.dto.DadosMercadoria;
import com.autobots.automanager.dto.DadosMercadoriaPorEmpresa;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;

@Component
public class ListagemMercadoriaPorEmpresa {
	
	public DadosMercadoriaPorEmpresa listarMercadorias(Empresa empresaListagem) {
		DadosListagemEmpresa empresa = new DadosListagemEmpresa(
				empresaListagem.getId(), 
				empresaListagem.getRazaoSocial(), 
				empresaListagem.getNomeFantasia(), 
				empresaListagem.getTelefones(), 
				empresaListagem.getEndereco(), 
				empresaListagem.getCadastro(),
				empresaListagem.getLinks());
		
		Set<DadosMercadoria> mercadorias = new HashSet<DadosMercadoria>();
		for(Mercadoria mercadoria : empresaListagem.getMercadorias()) {
			DadosMercadoria dadosMercadoria = new DadosMercadoria(
					mercadoria.getId(),
					mercadoria.getNome(),
					mercadoria.getDescricao(),
					mercadoria.getValor(),
					mercadoria.getCadastro(),
					mercadoria.getFabricacao(),
					mercadoria.getValidade(),
					mercadoria.getOriginal());
			mercadorias.add(dadosMercadoria);
		}
		
		DadosMercadoriaPorEmpresa mercadoriasPorEmpresa = new DadosMercadoriaPorEmpresa(empresa, mercadorias);
		return mercadoriasPorEmpresa;
	}
}

