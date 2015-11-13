package br.com.senai.service;

import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.SessionFactory;

import br.com.senai.dao.util.HibernateUtil;
import br.com.senai.entity.Oferta;

public class ThreadOferta {
	private Timer timer;

	public void agendaInicioOferta(Oferta oferta) {
		timer = new Timer();
		timer.schedule(new AgendaInicioOferta(oferta.getId()), oferta.getDataInicio());
	}

	public void agendaFimOferta(Oferta oferta) {
		timer = new Timer();
		timer.schedule(new AgendaFimOferta(oferta.getId()), oferta.getDataFim());
	}

	class AgendaInicioOferta extends TimerTask {
		private int idOferta;
		private SessionFactory	sf;

		public AgendaInicioOferta(int idOferta) {
			this.idOferta = idOferta;
			sf = HibernateUtil.getSessionFactory();			
		}

		public void run() {
			sf.getCurrentSession().beginTransaction();
			OfertaService service = new OfertaService();
			service.iniciaOferta(this.idOferta);
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			timer.cancel();
		}
	}

	class AgendaFimOferta extends TimerTask {
		private int idOferta;
		private SessionFactory	sf;

		public AgendaFimOferta(int idOferta) {
			this.idOferta = idOferta;
			sf = HibernateUtil.getSessionFactory();
		}

		public void run() {
			sf.getCurrentSession().beginTransaction();
			OfertaService service = new OfertaService();
			service.finalizaOferta(idOferta);
			sf.getCurrentSession().getTransaction().commit();
			sf.getCurrentSession().close();
			timer.cancel();
		}
	}

}