package com.autobots.automanager.modelo.atualizadores;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelo.StringVerificadorNulo;

public class EmpresaAtualizadora {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	
	public void atualizar(Empresa empresa, Empresa atualizacao){
		if(atualizacao != null){
			if(!verificador.verificar(atualizacao.getVendas().toString())){
				empresa.setVendas(atualizacao.getVendas());
			}
			if(!verificador.verificar(atualizacao.getUsuarios().toString())){
                empresa.setUsuarios(atualizacao.getUsuarios());
            }
			if(!verificador.verificar(atualizacao.getRazaoSocial())){
                empresa.setRazaoSocial(atualizacao.getRazaoSocial());
            }
			if(!verificador.verificar(atualizacao.getNomeFantasia())){
                empresa.setNomeFantasia(atualizacao.getNomeFantasia());
            }
			if(!verificador.verificar(atualizacao.getTelefones().toString())){
                empresa.setTelefones(atualizacao.getTelefones());
            }
			if(!verificador.verificar(atualizacao.getEndereco().toString())){
                empresa.setEndereco(atualizacao.getEndereco());
            }
			if(!verificador.verificar(atualizacao.getMercadorias().toString())){
                empresa.setMercadorias(atualizacao.getMercadorias());
            }
			if(!verificador.verificar(atualizacao.getCadastro().toString())){
                empresa.setCadastro(atualizacao.getCadastro());
            }
			if(!verificador.verificar(atualizacao.getUsuarios().toString())){
				empresa.setUsuarios(atualizacao.getUsuarios());
			}
			if(!verificador.verificar(atualizacao.getServicos().toString())){
				empresa.setServicos(atualizacao.getServicos());
			}
		}
	}
}
