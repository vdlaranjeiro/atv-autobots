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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.hateoas.AdicionadorLinkServico;
import com.autobots.automanager.modelos.AtualizadorServico;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/servicos")
public class ServicoControle {
	
	@Autowired
	private RepositorioServico repositorioServico;
	
	@Autowired
	private AtualizadorServico atualizador;
	
	@Autowired
	private AdicionadorLinkServico adicionadorLink;
	
	@Autowired 
	private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
	private RepositorioVenda repositorioVenda;
	
	@GetMapping("/listar")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorioServico.findAll();
		if(servicos != null) {
			adicionadorLink.adicionarLink(servicos);
			return new ResponseEntity<List<Servico>>(servicos, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Servico>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/servico/{idServico}")
	public ResponseEntity<Servico> obterServico(@PathVariable Long idServico) {
		Servico servico = repositorioServico.findById(idServico).orElse(null);
		if(servico != null) {
			adicionadorLink.adicionarLink(servico);
			return new ResponseEntity<Servico>(servico, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Servico>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cadastrar/{idEmpresa}")
	public ResponseEntity<?> cadastrarServico(@PathVariable Long idEmpresa, @RequestBody Servico servico) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(servico != null) {
			Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
			if(empresa != null) {
				empresa.getServicos().add(servico);
				repositorioEmpresa.save(empresa);
				status = HttpStatus.CREATED;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarServico(@RequestBody Servico servicoAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(servicoAtualizacao.getId() != null) {
			Servico servico = repositorioServico.findById(servicoAtualizacao.getId()).orElse(null);
			if(servico != null) {
				atualizador.atualizarServico(servico, servicoAtualizacao);
				repositorioServico.save(servico);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@DeleteMapping("/excluir/{idServico}")
	public ResponseEntity<?> excluirServico(@PathVariable Long idServico) {
		HttpStatus status = HttpStatus.CONFLICT;
		Servico servico = repositorioServico.findById(idServico).orElse(null);
		if(servico != null) {
			boolean existeVenda = false;
			for(Venda venda : repositorioVenda.findAll()) {
				if(venda.getServicos().contains(servico)) {
					existeVenda = true;
					break;
				}
			}
			
			if(!existeVenda) {
				System.out.println("entrou aqui");
				for(Empresa empresa : repositorioEmpresa.findAll()) {
					empresa.getServicos().removeIf(empresaServico -> empresaServico.getId().equals(servico.getId()));
					repositorioEmpresa.save(empresa);
				}
				repositorioServico.delete(servico);
				status = HttpStatus.OK;
			}
			
		} else {
			status = HttpStatus.NOT_FOUND; 
		}
		return new ResponseEntity<>(status);
	}
}
