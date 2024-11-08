package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {
	@Override
	public void adicionarLink(List<Documento> lista) {
		for (Documento documento : lista) {
			Long id = documento.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentoControle.class)
							.obterDocumento(id))
					.withSelfRel();
			documento.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Documento objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.obterDocumentos())
				.withRel("documentos");
		objeto.add(linkProprio);
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
					.methodOn(DocumentoControle.class)
				    .excluirDocumento(id))
				.withRel("deletar");
		objeto.add(linkDeletar);
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoControle.class)
                        .atualizarDocumento(objeto, id))
                .withRel("atualizar");
        objeto.add(linkAtualizar);
	}
}
