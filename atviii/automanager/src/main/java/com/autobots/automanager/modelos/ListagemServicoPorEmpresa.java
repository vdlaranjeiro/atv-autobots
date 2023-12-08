package com.autobots.automanager.modelos;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.autobots.automanager.dto.DadosListagemEmpresa;
import com.autobots.automanager.dto.DadosServico;
import com.autobots.automanager.dto.DadosServicoPorEmpresa;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;

@Component
public class ListagemServicoPorEmpresa {
	
	public DadosServicoPorEmpresa listarServicos(Empresa empresaListagem) {
		DadosListagemEmpresa empresa = new DadosListagemEmpresa(
				empresaListagem.getId(), 
				empresaListagem.getRazaoSocial(), 
				empresaListagem.getNomeFantasia(), 
				empresaListagem.getTelefones(), 
				empresaListagem.getEndereco(), 
				empresaListagem.getCadastro(),
				empresaListagem.getLinks());
		
		Set<DadosServico> servicos = new HashSet<DadosServico>();
		for(Servico servico : empresaListagem.getServicos()) {
			DadosServico dadosServico = new DadosServico(
					servico.getId(),
					servico.getNome(),
					servico.getDescricao(),
					servico.getValor(),
					servico.getOriginal());
			servicos.add(dadosServico);
		}
		
		DadosServicoPorEmpresa servicosPorEmpresa = new DadosServicoPorEmpresa(empresa, servicos);
		return servicosPorEmpresa;
	}
}
