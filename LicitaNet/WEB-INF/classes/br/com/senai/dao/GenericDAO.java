package br.com.senai.dao;

import java.util.List;

public interface GenericDAO<T> {
	public void salvar(T t);
	public void atualizar(T t);
	public void excluir(T t);
	public T carregar(Integer codigo);
	public List<T> listar();
}
