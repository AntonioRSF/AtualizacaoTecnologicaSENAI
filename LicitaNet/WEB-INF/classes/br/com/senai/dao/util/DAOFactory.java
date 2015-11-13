package br.com.senai.dao.util;

import br.com.senai.dao.LanceHibernateDAO;
import br.com.senai.dao.OfertaHibernateDAO;
import br.com.senai.dao.ProdutoHibernateDAO;
import br.com.senai.dao.UsuarioHibernateDAO;
import br.com.senai.entity.Lance;
import br.com.senai.entity.Oferta;
import br.com.senai.entity.Produto;
import br.com.senai.entity.Usuario;

public class DAOFactory {
	
	public static UsuarioHibernateDAO criarUsuarioDao(){
		UsuarioHibernateDAO usuarioDAO = new UsuarioHibernateDAO(Usuario.class);
		usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return usuarioDAO;
	}
	
	public static OfertaHibernateDAO criarOfertaDao(){
		OfertaHibernateDAO ofertaDAO = new OfertaHibernateDAO(Oferta.class);
		ofertaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return ofertaDAO;
	}
	
	public static ProdutoHibernateDAO criarProdutoDao(){
		ProdutoHibernateDAO produtoDAO = new ProdutoHibernateDAO(Produto.class);
		produtoDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return produtoDAO;
	}
	
	public static LanceHibernateDAO criarLanceDao(){
		LanceHibernateDAO lanceDAO = new LanceHibernateDAO(Lance.class);
		lanceDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
		return lanceDAO;
	}
	
}
