package com.autobots.automanager.enumeracoes;

public enum TipoDocumento {
	CPF, CNPJ, RG, CNH, PASSAPORTE;
	
	public static boolean tipoValido(TipoDocumento tipoCadastrado) {
		for(TipoDocumento tipo : TipoDocumento.values()) {
			if(tipo.equals(tipoCadastrado)) {
				return true;
			}
		}
		return false;
	}
}