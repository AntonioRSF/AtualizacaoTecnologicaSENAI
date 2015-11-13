package br.com.senai.dao;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.hibernate.Query;
import br.com.senai.entity.Usuario;

public class UsuarioHibernateDAO extends GenericDAOHibernate<Usuario> {
	
	public UsuarioHibernateDAO(Class<Usuario> classe) {
		super(classe);
	}

	public Usuario recuperaUsuarioLogado(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		String login = external.getRemoteUser();
		
		Query query = getSession().createQuery("from Usuario u where u.login = :login");
		query.setParameter("login", login);
		return (Usuario) query.uniqueResult();
	}
}
