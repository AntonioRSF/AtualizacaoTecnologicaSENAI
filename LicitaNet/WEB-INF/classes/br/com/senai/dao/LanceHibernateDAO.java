package br.com.senai.dao;

import java.util.List;

import org.hibernate.Query;

import br.com.senai.entity.Lance;
import br.com.senai.entity.Usuario;

@SuppressWarnings("rawtypes")
public class LanceHibernateDAO extends GenericDAOHibernate<Lance>{

	@SuppressWarnings("unchecked")
	public LanceHibernateDAO(Class classe) {
		super(classe);
	}
	
	public Lance recuperaMenorLanceOferta(int idOferta){
		Query query = getSession().createQuery("select l from Lance l where l.valor = (select min(l2.valor) from Lance l2 where l2.oferta = :cdOferta ) and l.oferta = :cdOferta ");
		query.setInteger("cdOferta", idOferta);
		return (Lance) query.uniqueResult();
	}
	
	public Lance recuperaMenorLanceUsuarioNaOferta(int idOferta, int idUsuario){
		Query query = getSession().createQuery("select l from Lance l where l.valor = (select min(l2.valor) from Lance l2 where l2.oferta.id = :cdOferta and l2.usuario.id = :idUsuario ) and l.oferta = :cdOferta ");
		query.setInteger("cdOferta", idOferta);
		query.setInteger("idUsuario", idUsuario);
		return (Lance) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Lance> recuperaLancesPorOferta(int idOferta){
		Query query = getSession().createQuery("from Lance l where l.oferta.id = :idOferta ORDER BY l.data desc");
		query.setInteger("idOferta", idOferta);
		return query.list();
	}
	
	public boolean valorLanceExiste(Double valorLance, int idOferta){
		Query query = getSession().createQuery("from Lance l where l.valor = :valorLance AND l.oferta.id = :idOferta");
		query.setDouble("valorLance", valorLance);
		query.setInteger("idOferta", idOferta);
		if(query.uniqueResult() == null)
			return false;
		else
			return true;
	}
	
	public Usuario recuperaGanhadorOferta(Integer idOferta){
		Query query = getSession().createQuery("select u from usuario u where u.id = (select l.idUsuario from lance l where l.idOferta = :idOferta and l.flVencedor = true)");
		query.setParameter("idOferta", idOferta);
		return (Usuario) query.uniqueResult();
	}

}
