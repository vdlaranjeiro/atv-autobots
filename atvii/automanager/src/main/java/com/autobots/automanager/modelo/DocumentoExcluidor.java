package com.autobots.automanager.modelo;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;

@Component
public class DocumentoExcluidor {
	
	public Cliente excluir(Cliente cliente, Documento documentoExclusao) {
		List<Documento> listaDocumentos = cliente.getDocumentos();
		Iterator<Documento> iterator = listaDocumentos.iterator();

		while (iterator.hasNext()) {
			Documento documento = iterator.next();
			if (documento.getId() == documentoExclusao.getId()) {
				iterator.remove();
			}
		}
		return cliente;
	}
}
