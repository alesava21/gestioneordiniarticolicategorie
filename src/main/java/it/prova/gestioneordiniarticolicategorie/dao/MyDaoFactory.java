package it.prova.gestioneordiniarticolicategorie.dao;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAOImpl;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAOImpl;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAOImpl;

public class MyDaoFactory {
	
	private static OrdineDAO ordineDAOInstance = null;
	private static ArticoloDAO articoloDAOinstance = null;
	private static CategoriaDAO categoriaDAOInstance = null;
	
	public static OrdineDAO getOrdineDAOInstance() {
		if (ordineDAOInstance == null) 
			ordineDAOInstance = new OrdineDAOImpl();
			return ordineDAOInstance;	
	}
	
	public static ArticoloDAO getArticoloDAOInstance() {
		if (articoloDAOinstance == null) 
			articoloDAOinstance = new ArticoloDAOImpl();
			return articoloDAOinstance;	
	}
	
	public static CategoriaDAO getCategoriaDAOInstance() {
		if (categoriaDAOInstance == null) 
			categoriaDAOInstance = new CategoriaDAOImpl();
			return categoriaDAOInstance;	
	}



}