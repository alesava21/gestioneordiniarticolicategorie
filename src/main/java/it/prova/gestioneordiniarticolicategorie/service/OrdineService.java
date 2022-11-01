package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {

	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void rimuovi(Long ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public Ordine caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public void rimuoviArticoli(Long id) throws Exception;

	public List<Ordine> cercaOrdiniTramiteCategoria(Categoria categoriaInput) throws Exception;

	public Ordine cercaOrdinePiuRecente(Categoria categoriaInput) throws Exception;

	public List<String> cercaTuttiIndirizziDiOrdiniConCheckNumeroSeriale(String numeroSerialeInput) throws Exception;

	public void setOrdineDAO(OrdineDAO ordineDAO);

}
