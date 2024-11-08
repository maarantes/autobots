package com.autobots.automanager.controles;

import com.autobots.automanager.dto.EmpresaDto;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.modelo.adicionadorLink.*;
import com.autobots.automanager.modelo.atualizadores.EmpresaAtualizadora;
import com.autobots.automanager.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
	
	@Autowired
	public EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	public TelefoneRepositorio telefoneRepositorio;
	
	@Autowired
	public EnderecoRepositorio enderecoRepositorio;
	
	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	public MercadoriaRepositorio mercadoriaRepositorio;
	
	@Autowired
	public ServicoRepositorio servicoRepositorio;
	
	@Autowired
	public VendaRepositorio vendaRepositorio;
	
	@Autowired
	public AdicionadorLinkEmpresa adicionadorLink;
	
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLinkEmpresa;
	
	@Autowired
	private AdicionadorLinkTelefone adicionadorLinkTelefone;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	@Autowired
	private AdicionadorLinkServico adicionadorLinkServico;
	
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;
	
	@Autowired
	private AdicionadorLinkEndereco adicionadorLinkEndereco;
	
	@Autowired
	private AdicionadorLinkDocumento adicionadorLinkDocumento;
	
	@Autowired
	private AdicionadorLinkEmail adicionadorLinkEmail;
	
	@Autowired
	private AdicionadorLinkCredencial adicionadorLinkCredencial;
	
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
	
	@GetMapping("/{id}")
	public Empresa obterEmpresa(@PathVariable Long id) {
		Empresa empresa = empresaRepositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		adicionadorLink.adicionarLink(empresa);
		return empresa;
	}
	
	@GetMapping
	public List<Empresa> obterEmpresas() {
		List<Empresa> empresas = empresaRepositorio.findAll();
		for (Empresa empresa : empresas) {
			empresa.getTelefones().forEach(telefone -> adicionadorLinkTelefone.adicionarLink(telefone));
			empresa.getUsuarios().forEach(usuario -> {
				adicionadorLinkUsuario.adicionarLink(usuario);
				usuario.getTelefones().forEach(telefone -> adicionadorLinkTelefone.adicionarLink(telefone));
				usuario.getDocumentos().forEach(documento -> adicionadorLinkDocumento.adicionarLink(documento));
				usuario.getEmails().forEach(email -> adicionadorLinkEmail.adicionarLink(email));
				usuario.getCredenciais().forEach(credencial -> adicionadorLinkCredencial.adicionarLink(credencial));
				usuario.getMercadorias().forEach(mercadoria -> adicionadorLinkMercadoria.adicionarLink(mercadoria));
				usuario.getVendas().forEach(venda -> adicionadorLinkVenda.adicionarLink(venda));
				usuario.getVeiculos().forEach(veiculo -> adicionadorLinkVeiculo.adicionarLink(veiculo));
			});
			empresa.getMercadorias().forEach(mercadoria -> adicionadorLinkMercadoria.adicionarLink(mercadoria));
			empresa.getServicos().forEach(servico -> adicionadorLinkServico.adicionarLink(servico));
			empresa.getVendas().forEach(venda -> adicionadorLinkVenda.adicionarLink(venda));
			if (empresa.getEndereco() != null) {
				adicionadorLinkEndereco.adicionarLink(empresa.getEndereco());
			}
		}
		adicionadorLinkEmpresa.adicionarLink(empresas);
		return empresas;
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody EmpresaDto empresaDto) {
		Empresa empresa = empresaDto.cadastro();
		empresaRepositorio.save(empresa);
		return new ResponseEntity<Empresa>(empresa, HttpStatus.CREATED);
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Empresa> atualizarEmpresa(@RequestBody EmpresaDto empresaDto, @PathVariable Long id) {
		Empresa empresaExistente = empresaRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa not found"));
		
		Set<Telefone> telefonesAtualizados = telefoneRepositorio.findAllById(empresaDto.getTelefones())
				.stream().collect(Collectors.toSet());
		Endereco endereco = enderecoRepositorio.findById(empresaDto.getEndereco())
				.orElse(null);
		Set<Usuario> usuarios = usuarioRepositorio.findAllById(empresaDto.getUsuarios())
				.stream().collect(Collectors.toSet());
		Set<Mercadoria> mercadorias = mercadoriaRepositorio.findAllById(empresaDto.getMercadorias())
				.stream().collect(Collectors.toSet());
		Set<Servico> servicos = servicoRepositorio.findAllById(empresaDto.getServicos())
				.stream().collect(Collectors.toSet());
		Set<Venda> vendas = vendaRepositorio.findAllById(empresaDto.getVendas())
				.stream().collect(Collectors.toSet());
		
		empresaDto.updateEntity(empresaExistente, telefonesAtualizados, endereco, usuarios, mercadorias, servicos, vendas);
		
		empresaRepositorio.save(empresaExistente);
		return new ResponseEntity<>(empresaExistente, HttpStatus.OK);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Empresa> excluirEmpresa(@PathVariable Long id) {
		Empresa empresa = empresaRepositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		empresa.setUsuarios(null);
		empresaRepositorio.save(empresa);
		empresaRepositorio.delete(empresa);
		return new ResponseEntity<>(empresa, HttpStatus.OK);
	}
}