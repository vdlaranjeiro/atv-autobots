package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

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

import com.autobots.automanager.dto.DadosAtualizacaoEmpresa;
import com.autobots.automanager.dto.DadosListagemEmpresa;
import com.autobots.automanager.dto.DadosMercadoriaPorEmpresa;
import com.autobots.automanager.dto.DadosServicoPorEmpresa;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.hateoas.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.AtualizadorEmpresa;
import com.autobots.automanager.modelos.ListagemEmpresa;
import com.autobots.automanager.modelos.ListagemMercadoriaPorEmpresa;
import com.autobots.automanager.modelos.ListagemServicoPorEmpresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
	
	@Autowired
	private RepositorioEmpresa repositorio;
	
	@Autowired
	private AtualizadorEmpresa atualizador;
	
	@Autowired
	private ListagemEmpresa listagemEmpresa;
	
	@Autowired
	private ListagemServicoPorEmpresa listagemServicoPorEmpresa;
	
	@Autowired
	private ListagemMercadoriaPorEmpresa listagemMercadoriaPorEmpresa;
	
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLink;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/listar")
	public ResponseEntity<List<DadosListagemEmpresa>> obterEmpresas(){
		List<Empresa> empresas = repositorio.findAll();
		if(!empresas.isEmpty()) {
			adicionadorLink.adicionarLink(empresas);
			List<DadosListagemEmpresa> dadosEmpresas = listagemEmpresa.listarEmpresas(empresas);
			return new ResponseEntity<List<DadosListagemEmpresa>>(dadosEmpresas, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<List<DadosListagemEmpresa>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@GetMapping("/empresa/{id}")
	public ResponseEntity<Empresa> obterEmpresa(@PathVariable Long id) {
		Empresa empresa = repositorio.findById(id).orElse(null);
		if(empresa != null) {
			adicionadorLink.adicionarLink(empresa);
			return new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@GetMapping("/empresa/{id}/servicos")
	public ResponseEntity<DadosServicoPorEmpresa> servicoPorEmpresa(@PathVariable Long id) {
		Empresa empresa = repositorio.findById(id).orElse(null);
		if(empresa != null) {
			adicionadorLink.adicionarLink(empresa);
			DadosServicoPorEmpresa servicosPorEmpresa = listagemServicoPorEmpresa.listarServicos(empresa);
			return new ResponseEntity<DadosServicoPorEmpresa>(servicosPorEmpresa, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@GetMapping("/empresa/{id}/mercadorias")
	public ResponseEntity<DadosMercadoriaPorEmpresa> mercadoriaPorEmpresa(@PathVariable Long id) {
		Empresa empresa = repositorio.findById(id).orElse(null);
		if(empresa != null) {
			adicionadorLink.adicionarLink(empresa);
			DadosMercadoriaPorEmpresa mercadoriasPorEmpresa = listagemMercadoriaPorEmpresa.listarMercadorias(empresa);
			return new ResponseEntity<DadosMercadoriaPorEmpresa>(mercadoriasPorEmpresa, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@GetMapping("/empresa/{id}/vendas")
	public ResponseEntity<Set<Venda>> vendasPorEmpresa(@PathVariable Long id) {
		Empresa empresa = repositorio.findById(id).orElse(null);
		if(empresa != null) {
			adicionadorLink.adicionarLink(empresa);
			Set<Venda> vendas = empresa.getVendas();
			return new ResponseEntity<Set<Venda>>(vendas, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarEmpresa(@RequestBody Empresa empresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(empresa.getId() == null) {
			repositorio.save(empresa);
			status = HttpStatus.CREATED; 
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarEmpresa(@RequestBody DadosAtualizacaoEmpresa atualizacaoEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		Empresa empresa = repositorio.findById(atualizacaoEmpresa.id()).orElse(null);
		if(empresa != null) {
			atualizador.atualizarDados(empresa, atualizacaoEmpresa);
			repositorio.save(empresa);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping("/excluir/{idEmpresa}")
	public ResponseEntity<?> excluirEmpresa(@PathVariable Long idEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		Empresa empresa = repositorio.findById(idEmpresa).orElse(null);
		if(empresa != null) {
			repositorio.delete(empresa);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
}
