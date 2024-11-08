package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.entidades.Mercadoria;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<Mercadoria> {
	
	@Override
	public void adicionarLink(List<Mercadoria> lista) {
		for (Mercadoria mercadoria : lista) {
			long id = mercadoria.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaControle.class)
							.obterMercadoria(id))
					.withSelfRel();
			mercadoria.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Mercadoria objeto) {
		long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaControle.class)
						.obterMercadorias())
				.withSelfRel();
		Link linkDel = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(MercadoriaControle.class)
                        .excluirMercadoria(id))
                .withRel("deletarMercadoria");
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(MercadoriaControle.class)
                        .atualizarMercadoria(objeto, id))
				.withRel("atualizarMercadoria");
        objeto.add(linkProprio);
		objeto.add(linkDel);
		objeto.add(linkAtualizar);
	}
}
