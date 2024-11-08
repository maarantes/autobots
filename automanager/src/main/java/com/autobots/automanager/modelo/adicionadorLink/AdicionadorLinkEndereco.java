package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.entidades.Endereco;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEndereco implements AdicionadorLink<Endereco> {
	
	@Override
	public void adicionarLink(List<Endereco> lista) {
		for (Endereco endereco : lista) {
			long id = endereco.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EnderecoControle.class)
							.obterEndereco(id))
					.withSelfRel();
			endereco.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Endereco objeto) {
		long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.obterEnderecos())
				.withRel("ListarEnderecos");
		Link linkDeletar =  WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.excluirEndereco(id))
				.withRel("DeletarEndereco:");
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EnderecoControle.class)
						.atualizarEndereco(objeto, id))
				.withRel("AtualizarEndereco:");
		objeto.add(linkProprio);
		objeto.add(linkDeletar);
		objeto.add(linkAtualizar);
	}
}
