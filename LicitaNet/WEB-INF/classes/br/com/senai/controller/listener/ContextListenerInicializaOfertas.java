package br.com.senai.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.com.senai.service.OfertaService;

/**
 * Application Lifecycle Listener implementation class ContextListenerInicializaOfertas
 *
 */
@WebListener
public class ContextListenerInicializaOfertas implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListenerInicializaOfertas() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	new OfertaService().agendaOfertasStartSistema();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
