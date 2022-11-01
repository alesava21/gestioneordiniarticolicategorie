package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo>{
	
	public Articolo findByIdFetchingCategorie(Long id) throws Exception;
	
	public void deleteAllFromJoinTable() throws Exception;
	
	public void deleteAllArticoliWithOrder(Long idOrdine) throws Exception;
	
	public int sumOfPricesOfCategoriasArticoli(Categoria categoriaInstance) throws Exception;

	public int sumOfPricesForTheArticoliAddressed(String destinatario) throws Exception;
	
	public List<Articolo> findAllArticoliOfOrderWithErrors() throws Exception;





}
