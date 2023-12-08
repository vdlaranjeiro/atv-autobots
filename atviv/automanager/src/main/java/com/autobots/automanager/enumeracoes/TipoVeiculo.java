package com.autobots.automanager.enumeracoes;

public enum TipoVeiculo {
	HATCH,SEDAN,SUV,PICKUP,SW;
	
	public static boolean tipoValido(TipoVeiculo tipoCadastrado) {
		for(TipoVeiculo tipo : TipoVeiculo.values()) {
			if(tipo.equals(tipoCadastrado)) {
				return true;
			}
		}
		return false;
	}
}