package com.autobots.automanager.modelos;

import org.springframework.stereotype.Service;

import com.autobots.automanager.dto.DadosCadastroCliente;
import com.autobots.automanager.dto.DadosCadastroFornecedor;
import com.autobots.automanager.dto.DadosCadastroFuncionario;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoVeiculo;

@Service
public class CadastrarUsuarios {
	
	public Usuario cadastrarCliente(DadosCadastroCliente dadosCadastroCliente) {
		Usuario usuario = new Usuario();
		usuario.setNome(dadosCadastroCliente.nome());
		usuario.setNomeSocial(dadosCadastroCliente.nomeSocial());
		usuario.getPerfis().add(PerfilUsuario.CLIENTE);
		usuario.setTelefones(dadosCadastroCliente.telefones());
		usuario.setEndereco(dadosCadastroCliente.endereco());
		usuario.setDocumentos(dadosCadastroCliente.documentos());
		usuario.setEmails(dadosCadastroCliente.emails());
		usuario.getCredenciais().add(dadosCadastroCliente.credencial());
		if(dadosCadastroCliente.veiculos() != null) {
			for(Veiculo veiculo : dadosCadastroCliente.veiculos()) {
				if(TipoVeiculo.tipoValido(veiculo.getTipo())) {
					veiculo.setProprietario(usuario);
					usuario.getVeiculos().add(veiculo);
				}
			}
		}
		usuario.setAtivo(true);
		
		return usuario;
	}
	
	public Usuario cadastrarFuncionario(DadosCadastroFuncionario dadosCadastroFuncionario) {
		Usuario usuario = new Usuario();
		usuario.setNome(dadosCadastroFuncionario.nome());
		usuario.setNomeSocial(dadosCadastroFuncionario.nomeSocial());
		usuario.getPerfis().add(PerfilUsuario.FUNCIONARIO);
		usuario.setTelefones(dadosCadastroFuncionario.telefones());
		usuario.setEndereco(dadosCadastroFuncionario.endereco());
		usuario.setDocumentos(dadosCadastroFuncionario.documentos());
		usuario.setEmails(dadosCadastroFuncionario.emails());
		usuario.getCredenciais().add(dadosCadastroFuncionario.credencialCodigoBarra());
		usuario.getCredenciais().add(dadosCadastroFuncionario.credencialUsuarioSenha());
		usuario.setVendas(dadosCadastroFuncionario.vendas());
		usuario.setAtivo(true);
		
		return usuario;
	}
	
	public Usuario cadastrarFornecedor(DadosCadastroFornecedor dadosCadastroFornecedor) {
		Usuario usuario = new Usuario();
		usuario.setNome(dadosCadastroFornecedor.nome());
		usuario.setNomeSocial(dadosCadastroFornecedor.nomeSocial());
		usuario.getPerfis().add(PerfilUsuario.FORNECEDOR);
		usuario.setTelefones(dadosCadastroFornecedor.telefones());
		usuario.setEndereco(dadosCadastroFornecedor.endereco());
		usuario.setDocumentos(dadosCadastroFornecedor.documentos());
		usuario.setEmails(dadosCadastroFornecedor.emails());
		usuario.getCredenciais().add(dadosCadastroFornecedor.credencial());
		usuario.setMercadorias(dadosCadastroFornecedor.mercadorias());
		usuario.setAtivo(true);

		return usuario;
	}
}
