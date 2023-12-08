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

import com.autobots.automanager.dto.DadosAtualizacaoUsuario;
import com.autobots.automanager.dto.DadosCadastroCliente;
import com.autobots.automanager.dto.DadosCadastroFornecedor;
import com.autobots.automanager.dto.DadosCadastroFuncionario;
import com.autobots.automanager.dto.DadosListagemUsuario;
import com.autobots.automanager.dto.DadosMercadoriaPorFornecedor;
import com.autobots.automanager.dto.DadosVeiculosPorUsuario;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.hateoas.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.AtualizadorUsuario;
import com.autobots.automanager.modelos.CadastrarUsuarios;
import com.autobots.automanager.modelos.ListagemMercadoriaPorFornecedor;
import com.autobots.automanager.modelos.ListagemUsuario;
import com.autobots.automanager.modelos.ListagemVeiculosPorUsuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private ListagemUsuario listagemUsuario;
	
	@Autowired
	private CadastrarUsuarios cadastrarUsuarios;
	
	@Autowired
	private AtualizadorUsuario atualizador;
	
	@Autowired
	private ListagemVeiculosPorUsuario listagemVeiculosPorUsuario;
	
	@Autowired
	private ListagemMercadoriaPorFornecedor listagemMercadoriaPorFornecedor;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;
	
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/listar")
	public ResponseEntity<List<DadosListagemUsuario>>  obterUsuarios() {
		List<Usuario> usuarios = repositorioUsuario.findAll();
		if(!usuarios.isEmpty()) {
			adicionadorLink.adicionarLink(usuarios);
			List<DadosListagemUsuario> dadosUsuarios = listagemUsuario.listarUsuarios(usuarios);
			return new ResponseEntity<List<DadosListagemUsuario>>(dadosUsuarios, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<Usuario> obterUsuario(@PathVariable Long idUsuario) {
		Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
		if(usuario == null || !usuario.isAtivo()) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		} else {
			adicionadorLink.adicionarLink(usuario);
			return new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PostMapping("/cadastrar/cliente/{idEmpresa}")
	public ResponseEntity<?> cadastrarCliente(@RequestBody DadosCadastroCliente dadosCadastroCliente, @PathVariable Long idEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosCadastroCliente != null) {
			Usuario cliente = cadastrarUsuarios.cadastrarCliente(dadosCadastroCliente);
			Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
			if(empresa != null) {
				empresa.getUsuarios().add(cliente);
				repositorioEmpresa.save(empresa);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR', 'ROLE_CLIENTE')")
	@GetMapping("/usuario/{idUsuario}/veiculos")
	public ResponseEntity<?> veiculosPorUsuario(@PathVariable Long idUsuario) {
		Usuario cliente = repositorioUsuario.findById(idUsuario).orElse(null);
		if(cliente != null && cliente.getPerfis().contains(PerfilUsuario.CLIENTE)) {
			adicionadorLink.adicionarLink(cliente);
			DadosVeiculosPorUsuario dadosVeiculosPorUsuario = listagemVeiculosPorUsuario.listarVeiculosPorUsuario(cliente);
			return new ResponseEntity<>(dadosVeiculosPorUsuario, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PostMapping("/cadastrar/funcionario/{idEmpresa}")
	public ResponseEntity<?> cadastrarFuncionario(@RequestBody DadosCadastroFuncionario dadosCadastroFuncionario, @PathVariable Long idEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosCadastroFuncionario != null) {
			Usuario funcionario = cadastrarUsuarios.cadastrarFuncionario(dadosCadastroFuncionario);
			Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
			if(empresa != null) {
				empresa.getUsuarios().add(funcionario);
				repositorioEmpresa.save(empresa);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/usuario/{id}/vendas")
	public ResponseEntity<?> vendaPorFuncionario(@PathVariable Long id) {
		Usuario funcionario = repositorioUsuario.findById(id).orElse(null);
		if(funcionario != null && funcionario.getPerfis().contains(PerfilUsuario.FUNCIONARIO)) {
			adicionadorLink.adicionarLink(funcionario);
			Set<Venda> vendas = funcionario.getVendas();
			return new ResponseEntity<Set<Venda>>(vendas, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>("Funcionário não encontrado", HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE')")
	@PostMapping("/cadastrar/fornecedor/{idEmpresa}")
	public ResponseEntity<?> cadastrarFornecedor(@RequestBody DadosCadastroFornecedor dadosCadastroFornecedor, @PathVariable Long idEmpresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosCadastroFornecedor != null) {
			Usuario fornecedor = cadastrarUsuarios.cadastrarFornecedor(dadosCadastroFornecedor);
			Empresa empresa = repositorioEmpresa.findById(idEmpresa).orElse(null);
			if(empresa != null) {
				empresa.getUsuarios().add(fornecedor);
				repositorioEmpresa.save(empresa);
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Empresa não encontrada", HttpStatus.NOT_FOUND);
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@GetMapping("/usuario/{id}/mercadorias")
	public ResponseEntity<?> mercadoriaPorFornecedor(@PathVariable Long id) {
		Usuario fornecedor = repositorioUsuario.findById(id).orElse(null);
		if(fornecedor != null && fornecedor.getPerfis().contains(PerfilUsuario.FORNECEDOR)) {
			adicionadorLink.adicionarLink(fornecedor);
			DadosMercadoriaPorFornecedor mercadoriasPorFornecedor = listagemMercadoriaPorFornecedor.listarMercadorias(fornecedor);
			return new ResponseEntity<DadosMercadoriaPorFornecedor>(mercadoriasPorFornecedor, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<>("Fornecedor não encontrado", HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GERENTE', 'ROLE_VENDEDOR')")
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizarUsuario(@RequestBody DadosAtualizacaoUsuario dadosAtualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(dadosAtualizacao.id() != null) {
			Usuario usuario = repositorioUsuario.findById(dadosAtualizacao.id()).orElse(null);
			if(usuario != null && usuario.isAtivo()) {
				atualizador.atualizar(usuario, dadosAtualizacao);
				repositorioUsuario.save(usuario);
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
	@DeleteMapping("/excluir/{idUsuario}")
	public ResponseEntity<?> excluirUsuario(@PathVariable Long idUsuario) {
		HttpStatus status = HttpStatus.CONFLICT;
		if(idUsuario != null) {
			Usuario usuario = repositorioUsuario.findById(idUsuario).orElse(null);
			if(usuario != null && usuario.isAtivo()) {
				usuario.setAtivo(false);
				repositorioUsuario.save(usuario);
				status = HttpStatus.OK;
			} else {
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
}
