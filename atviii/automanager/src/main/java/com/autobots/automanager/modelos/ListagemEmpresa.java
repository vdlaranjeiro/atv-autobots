package com.autobots.automanager.modelos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosListagemEmpresa;
import com.autobots.automanager.entidades.Empresa;

@Component
public class ListagemEmpresa {
	
	public List<DadosListagemEmpresa> listarEmpresas(List<Empresa> listaEmpresas){
		List<DadosListagemEmpresa> listaFinal = new ArrayList<DadosListagemEmpresa>();
		for(Empresa empresa : listaEmpresas) {
			DadosListagemEmpresa dadosEmpresa = new DadosListagemEmpresa(
					empresa.getId(), 
					empresa.getRazaoSocial(), 
					empresa.getNomeFantasia(), 
					empresa.getTelefones(), 
					empresa.getEndereco(), 
					empresa.getCadastro(),
					empresa.getLinks()
			);
			listaFinal.add(dadosEmpresa);
		}
		return listaFinal;
	}
}
