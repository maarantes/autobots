package com.autobots.automanager.controles;

import com.autobots.automanager.dto.VeiculoDto;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelo.adicionadorLink.*;
import com.autobots.automanager.modelo.selecionadores.VeiculoSelecionador;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import com.autobots.automanager.repositorios.VendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControle {
	
	@Autowired
	private VeiculoRepositorio veiculoRepositorio;
	
	@Autowired
	private VeiculoSelecionador veiculoSelecionador;
	
	@Autowired
	public VendaRepositorio vendaRepositorio;
	
	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLink;
	
	@Autowired
	private AdicionadorLinkVenda adicionadorLinkVenda;
	
	@Autowired
	private AdicionadorLinkUsuario adicionadorLinkUsuario;
	
	@Autowired
	private AdicionadorLinkMercadoria adicionadorLinkMercadoria;
	
	@Autowired
	private AdicionadorLinkServico adicionadorLinkServico;
	
	@GetMapping("/{id}")
	public Veiculo obterVeiculo(@PathVariable Long id) {
		Veiculo veiculo = veiculoRepositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		adicionadorLink.adicionarLink(veiculo);
		return veiculo;
	}
	
	@GetMapping
	public List<Veiculo> obterVeiculos() {
		List<Veiculo> veiculos = veiculoRepositorio.findAll();
		for(Veiculo veiculo : veiculos){
			for(Venda venda : veiculo.getVendas()){
				venda.getMercadorias().forEach(mercadoria -> adicionadorLinkMercadoria.adicionarLink(mercadoria));
				venda.getServicos().forEach(servico -> adicionadorLinkServico.adicionarLink(servico));
				adicionadorLinkVenda.adicionarLink(venda);
			}
		}
		adicionadorLink.adicionarLink(veiculos);
		return veiculos;
	}
	
	@PostMapping("/cadastrar/{idVenda}/{idUsuario}")
	public ResponseEntity<Veiculo> cadastrarVeiculoVenda(@RequestBody VeiculoDto veiculoDto, @PathVariable Long idVenda, @PathVariable Long idUsuario) {
		Venda venda = vendaRepositorio.findById(idVenda)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada com ID: " + idVenda));
		Usuario proprietario = usuarioRepositorio.findById(idUsuario)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado com ID: " + idUsuario));
		
		Veiculo veiculo = veiculoDto.toEntity();
		veiculo.getVendas().add(venda);
		veiculo.setProprietario(proprietario);
		veiculoRepositorio.save(veiculo);
		return new ResponseEntity<Veiculo>(veiculo, HttpStatus.CREATED);
	}
	
	@PutMapping("/atualizar/{idVeiculo}")
	public ResponseEntity<VeiculoDto> atualizarVeiculo(@RequestBody VeiculoDto veiculoDto, @PathVariable Long idVeiculo) {
		Veiculo veiculoExistente = veiculoRepositorio.findById(idVeiculo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado com ID: " + idVeiculo));
		
		veiculoExistente.setTipo(veiculoDto.getTipo());
		veiculoExistente.setModelo(veiculoDto.getModelo());
		veiculoExistente.setPlaca(veiculoDto.getPlaca());
		
		if (veiculoDto.getVendas() != null && !veiculoDto.getVendas().isEmpty()) {
			Set<Venda> vendas = veiculoDto.getVendas().stream()
					.map(vendaId -> vendaRepositorio.findById(vendaId)
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada com ID: " + vendaId)))
					.collect(Collectors.toSet());
			veiculoExistente.setVendas(vendas);
		}
		
		if (veiculoDto.getProprietario() != null) {
			Usuario proprietario = usuarioRepositorio.findById(veiculoDto.getProprietario())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + veiculoDto.getProprietario()));
			veiculoExistente.setProprietario(proprietario);
		}
		veiculoRepositorio.save(veiculoExistente);
		
		VeiculoDto veiculoAtualizadoDto = new VeiculoDto();
		veiculoAtualizadoDto.setId(veiculoExistente.getId());
		veiculoAtualizadoDto.setTipo(veiculoExistente.getTipo());
		veiculoAtualizadoDto.setModelo(veiculoExistente.getModelo());
		veiculoAtualizadoDto.setPlaca(veiculoExistente.getPlaca());
		veiculoAtualizadoDto.setVendas(veiculoExistente.getVendas().stream().map(Venda::getId).collect(Collectors.toSet()));
		veiculoAtualizadoDto.setProprietario(veiculoExistente.getProprietario().getId());
		
		return ResponseEntity.ok(veiculoAtualizadoDto);
	}
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<Veiculo> excluirVeiculo(@PathVariable Long id) {
		Veiculo veiculo = veiculoRepositorio.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		veiculoRepositorio.delete(veiculo);
		return new ResponseEntity<>(veiculo, HttpStatus.OK);
	}
}
