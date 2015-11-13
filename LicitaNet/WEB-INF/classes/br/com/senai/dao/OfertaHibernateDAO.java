package br.com.senai.dao;

import java.util.List;
import org.hibernate.Query;
import br.com.senai.entity.Oferta;

@SuppressWarnings("rawtypes")
public class OfertaHibernateDAO extends GenericDAOHibernate<Oferta> {

	@SuppressWarnings("unchecked")
	public OfertaHibernateDAO(Class classe) {
		super(classe);
	}
	
	@SuppressWarnings("unchecked")
	public List<Oferta> recuperaOfertasNaoFinalizadas(){
		Query query = getSession().createQuery("select new Oferta(o.id, o.dataInicio, o.dataFim) from Oferta o where o.dataFim > current_date");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Oferta> recuperaOfertasEmAndamento(Integer idUsuario){
		//Query query = getSession().createQuery("select o from Oferta o join fetch o.produto p where o.status = 2");
		
		Query query = getSession().createQuery("select new Oferta(o.id, p.id, p.nome, o.dataInicio, o.dataFim, o.quantidade, o.descricao, " +
				"	(select min(l.valor) from Lance l where l.oferta.id=o.id) as menorLance, " +
				"	(select min(l2.valor) from Lance l2 where l2.oferta.id=o.id and l2.usuario.id=:id) as menorLanceUsuario, o.status) " +
				"from " +
				"	Oferta o " +
				"join o.produto p " +
				"where " +
				"	o.status=2");
		query.setInteger("id", idUsuario);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Oferta> recuperaOfertasNaoFinalizadasByStatus(){
		Query query = getSession().createQuery("select o from Oferta o where o.status not in (1, 3) ");
		return query.list();
	}
}
