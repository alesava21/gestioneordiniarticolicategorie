package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria>{
	
	public Categoria findByIdFetchingArticoli(Long id);
	
	public void deleteAppFromJoinTable(Long idCategoria) throws Exception;
	
	public void deleteAllSFromJoinTable() throws Exception;



}
