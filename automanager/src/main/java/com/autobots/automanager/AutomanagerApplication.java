package com.autobots.automanager;

import java.util.Date;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.TipoDocumento;
import com.autobots.automanager.enumeracoes.TipoVeiculo;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {
	
	@Autowired
	private EmpresaRepositorio repositorioEmpresa;
	
	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Volvo Industries");
		empresa.setNomeFantasia("Peças Volvo Industries");
		empresa.setCadastro(new Date());
		
		Endereco enderecoEmpresa = new Endereco();
		enderecoEmpresa.setEstado("Suécia");
		enderecoEmpresa.setCidade("Gotemburgo");
		enderecoEmpresa.setBairro("Linnestaden");
		enderecoEmpresa.setRua("Sodra Hamngatan");
		enderecoEmpresa.setNumero("24");
		enderecoEmpresa.setCodigoPostal("24120-200");
		
		empresa.setEndereco(enderecoEmpresa);
		
		Telefone telefoneEmpresa = new Telefone();
		telefoneEmpresa.setDdd("012");
		telefoneEmpresa.setNumero("49228092");
		
		empresa.getTelefones().add(telefoneEmpresa);
		
		Usuario funcionario = new Usuario();
		funcionario.setNome("Marco Antonio Filho");
		funcionario.setNomeSocial("Marquinho");
		funcionario.getPerfis().add(PerfilUsuario.FUNCIONARIO);
		
		Email emailFuncionario = new Email();
		emailFuncionario.setEndereco("marcoantoniopou@gmail.com");
		
		funcionario.getEmails().add(emailFuncionario);
		
		Endereco enderecoFuncionario = new Endereco();
		enderecoFuncionario.setEstado("São José dos Campos");
		enderecoFuncionario.setCidade("São Paulo");
		enderecoFuncionario.setBairro("Bairro Legal");
		enderecoFuncionario.setRua("Rua Legal");
		enderecoFuncionario.setNumero("123");
		enderecoFuncionario.setCodigoPostal("12345-000");
		
		funcionario.setEndereco(enderecoFuncionario);
		
		empresa.getUsuarios().add(funcionario);
		
		Telefone telefoneFuncionario = new Telefone();
		telefoneFuncionario.setDdd("012");
		telefoneFuncionario.setNumero("1299000000");
		
		funcionario.getTelefones().add(telefoneFuncionario);
		
		Documento cpf = new Documento();
		cpf.setDataEmissao(new Date());
		cpf.setNumero("5579589002");
		cpf.setTipo(TipoDocumento.CPF);
		
		funcionario.getDocumentos().add(cpf);
		
		CredencialUsuarioSenha credencialFuncionario = new CredencialUsuarioSenha();
		credencialFuncionario.setInativo(false);
		credencialFuncionario.setNomeUsuario("marcofilho");
		credencialFuncionario.setSenha("123");
		credencialFuncionario.setCriacao(new Date());
		credencialFuncionario.setUltimoAcesso(new Date());
		
		funcionario.getCredenciais().add(credencialFuncionario);
		
		Usuario fornecedor = new Usuario();
		fornecedor.setNome("Fornecedora de Peças Volvo LTDA");
		fornecedor.setNomeSocial("Nós temos as melhores peças para customizar seu carro Volvo!");
		fornecedor.getPerfis().add(PerfilUsuario.FORNECEDOR);
		
		Email emailFornecedor = new Email();
		emailFornecedor.setEndereco("fornecedor@motor.com");
		
		fornecedor.getEmails().add(emailFornecedor);
		
		CredencialUsuarioSenha credencialFornecedor = new CredencialUsuarioSenha();
		credencialFornecedor.setInativo(false);
		credencialFornecedor.setNomeUsuario("fornecedor");
		credencialFornecedor.setSenha("123");
		credencialFornecedor.setCriacao(new Date());
		credencialFornecedor.setUltimoAcesso(new Date());
		
		fornecedor.getCredenciais().add(credencialFornecedor);
		
		Documento cnpj = new Documento();
		cnpj.setDataEmissao(new Date());
		cnpj.setNumero("123000000000");
		cnpj.setTipo(TipoDocumento.CNPJ);
		
		fornecedor.getDocumentos().add(cnpj);
		
		Endereco enderecoFornecedor = new Endereco();
		enderecoFornecedor.setEstado("Vastra Gotaland");
		enderecoFornecedor.setCidade("Boras");
		enderecoFornecedor.setBairro("Gota");
		enderecoFornecedor.setRua("Bergson Nascimento");
		enderecoFornecedor.setNumero("123");
		enderecoFornecedor.setCodigoPostal("56789-0000");
		
		fornecedor.setEndereco(enderecoFornecedor);
		
		empresa.getUsuarios().add(fornecedor);
		
		Mercadoria AerofolioChique = new Mercadoria();
		AerofolioChique.setCadastro(new Date());
		AerofolioChique.setFabricacao(new Date());
		AerofolioChique.setNome("Aerofólio Volvo V4000");
		AerofolioChique.setValidade(new Date());
		AerofolioChique.setQuantidade(30);
		AerofolioChique.setValor(1200.0);
		AerofolioChique.setDescricao("Este aerofólio da Volvo vai deixar seu carro muito mais descolado!");
		
		empresa.getMercadorias().add(AerofolioChique);
		
		fornecedor.getMercadorias().add(AerofolioChique);
		
		Usuario cliente = new Usuario();
		cliente.setNome("Bruna Maciel Arantes");
		cliente.setNomeSocial("Bruninha");
		cliente.getPerfis().add(PerfilUsuario.CLIENTE);
		
		Email emailCliente = new Email();
		emailCliente.setEndereco("bruna@maciel.com");
		
		cliente.getEmails().add(emailCliente);
		
		Documento cpfCliente = new Documento();
		cpfCliente.setDataEmissao(new Date());
		cpfCliente.setNumero("997712345");
		cpfCliente.setTipo(TipoDocumento.CPF);
		
		cliente.getDocumentos().add(cpfCliente);
		
		CredencialUsuarioSenha credencialCliente = new CredencialUsuarioSenha();
		credencialCliente.setInativo(false);
		credencialCliente.setNomeUsuario("brunamaciel");
		credencialCliente.setSenha("123");
		credencialCliente.setCriacao(new Date());
		credencialCliente.setUltimoAcesso(new Date());
		
		cliente.getCredenciais().add(credencialCliente);
		
		Endereco enderecoCliente = new Endereco();
		enderecoCliente.setEstado("Uppsala Lan");
		enderecoCliente.setCidade("Uppsala");
		enderecoCliente.setBairro("Kopenhagen");
		enderecoCliente.setRua("Kungsgatan");
		enderecoCliente.setNumero("475");
		enderecoCliente.setCodigoPostal("12120-000");


		
		cliente.setEndereco(enderecoCliente);
		
		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca("ABC-4091");
		veiculo.setModelo("Volvo Speedster");
		veiculo.setTipo(TipoVeiculo.SUV);
		veiculo.setProprietario(cliente);
		
		cliente.getVeiculos().add(veiculo);
		
		empresa.getUsuarios().add(cliente);
		
		Servico TrocaOleo = new Servico();
		TrocaOleo.setDescricao("O óleo do carro tava velho daí achamos melhor trocar");
		TrocaOleo.setNome("Troca de óleo");
		TrocaOleo.setValor(250);
		
		Servico InstalacaoAerofolio = new Servico();
		InstalacaoAerofolio.setDescricao("O cliente queria um aerofólio pra deixar o carro dele mais descolado");
		InstalacaoAerofolio.setNome("Instalação de Aerofólio");
		InstalacaoAerofolio.setValor(1500);
		
		empresa.getServicos().add(TrocaOleo);
		empresa.getServicos().add(InstalacaoAerofolio);
		
		Venda venda = new Venda();
		venda.setCadastro(new Date());
		venda.setCliente(cliente);
		venda.getMercadorias().add(AerofolioChique);
		venda.setIdentificacao("123450000");
		venda.setFuncionario(funcionario);
		venda.getServicos().add(TrocaOleo);
		venda.getServicos().add(InstalacaoAerofolio);
		venda.setVeiculo(veiculo);
		veiculo.getVendas().add(venda);
		
		empresa.getVendas().add(venda);
		
		repositorioEmpresa.save(empresa);
	}
}