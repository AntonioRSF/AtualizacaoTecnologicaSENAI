package br.com.senai.dao;

import java.util.List;

import org.hibernate.Query;

import br.com.senai.entity.Produto;

@SuppressWarnings("rawtypes")
public class ProdutoHibernateDAO extends GenericDAOHibernate<Produto> {

	@SuppressWarnings("unchecked")
	public ProdutoHibernateDAO(Class classe) {
		super(classe);
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> recuperaProdutosDisponiveis(){
		Query query = getSession().createQuery("select p from Produto p where p.id not in(select p2.id from Produto p2 join p2.ofertas o where o.status in (0, 2)) ");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> recuperaProdutosDisponiveisAlteracao(Integer idProduto){
		Query query = getSession().createQuery("select p from Produto p where p.id not in(select p2.id from Produto p2 join p2.ofertas o where o.status in (0, 2)) or p.id = :idProduto ");
		query.setInteger("idProduto", idProduto);
		return query.list();
	}

}
