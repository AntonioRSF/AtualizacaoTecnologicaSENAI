package br.com.senai.controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import br.com.senai.dao.GenericDAO;
import br.com.senai.dao.util.DAOFactory;
import br.com.senai.entity.Produto;

@ManagedBean
@RequestScoped
public class ProdutoController {

	private GenericDAO<Produto> produtoDAO;
	private Produto produto;
	private List<Produto> produtos;
	
	public ProdutoController(){
		this.produtoDAO = DAOFactory.criarProdutoDao();
		produto = new Produto();
		listar();
	}

	public String salvar(){
		Integer id = produto.getId();
		if(id == null || id == 0){
			this.produtoDAO.salvar(produto);
			listar();
		} else{
			this.produtoDAO.atualizar(produto);
			listar();
		}
		return "listarProdutos";
	}
	
	public void excluir(){
		this.produtoDAO.excluir(produto);
		listar();
	}
	
	public List<Produto> listar(){
		produtos = this.produtoDAO.listar();
		return produtos;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
}
