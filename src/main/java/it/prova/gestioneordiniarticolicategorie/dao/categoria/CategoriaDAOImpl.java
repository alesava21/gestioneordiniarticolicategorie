package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}

		categoriaInstance = entityManager.merge(categoriaInstance);

	}

	@Override
	public void insert(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(categoriaInstance);
	}

	@Override
	public void delete(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(categoriaInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public Categoria findByIdFetchingArticoli(Long id) {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteAppFromJoinTable(Long idCategoria) throws Exception {

	}

	@Override
	public void deleteAllSFromJoinTable() throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria").executeUpdate();
		
		
	}

}
