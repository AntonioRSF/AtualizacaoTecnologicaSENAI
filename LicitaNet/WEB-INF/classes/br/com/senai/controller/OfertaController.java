package br.com.senai.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import br.com.senai.dao.GenericDAO;
import br.com.senai.dao.ProdutoHibernateDAO;
import br.com.senai.dao.util.DAOFactory;
import br.com.senai.entity.Oferta;
import br.com.senai.entity.Produto;
import br.com.senai.entity.StatusOferta;
import br.com.senai.service.OfertaService;

@ManagedBean
public class OfertaController {

	private GenericDAO<Oferta> ofertaDAO;
	private ProdutoHibernateDAO produtoDAO;
	private Oferta oferta;
	private List<SelectItem> produtosItens;
	private List<Oferta> ofertas;

	public OfertaController() {
		this.ofertaDAO = DAOFactory.criarOfertaDao();
		this.produtoDAO = DAOFactory.criarProdutoDao();	
		oferta = new Oferta();
		oferta.setProduto(new Produto());
		listar();	
	}
	
	public String gravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(new Date().compareTo(oferta.getDataFim()) > 0){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Data final da oferta deve ser maior que a data atual."));
		}
		if(oferta.getDataInicio().compareTo(oferta.getDataFim()) > 0){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Data final da oferta deve ser maior que a data inicial da oferta."));
		}
		
		if(!context.getMessageList().isEmpty()){
			return null;
		}
		new OfertaService().grava(oferta, true);
		listar();
		
		return "listarOfertas";
	}

	public void listar() {
		ofertas = ofertaDAO.listar();
	}
	
	public String editar(){
		if(isOfertaNaoSelecionada()){
			return null;
		}
		if(StatusOferta.FINALIZADA.equals(oferta.getStatus())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Não é permitido editar uma oferta finalizada."));
			return null;
		}
		if(StatusOferta.ANDAMENTO.equals(oferta.getStatus())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Não é permitido editar uma oferta em andamento."));
			return null;
		}
		return "cadOferta";
	}

	private boolean isOfertaNaoSelecionada() {
		if(oferta == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Selecione uma oferta."));
			return true;
		}
		return false;
	}
	
	public String remover(){
		if(isOfertaNaoSelecionada()){
			return null;
		}
		if(StatusOferta.FINALIZADA.equals(oferta.getStatus())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Não é permitido excluir uma oferta finalizada."));
			return null;
		}
		if(StatusOferta.ANDAMENTO.equals(oferta.getStatus())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Não é permitido excluir uma oferta em andamento."));
			return null;
		}
		ofertaDAO.excluir(oferta);
		listar();
		return "listarOfertas";
	}


	public List<SelectItem> getProdutosItens() {
		List<Produto> produtos = new ArrayList<Produto>();
		
		if(oferta.getId() == null){
			produtos = produtoDAO.recuperaProdutosDisponiveis();	
		}else{
			produtos = produtoDAO.recuperaProdutosDisponiveisAlteracao(oferta.getProduto().getId());
		}
		
		produtosItens = new ArrayList<SelectItem>();
		for (Produto p : produtos) {
			produtosItens.add(new SelectItem(p.getId(), p.getNome()));
		}
		return produtosItens;
	}

	public void setProdutosItens(List<SelectItem> produtosItens) {
		this.produtosItens = produtosItens;
	}

	public List<Oferta> getOfertas() {
		return ofertas;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public SelectItem[] getStatusFilter(){
		return createFilterOptions(StatusOferta.values());
	}

	private SelectItem[] createFilterOptions(StatusOferta[] data)  {  
        SelectItem[] options = new SelectItem[data.length + 1];  
  
        options[0] = new SelectItem("", "-- Select --");  
        for(int i = 0; i < data.length; i++) {  
            options[i + 1] = new SelectItem(data[i].toString(), data[i].toString());  
        }  
  
        return options;  
    }


}
