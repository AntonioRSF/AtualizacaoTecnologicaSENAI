package br.com.senai.dao;

import java.util.List;
import org.hibernate.classic.Session;

public class GenericDAOHibernate<T> implements GenericDAO<T> {
	
	private Session session;
	private Class<T> classe;
	public void setSession(Session session){
		this.session = session;
	}
	
	public GenericDAOHibernate(Class<T> classe){
		this.classe = classe;
	}

	@Override
	public void salvar(T t) {
		this.session.save(t);
	}

	@Override
	public void atualizar(T t) {
//		session.refresh(t);
		session.merge(t);
//		session.refresh(t);
//		this.session.update(t);
	}

	@Override
	public void excluir(T t) {
		this.session.delete(t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T carregar(Integer codigo) {
		return (T) this.session.get(classe, codigo) ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listar() {
		return this.session.createCriteria(classe).list();
	}
	
	protected Session getSession(){
		return session;
	}

}
