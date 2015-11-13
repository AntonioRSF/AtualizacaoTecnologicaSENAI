package br.com.senai.controller;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class MenuController {

	public String home(){
		return "/pages/publico/admin.jsf";
	}
	
	public String usuarioCadPF(){
		return "/pages/admin/cadUsuarioPF.jsf";
	}
	
	public String usuarioCadPJ(){
		return "/pages/admin/cadUsuarioPJ.jsf";
	}
	
	public String usuarioConsultar(){
		return "/pages/admin/listarUsuarios.jsf";
	}
	
	public String produtoNovo(){
		return "/pages/admin/cadProduto.jsf";
	}
	
	public String produtoConsultar(){
		return "/pages/admin/listarProdutos.jsf";
	}
	
	public String produtoAlterar(){
		return "/pages/admin/cadProduto.jsf";
	}
	
	public String ofertaNovo(){
		return "../admin/cadOferta.jsf";
	}
	
	public String ofertaConsultar(){
		return "../admin/listarOfertas.jsf";
	}
	
	public String leiloes(){
		return "/pages/lancador/leiloes.jsf";
	}
	
	public String lances(){
		return "/pages/lancador/lances.jsf";
	}
}
