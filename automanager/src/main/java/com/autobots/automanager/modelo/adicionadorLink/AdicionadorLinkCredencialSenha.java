package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.CredencialControle;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkCredencialSenha implements AdicionadorLink<CredencialUsuarioSenha> {
	
	@Override
	public void adicionarLink(List<CredencialUsuarioSenha> lista) {
		for (CredencialUsuarioSenha credencialUsuarioSenha : lista) {
			adicionarLinkParaListagem(credencialUsuarioSenha);
		}
	}
	
	@Override
	public void adicionarLink(CredencialUsuarioSenha objeto) {
		adicionarLinkParaDetalhe(objeto);
	}
	
	public void adicionarLinkParaListagem(CredencialUsuarioSenha objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredencial(id))
				.withSelfRel();
		objeto.add(linkProprio);
	}
	
	public void adicionarLinkParaDetalhe(CredencialUsuarioSenha objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredencial(id))
				.withSelfRel();
		objeto.add(linkProprio);
		
		Link linkListar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.obterCredenciais())
				.withRel("listarTodasAsCredenciais");
		objeto.add(linkListar);
		
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.excluirCredencial(id))
				.withRel("deletar");
		objeto.add(linkDeletar);
		
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(CredencialControle.class)
						.atualizarCredencial(null, id, null))
				.withRel("atualizar");
		objeto.add(linkAtualizar);
	}
}
