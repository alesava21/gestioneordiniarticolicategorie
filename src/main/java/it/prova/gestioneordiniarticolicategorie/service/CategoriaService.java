package it.prova.gestioneordiniarticolicategorie.service;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface CategoriaService {
	
	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Long categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public Categoria caricaSingoloElementoEagerCategoria(Long id) throws Exception;
	
	public void aggiungiArticolo(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;
	
	public void rimuoviTutteLeCategorieDallaTabellaDiJoin() throws Exception;
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
	
	public List<Categoria> trovaDistinctCategoriaArticoliDiOrdine(Ordine ordineInstance) throws Exception;
	
	public List<String> trovaCodiciDiCategorieDiOrdiniEffettuatiInUnDeterminatoMese(Date input) throws Exception;


}
