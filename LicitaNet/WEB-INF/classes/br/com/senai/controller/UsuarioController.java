package br.com.senai.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputHidden;
import br.com.senai.dao.GenericDAO;
import br.com.senai.dao.util.DAOFactory;
import br.com.senai.entity.Usuario;

@ManagedBean
@RequestScoped
public class UsuarioController {

	private GenericDAO<Usuario> usuarioDAO;
	private Usuario usuario;
	private HtmlInputHidden hidden;
	private List<Usuario> usuarios;
	private String permissao;

	public UsuarioController(){		
		this.usuarioDAO = DAOFactory.criarUsuarioDao();
		usuario = new Usuario();
		listar();
	}
	
	public String salvar(){
		Integer id = usuario.getId();		
		usuario.setTipo(hidden.getValue().toString());
		if(id == null || id == 0){
			this.usuarioDAO.salvar(usuario);
			listar();
		} else{
			this.usuarioDAO.atualizar(usuario);
			listar();
		}
		return "listarUsuarios";
	}
	
	public void excluir(){
		this.usuarioDAO.excluir(usuario);
		listar();
	}
	
	public List<Usuario> listar(){
		usuarios = this.usuarioDAO.listar();
		return usuarios;
	}
	
	public String editarUsuario(){
		if(usuario.getTipo().equals("PF"))
			return "/pages/admin/cadUsuarioPF.jsf";
		else
			return "/pages/admin/cadUsuarioPJ.jsf";
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public HtmlInputHidden getHidden() {
		return hidden;
	}

	public void setHidden(HtmlInputHidden hidden) {
		this.hidden = hidden;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getPermissao() {
		Set<String> permissaoSet = usuario.getPermissao();
		if(permissaoSet != null && !permissaoSet.isEmpty()){
			permissao = (String) permissaoSet.toArray()[0];
		}
		return permissao;
	}

	public void setPermissao(String permissao) {
		Set<String> permissaoSet = new HashSet<String>();
		permissaoSet.add(permissao);		
		usuario.setPermissao(permissaoSet);
		this.permissao = permissao;
	}
}
