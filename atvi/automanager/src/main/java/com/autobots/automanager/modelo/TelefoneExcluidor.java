package com.autobots.automanager.modelo;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;

@Component
public class TelefoneExcluidor {
	
	public Cliente excluir(Cliente cliente, Telefone telefoneExclusao) {
		List<Telefone> listaTelefones = cliente.getTelefones();
		Iterator<Telefone> iterator = listaTelefones.iterator();
		
		while(iterator.hasNext()) {
			Telefone telefone = iterator.next();
			if(telefone.getId() == telefoneExclusao.getId()) {
				iterator.remove();
			}
		}
		return cliente;
	}
}
