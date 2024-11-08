package com.autobots.automanager.controles;

import com.autobots.automanager.dto.UsuarioDto;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.modelo.adicionadorLink.*;
import com.autobots.automanager.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControle {
	
	@Autowired
	public UsuarioRepositorio repositorio;
	
	@Autowired
	public DocumentoRepositorio documentoRepositorio;
	
	@Autowired
	public EnderecoRepositorio enderecoRepositorio;
	
	@Autowired
	public TelefoneRepositorio telefoneRepositorio;
	
	@Autowired
	public CredencialRepositorio credencialRepositorio;
	
	@Autowired
	public EmailRepositorio emailRepositorio;
	
	@Autowired
	public MercadoriaRepositorio mercadoriaRepositorio;
	
	@Autowired
	public VendaRepositorio vendaRepositorio;
	
	@Autowired
	public VeiculoRepositorio veiculoRepositorio;
	
	@Autowired
	public AdicionadorLinkUsuario adicionadorLink;
	
	@Autowired
	public EmpresaRepositorio empresaRepositorio;
	@Autowired
	private AdicionadorLinkCredencial adicionadorLinkCredencial;
	@Autowired
	private AdicionadorLinkEmail adicionadorLinkEmail;
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLinkEndereco;
	
	@GetMapping("/{id}")
	public Usuario obterUsuario(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		adicionadorLink.adicionarLink(usuario);
		return usuario;
	}
	
	@GetMapping
	public List<Usuario> obterUsuarios() {
		List<Usuario> usuarios = repositorio.findAll();
		for(Usuario usuario : usuarios){
			usuario.getCredenciais().forEach(credencial -> adicionadorLinkCredencial.adicionarLink(credencial));
			usuario.getEmails().forEach(email -> adicionadorLinkEmail.adicionarLink(email));
			usuario.getVendas().forEach(venda -> adicionadorLinkVenda.adicionarLink(venda));
			usuario.getMercadorias().forEach(mercadoria -> adicionadorLinkMercadoria.adicionarLink(mercadoria));
			usuario.getVeiculos().forEach(veiculo -> adicionadorLinkVeiculo.adicionarLink(veiculo));
			if (usuario.getEndereco() != null){
				adicionadorLinkEndereco.adicionarLink(usuario.getEndereco());
			}
		}
		adicionadorLink.adicionarLink(usuarios);
		return usuarios;
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioDto usuarioDto) {
		try {
			List<PerfilUsuario> perfis = usuarioDto.getPerfis().stream()
					.map(perfil -> PerfilUsuario.valueOf(String.valueOf(perfil)))
					.collect(Collectors.toList());
			Usuario usuario = usuarioDto.toEntity(perfis);
			Usuario usuarioSalvo = repositorio.save(usuario);
			return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<UsuarioDto> atualizarUsuario(@RequestBody UsuarioDto usuarioDto, @PathVariable Long id) {
		Usuario usuarioExistente = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
		
		List<Documento> documentos = documentoRepositorio.findAllById(usuarioDto.getDocumentos());
		Endereco endereco = enderecoRepositorio.findById(usuarioDto.getEndereco()).orElse(null);
		List<Telefone> telefones = telefoneRepositorio.findAllById(usuarioDto.getTelefones());
		List<Email> emails = emailRepositorio.findAllById(usuarioDto.getEmails());
		List<Credencial> credenciais = credencialRepositorio.findAllById(usuarioDto.getCredenciais());
		List<Mercadoria> mercadorias = mercadoriaRepositorio.findAllById(usuarioDto.getMercadorias());
		List<Venda> vendas = vendaRepositorio.findAllById(usuarioDto.getVendas());
		List<Veiculo> veiculos = veiculoRepositorio.findAllById(usuarioDto.getVeiculos());
		
		usuarioDto.updateEntity(usuarioExistente, documentos, endereco, telefones, emails, usuarioDto.getPerfis(), credenciais, mercadorias, vendas, veiculos);
		
		Usuario usuarioAtualizado = repositorio.save(usuarioExistente);
		
		usuarioDto.setId(usuarioAtualizado.getId());
		
		return new ResponseEntity<>(usuarioDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Usuario> excluirUsuario(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		List<Empresa> empresas = empresaRepositorio.findAll();
		for(Empresa e : empresas){
			if(e.getUsuarios().contains(usuario)){
				e.getUsuarios().remove(usuario);
                empresaRepositorio.save(e);
			}
		}
		repositorio.delete(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
}
