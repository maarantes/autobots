package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkEndereco;
import com.autobots.automanager.modelo.adicionadorLink.AdicionadorLinkUsuario;
import com.autobots.automanager.modelo.atualizadores.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoControle {

	@Autowired
	public EnderecoRepositorio repositorio;
	
	@Autowired
	public AdicionadorLinkEndereco adicionadorLink;
	
	@Autowired
	public EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLinkEmpresa;
	
	@PostMapping("/cadastrar/usuario/{idUsuario}")
	public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco endereco, @PathVariable Long idUsuario) {
		Usuario usuario = usuarioRepositorio.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		endereco = repositorio.save(endereco);
		usuario.setEndereco(endereco);
		usuarioRepositorio.save(usuario);
		return new ResponseEntity<>(endereco, HttpStatus.CREATED);
	}
	
	@PostMapping("/cadastrar/empresa/{idEmpresa}")
	public ResponseEntity<Endereco>  cadastrarEnderecoEmpresa(@RequestBody Endereco endereco, @PathVariable Long idEmpresa) {
		Empresa empresa = empresaRepositorio.findById(idEmpresa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));;
		endereco = repositorio.save(endereco);
		empresa.setEndereco(endereco);
		empresaRepositorio.save(empresa);
		repositorio.save(endereco);
		return new ResponseEntity<>(endereco, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable Long id) {
        Endereco endereco = repositorio.findById(id)
		        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        adicionadorLink.adicionarLink(endereco);
        return new ResponseEntity<>(endereco, HttpStatus.OK);
    }
	
	@GetMapping
	public List<Endereco> obterEnderecos(){
		List<Endereco> enderecos = repositorio.findAll();
		adicionadorLink.adicionarLink(enderecos);
		return enderecos;
	}
	
	@PutMapping("/atualizar/{id}")
	public ResponseEntity<Endereco> atualizarEndereco(@RequestBody Endereco e, @PathVariable Long id) {
		Endereco endereco = repositorio.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço not found"));
		endereco.setBairro(endereco.getBairro());
		endereco.setCidade(endereco.getCidade());
		endereco.setEstado(endereco.getEstado());
		endereco.setRua(endereco.getRua());
		endereco.setCodigoPostal(endereco.getCodigoPostal());
		endereco.setNumero(endereco.getNumero());
		endereco.setInformacoesAdicionais(endereco.getInformacoesAdicionais());
		repositorio.save(endereco);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Endereco> excluirEndereco(@PathVariable long id) {
		Endereco endereco = repositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		List<Empresa> empresas = empresaRepositorio.findAll();
		for(Empresa e : empresas){
			if(e.getEndereco().getId().equals(endereco.getId())){
                e.setEndereco(null);
                empresaRepositorio.save(e);
            }
		}
		repositorio.delete(endereco);
		return new ResponseEntity<>(endereco, HttpStatus.OK);
	}
}
