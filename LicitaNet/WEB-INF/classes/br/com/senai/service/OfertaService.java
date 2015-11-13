package br.com.senai.service;

import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import br.com.senai.dao.LanceHibernateDAO;
import br.com.senai.dao.OfertaHibernateDAO;
import br.com.senai.dao.util.DAOFactory;
import br.com.senai.dao.util.HibernateUtil;
import br.com.senai.entity.Lance;
import br.com.senai.entity.Oferta;
import br.com.senai.entity.StatusOferta;

public class OfertaService {

	private OfertaHibernateDAO ofertaDAO;
	private LanceHibernateDAO lanceDAO;
	
	public OfertaService() {
		this.ofertaDAO = DAOFactory.criarOfertaDao();
		this.lanceDAO = DAOFactory.criarLanceDao();
	}

	public void grava(Oferta oferta, boolean isAgendaOferta) {
		
		if (oferta.getId() == null) {
			if (isOfertaAIniciar(oferta)) {
				oferta.setStatus(StatusOferta.PROGRAMADA);
			} else {
				oferta.setStatus(StatusOferta.ANDAMENTO);
			}
			ofertaDAO.salvar(oferta);
		} else {
			ofertaDAO.atualizar(oferta);
		}

		if (isAgendaOferta) {
			agendaOferta(oferta);
		}
	}

	public void grava(Oferta oferta) {
		grava(oferta, false);
	}

	public void agendaOferta(Oferta oferta) {
		agendaInicioOferta(oferta);
		agendaFimOferta(oferta);
	}

	public void agendaInicioOferta(Oferta oferta) {
		if (isOfertaAIniciar(oferta)) {
			new ThreadOferta().agendaInicioOferta(oferta);
		}
	}

	public void agendaFimOferta(Oferta oferta) {
		if (oferta.getDataFim() != null
				&& (oferta.getDataFim().compareTo(new Date()) >= 0)) {
			new ThreadOferta().agendaFimOferta(oferta);
		}
	}

	public void iniciaOferta(int idOferta) {
		Oferta oferta = recuperaOferta(idOferta);
		oferta.setStatus(StatusOferta.ANDAMENTO);
		grava(oferta);
	}

	public void finalizaOferta(int idOferta) {
		Oferta oferta = recuperaOferta(idOferta);
		oferta.setStatus(StatusOferta.FINALIZADA);

		Lance lanceVencedor = lanceDAO.recuperaMenorLanceOferta(oferta.getId());
		if(lanceVencedor != null){
			oferta.setUsuarioVencedor(lanceVencedor.getUsuario());
			lanceVencedor.setFlVencedor(true);
			lanceDAO.atualizar(lanceVencedor);
		}	
		grava(oferta);
	}

	public void agendaOfertasStartSistema() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		sf.getCurrentSession().beginTransaction();
		ofertaDAO.setSession(sf.getCurrentSession());
		List<Oferta> ofertas = ofertaDAO.recuperaOfertasNaoFinalizadasByStatus();
		
		for (Oferta o : ofertas) {
			if((o.getDataFim().compareTo(new Date()) < 0)){
				finalizaOferta(o.getId());				
			}else{
				if(o.getDataInicio().compareTo(new Date()) < 0){
					iniciaOferta(o.getId());
					agendaFimOferta(o);
				}else{
					agendaFimOferta(o);	
				}				
			}
		}
		sf.getCurrentSession().getTransaction().commit();
		sf.getCurrentSession().close();
	}

	public Oferta recuperaOferta(int idOferta) {
		return ofertaDAO.carregar(idOferta);
	}

	private boolean isOfertaAIniciar(Oferta oferta) {
		return oferta.getDataInicio() != null
				&& (oferta.getDataInicio().compareTo(new Date()) > 0);
	}

}
