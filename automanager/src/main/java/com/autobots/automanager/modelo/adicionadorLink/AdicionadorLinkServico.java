package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.entidades.Servico;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class  AdicionadorLinkServico implements AdicionadorLink<Servico> {
	
	@Override
	public void adicionarLink(List<Servico> lista) {
		for (Servico servico : lista) {
			long id = servico.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoControle.class)
							.obterServico(id))
					.withSelfRel();
			servico.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Servico objeto) {
		long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(ServicoControle.class)
						.obterServicos())
				.withRel("ListarServicos");
		objeto.add(linkProprio);
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControle.class)
                        .atualizarServico(objeto, id))
                .withRel("AtualizarServico:");
        objeto.add(linkAtualizar);
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(ServicoControle.class)
                        .excluirServico(id))
                .withRel("DeletarServico:");
        objeto.add(linkDeletar);
	}
}
