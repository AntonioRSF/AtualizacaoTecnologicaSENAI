package br.com.senai.controller;

import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import br.com.senai.dao.LanceHibernateDAO;
import br.com.senai.dao.OfertaHibernateDAO;
import br.com.senai.dao.UsuarioHibernateDAO;
import br.com.senai.dao.util.DAOFactory;
import br.com.senai.entity.Lance;
import br.com.senai.entity.Oferta;
import br.com.senai.entity.StatusOferta;
import br.com.senai.entity.Usuario;

@RequestScoped
@ManagedBean(name="leilaoController")
public class LeilaoController {
	
	private Oferta oferta;
	private Lance lance;
	private UsuarioHibernateDAO usuarioHibernateDAO;
	private OfertaHibernateDAO ofertaHibernateDAO;
	private List<Lance> listaLancesPorOferta;
	private LanceHibernateDAO lanceHibernateDAO;
	private List<Oferta> ofertasAndamento;
	private Usuario usuarioLogado;
	private Lance menorLance;
	private Lance menorLanceUsuario ;
	
	public LeilaoController(){
		lance = new Lance();
		oferta = new Oferta();
		this.ofertaHibernateDAO = DAOFactory.criarOfertaDao();
		this.usuarioHibernateDAO = DAOFactory.criarUsuarioDao();	
		this.lanceHibernateDAO = DAOFactory.criarLanceDao();	
		this.usuarioLogado = this.usuarioHibernateDAO.recuperaUsuarioLogado();
		ofertasAndamento = this.ofertaHibernateDAO.recuperaOfertasEmAndamento(usuarioLogado.getId());
	}
	
	public void salvar(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		Oferta ofertaAndamento = ofertaHibernateDAO.carregar(this.oferta.getId());
		
		this.menorLance = this.lanceHibernateDAO.recuperaMenorLanceOferta(this.oferta.getId());
		
		if(ofertaAndamento.getStatus().equals(StatusOferta.FINALIZADA)){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Você chegou tarde !!! Esta oferta já foi finalizada."));
		}
		if(this.lanceHibernateDAO.valorLanceExiste(this.lance.getValor(), this.oferta.getId())){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este valor de lance já foi ofertado !!! Tente outro valor !!!"));
		} 
		if(this.lance.getValor() < 0.01){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Informe um valor IGUAL ou MAIOR que 0,01(UM CENTAVO) para o lance !!!"));
		}
		if((this.menorLance != null) && (this.lance.getValor() >= this.menorLance.getValor())){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Existe Lance com o valor menor !!! Informe um valor abaixo de R$ " + this.menorLance.getValor()));
		}
		if(context.getMessageList().isEmpty()){
			this.lance.setFlVencedor(false);
			this.lance.setOferta(ofertaHibernateDAO.carregar(this.oferta.getId()));
			this.lance.setUsuario(usuarioLogado);
			this.lance.setData(new Date());
			
			this.lanceHibernateDAO.salvar(lance);
			
			this.menorLanceUsuario = this.lanceHibernateDAO.recuperaMenorLanceUsuarioNaOferta(this.oferta.getId(), this.usuarioLogado.getId());
			this.menorLance = this.lanceHibernateDAO.recuperaMenorLanceOferta(this.oferta.getId());
		}
		
		recuperaLancesPorOferta();
	}
	
	public String acompanharLeilao(){
		listaLancesPorOferta = this.lanceHibernateDAO.recuperaLancesPorOferta(this.oferta.getId());
		return "lances";
	}
	
	public void recuperaLancesPorOferta(){
		listaLancesPorOferta = this.lanceHibernateDAO.recuperaLancesPorOferta(this.oferta.getId());
		
		this.menorLanceUsuario = this.lanceHibernateDAO.recuperaMenorLanceUsuarioNaOferta(this.oferta.getId(), this.usuarioLogado.getId());
		this.menorLance = this.lanceHibernateDAO.recuperaMenorLanceOferta(this.oferta.getId());
	}

	public Oferta getOferta() {
		return oferta;
	}

	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}

	public Lance getLance() {
		return lance;
	}

	public void setLance(Lance lance) {
		this.lance = lance;
	}

	public List<Lance> getListaLancesPorOferta() {
		return listaLancesPorOferta;
	}

	public void setListaLancesPorOferta(List<Lance> listaLancesPorOferta) {
		this.listaLancesPorOferta = listaLancesPorOferta;
	}

	public List<Oferta> getOfertasAndamento() {
		return ofertasAndamento;
	}

	public void setOfertasAndamento(List<Oferta> ofertasAndamento) {
		this.ofertasAndamento = ofertasAndamento;
	}

	public Lance getMenorLance() {
		return menorLance;
	}

	public void setMenorLance(Lance menorLance) {
		this.menorLance = menorLance;
	}

	public Lance getMenorLanceUsuario() {
		return menorLanceUsuario;
	}

	public void setMenorLanceUsuario(Lance menorLanceUsuario) {
		this.menorLanceUsuario = menorLanceUsuario;
	}
}
