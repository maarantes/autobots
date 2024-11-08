package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.entidades.Venda;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<Venda> {
	
	@Override
	public void adicionarLink(List<Venda> lista) {
		for (Venda venda : lista) {
			long id = venda.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaControle.class)
							.obterVenda(id))
					.withSelfRel();
			venda.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Venda objeto) {
		long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaControle.class)
						.obterVendas())
				.withSelfRel();
		objeto.add(linkProprio);
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .atualizarVenda(null, id))
                .withRel("AtualizarVenda:");
        objeto.add(linkAtualizar);
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(VendaControle.class)
                        .excluirVenda(id))
                .withRel("DeletarVenda:");
        objeto.add(linkDeletar);
	}
}
