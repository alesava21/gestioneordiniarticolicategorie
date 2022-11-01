package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null)
			throw new Exception("Problema valore in input");

		articoloInstance = entityManager.merge(articoloInstance);
	}

	@Override
	public void insert(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(articoloInstance);
	}

	@Override
	public void delete(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(articoloInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public Articolo findByIdFetchingCategorie(Long id) throws Exception {
		TypedQuery<Articolo> query = entityManager.createQuery(
				"select a FROM Articolo a left join fetch a.categorie c where a.id = :idArticolo", Articolo.class);
		query.setParameter("idArticolo", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteAllFromJoinTable() throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria").executeUpdate();

	}

	@Override
	public void deleteAllArticoliWithOrder(Long idOrdine) throws Exception {
		if (idOrdine < 0) {
			throw new Exception("Problema valore in input");
		}
		entityManager.createNativeQuery("delete from Articolo where ordine_id=?1").setParameter(1, idOrdine)
				.executeUpdate();

	}

	@Override
	public int sumOfPricesOfCategoriasArticoli(Categoria categoriaInstance) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) FROM Articolo a left join a.categorie c where c.id = ?1", Long.class);
		query.setParameter(1, categoriaInstance.getId());
		return query.getSingleResult().intValue();
	}

	@Override
	public int sumOfPricesForTheArticoliAddressed(String destinatario) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) FROM Articolo a inner join a.ordine o where o.nomeDestinatario = ?1",
				Long.class);
		query.setParameter(1, destinatario);
		return query.getSingleResult().intValue();
	}

	@Override
	public List<Articolo> findAllArticoliOfOrderWithErrors() throws Exception {
		return entityManager
				.createQuery("select a from Articolo a inner join a.ordine o where o.dataSpedizone > o.dataScadenza",
						Articolo.class)
				.getResultList();
	}
}
