package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.dto.DadosCadastroVenda;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.hateoas.AdicionadorLinkVenda;
import com.autobots.automanager.modelos.CadastrarVenda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/vendas")
public class VendaControle {

	@Autowired
	private RepositorioVenda repositorioVenda;
	
	@Autowired
	private CadastrarVenda cadastrarVenda;
	
	@Autowired
	private AdicionadorLinkVenda adicionadorLink;
	
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Venda>> obterVendas() {
		List<Venda> vendas = repositorioVenda.findAll();
		if(vendas != null) {
			adicionadorLink.adicionarLink(vendas);
			return new ResponseEntity<List<Venda>>(vendas, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Venda>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/venda/{idVenda}")
	public ResponseEntity<Venda> obterVenda(@PathVariable Long idVenda) {
		Venda venda = repositorioVenda.findById(idVenda).orElse(null);
		if(venda != null) {
			return new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Venda>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarVenda(@RequestBody DadosCadastroVenda dadosVenda) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(dadosVenda.idCliente() != null && 
			dadosVenda.idEmpresa() != null && 
			dadosVenda.idFuncionario() != null && 
			dadosVenda.idVeiculo() != null && 
			(!dadosVenda.idsMercadorias().isEmpty() || !dadosVenda.idsServicos().isEmpty())) {
			
			Empresa empresa = repositorioEmpresa.findById(dadosVenda.idEmpresa()).orElse(null);
			if(empresa != null) {
				Venda venda = cadastrarVenda.cadastrarVenda(dadosVenda, empresa);
				if(venda.getCliente() != null && venda.getFuncionario() != null && !venda.getMercadorias().isEmpty() && !venda.getServicos().isEmpty() && venda.getVeiculo() != null) {
					repositorioVenda.save(venda);
					
					empresa.getVendas().add(venda);
					repositorioEmpresa.save(empresa);
					
					Usuario funcionario = venda.getFuncionario();
					funcionario.getVendas().add(venda);
					repositorioUsuario.save(funcionario);
					
					Veiculo veiculo = venda.getVeiculo();
					veiculo.getVendas().add(venda);
					repositorioVeiculo.save(veiculo);
					status = HttpStatus.CREATED;
				} else {
					status = HttpStatus.CONFLICT;
				}
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		}
		return new ResponseEntity<>(status);
	}
}
