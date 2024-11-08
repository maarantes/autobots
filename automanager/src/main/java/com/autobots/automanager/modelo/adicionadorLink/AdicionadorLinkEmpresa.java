package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.entidades.Empresa;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {
	@Override
	public void adicionarLink(List<Empresa> lista) {
		for (Empresa empresa : lista) {
			long id = empresa.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(EmpresaControle.class)
							.obterEmpresa(id))
					.withSelfRel();
			empresa.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Empresa objeto) {
		long id = objeto.getId();
		Link linkLista = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.obterEmpresas())
				.withRel("listarTodasAsEmpresas");
		Link linkDel = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(EmpresaControle.class)
						.excluirEmpresa(id))
				.withRel("deletarEmpresa");
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(EmpresaControle.class)
                        .atualizarEmpresa(null, id))
                .withRel("atualizarEmpresa");
		
		objeto.add(linkDel);
		objeto.add(linkLista);
		objeto.add(linkAtualizar);
	}
}