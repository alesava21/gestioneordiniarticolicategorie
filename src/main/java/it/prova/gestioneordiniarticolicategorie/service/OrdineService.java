package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {
	
	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void rimuovi(Long ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public Ordine caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public void setOrdineDAO(OrdineDAO ordineDAO);

}
