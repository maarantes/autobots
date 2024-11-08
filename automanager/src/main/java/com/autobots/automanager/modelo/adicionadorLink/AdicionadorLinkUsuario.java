package com.autobots.automanager.modelo.adicionadorLink;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.Usuario;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<Usuario> {
	
	@Override
	public void adicionarLink(List<Usuario> lista) {
		for (Usuario usuario : lista) {
			Long id = usuario.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(UsuarioControle.class)
							.obterUsuario(id))
					.withSelfRel();
			usuario.add(linkProprio);
		}
	}
	
	@Override
	public void adicionarLink(Usuario objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(UsuarioControle.class)
						.obterUsuarios())
				.withSelfRel();
		objeto.add(linkProprio);
		Link linkAtualizar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .atualizarUsuario(null, id))
                .withRel("AtualizarUsuario:");
        objeto.add(linkAtualizar);
		Link linkDeletar = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
                        .methodOn(UsuarioControle.class)
                        .excluirUsuario(id))
                .withRel("DeletarUsuario:");
        objeto.add(linkDeletar);
	}
}
