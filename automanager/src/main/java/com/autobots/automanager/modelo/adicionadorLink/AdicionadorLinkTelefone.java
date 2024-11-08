package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {
	
	@Override
	public void adicionarLink(List<Telefone> lista) {
		for (Telefone telefone : lista) {
			Long id = telefone.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.obterTelefone(id))
					.withSelfRel();
			telefone.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Telefone objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.obterTelefones())
				.withRel("ListarTodosOsTelefones");
		objeto.add(linkProprio);
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .atualizarTelefone(objeto, id))
                .withRel("AtualizarTelefone:");
        objeto.add(linkAtualizar);
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneControle.class)
                        .excluirTelefone(id))
                .withRel("DeletarTelefone:");
        objeto.add(linkDeletar);
	}
}