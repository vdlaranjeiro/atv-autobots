package com.autobots.automanager.modelos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosMercadoria;
import com.autobots.automanager.dto.DadosMercadoriaPorFornecedor;
import com.autobots.automanager.dto.DadosUsuario;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;

@Component
public class ListagemMercadoriaPorFornecedor {
	
	public DadosMercadoriaPorFornecedor listarMercadorias(Usuario fornecedorListagem) {
		DadosUsuario fornecedor = new DadosUsuario(
				fornecedorListagem.getId(), 
				fornecedorListagem.getNome(), 
				fornecedorListagem.getNomeSocial(), 
				fornecedorListagem.getPerfis(), 
				fornecedorListagem.getDocumentos(),
				fornecedorListagem.getLinks());
		
		Set<DadosMercadoria> mercadorias = new HashSet<DadosMercadoria>();
		for(Mercadoria mercadoria : fornecedorListagem.getMercadorias()) {
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
		
		DadosMercadoriaPorFornecedor mercadoriasPorFornecedor = new DadosMercadoriaPorFornecedor(fornecedor, mercadorias);
		return mercadoriasPorFornecedor;
	}
}
