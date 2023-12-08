package com.autobots.automanager.controles;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.hateoas.AdicionadorLinkMercadoria;
import com.autobots.automanager.modelos.AtualizadorMercadoria;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
@RequestMapping("/mercadorias")
public class MercadoriaControle {
	
	@Autowired
	private RepositorioMercadoria repositorioMercadoria;
	
	@Autowired
	private AtualizadorMercadoria atualizador;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLink;
	
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private RepositorioVenda repositorioVenda;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		List<Mercadoria> mercadorias = repositorioMercadoria.findAll();
		if(mercadorias != null) {
			adicionadorLink.adicionarLink(mercadorias);
			return new ResponseEntity<List<Mercadoria>>(mercadorias, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<Mercadoria>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/mercadoria/{idMercadoria}")
	public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable Long idMercadoria) {
		Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
		if(mercadoria != null) {
			adicionadorLink.adicionarLink(mercadoria);
			return new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<Mercadoria>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar/empresa/{idEmpresa}")
	public ResponseEntity<?> cadastrarMercadoriaEmpresa(@RequestBody Mercadoria mercadoria, @PathVariable Long idEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(mercadoria != null) {
			Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
			if(empresa != null) {
				mercadoria.setCadastro(Calendar.getInstance().getTime());
				empresa.getMercadorias().add(mercadoria);
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
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar/fornecedor/{idFornecedor}")
	public ResponseEntity<?> cadastrarMercadoriaFornecedor(@RequestBody Mercadoria mercadoria, @PathVariable Long idFornecedor) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(mercadoria != null) {
			Usuario fornecedor = repositorioUsuario.findById(idFornecedor).orElse(null);
			if(fornecedor != null && fornecedor.getPerfis().contains(PerfilUsuario.FORNECEDOR)) {
				mercadoria.setCadastro(Calendar.getInstance().getTime());
				fornecedor.getMercadorias().add(mercadoria);
				repositorioUsuario.save(fornecedor);
				status = HttpStatus.CREATED;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria mercadoriaAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(mercadoriaAtualizacao.getId() != null) {
			Mercadoria mercadoria = repositorioMercadoria.findById(mercadoriaAtualizacao.getId()).orElse(null);
			if(mercadoria != null) {
				atualizador.atualizarMercadoria(mercadoria, mercadoriaAtualizacao);
				repositorioMercadoria.save(mercadoria);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@DeleteMapping("/excluir/{idMercadoria}")
	public ResponseEntity<?> excluirMercadoria(@PathVariable Long idMercadoria) {
		HttpStatus status = HttpStatus.CONFLICT;
		Mercadoria mercadoria = repositorioMercadoria.findById(idMercadoria).orElse(null);
		if(mercadoria != null) {
			boolean existeVenda = false;
			for(Venda venda : repositorioVenda.findAll()) {
				if(venda.getMercadorias().contains(mercadoria)) {
					existeVenda = true;
					break;
				}
			}
			
			if(!existeVenda) {
				for(Empresa empresa : repositorioEmpresa.findAll()) {
					empresa.getMercadorias().removeIf(empresaMercadoria -> empresaMercadoria.getId().equals(mercadoria.getId()));
					repositorioEmpresa.save(empresa);
				}
				
				for(Usuario fornecedor : repositorioUsuario.findAll()) {
					fornecedor.getMercadorias().removeIf(fornecedorMercadoria -> fornecedorMercadoria.getId().equals(mercadoria.getId()));
					repositorioUsuario.save(fornecedor);
				}
				repositorioMercadoria.delete(mercadoria);
				status = HttpStatus.OK;
			}
		} else {
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status);
	}
}
