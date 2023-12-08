package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.dto.DadosAtualizacaoVeiculo;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoVeiculo;
import com.autobots.automanager.hateoas.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.AtualizadorVeiculo;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	
	@Autowired
	private AtualizadorVeiculo atualizador;
	
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLink;
	
	@Autowired 
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioVenda repositorioVenda;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Veiculo>> obterVeiculos(){
		List<Veiculo> veiculos = repositorioVeiculo.findAll();
		if(veiculos != null) {
			adicionadorLink.adicionarLink(veiculos);
			return new ResponseEntity<List<Veiculo>>(veiculos, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Veiculo>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/veiculo/{idVeiculo}")
	public ResponseEntity<Veiculo> obterVeiculo(@PathVariable Long idVeiculo) {
		Veiculo veiculo = repositorioVeiculo.findById(idVeiculo).orElse(null);
		if(veiculo != null) {
			adicionadorLink.adicionarLink(veiculo);
			return new ResponseEntity<Veiculo>(veiculo, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Veiculo>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cadastrar/{idCliente}")
	public ResponseEntity<?> cadastrarVeiculo(@PathVariable Long idCliente, @RequestBody Veiculo veiculo) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(veiculo != null) {
			Usuario cliente = repositorioUsuario.findById(idCliente).orElse(null);
		    if (cliente != null && cliente.getPerfis().contains(PerfilUsuario.CLIENTE)) {
		        if (TipoVeiculo.tipoValido(veiculo.getTipo())) {
		        	veiculo.setProprietario(cliente);
		            cliente.getVeiculos().add(veiculo);
		            repositorioUsuario.save(cliente);
		            status = HttpStatus.CREATED;
		        } else {
		        	status = HttpStatus.BAD_REQUEST;
		        }
		    } else {
		    	status = HttpStatus.NOT_FOUND;
		    }
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
	    return new ResponseEntity<>(status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarVeiculo(@RequestBody DadosAtualizacaoVeiculo veiculoAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(veiculoAtualizacao.id() != null) {
			Veiculo veiculo = repositorioVeiculo.findById(veiculoAtualizacao.id()).orElse(null);
			if(veiculo != null) {
				atualizador.atualizar(veiculo, veiculoAtualizacao);
				repositorioVeiculo.save(veiculo);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir/{idVeiculo}")
	public ResponseEntity<?> excluirVeiculo(@PathVariable Long idVeiculo) {
		HttpStatus status = HttpStatus.CONFLICT;
		Veiculo veiculo = repositorioVeiculo.findById(idVeiculo).orElse(null);
		if(veiculo != null) {
			boolean existeVenda = false;
			for(Venda venda : repositorioVenda.findAll()) {
				if(venda.getVeiculo().equals(veiculo)) {
					existeVenda = true;
					break;
				}
			}
			
			if(!existeVenda) {
				System.out.println("entrou aqui");
				List<Usuario> usuarios = repositorioUsuario.findAll();
				for(Usuario usuario : usuarios) {
					usuario.getVeiculos().removeIf(veiculoUsuario -> veiculoUsuario.getId().equals(veiculo.getId()));
					repositorioUsuario.save(usuario);
				}
				repositorioVeiculo.delete(veiculo);
				status = HttpStatus.OK;
			}	
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
	
}
