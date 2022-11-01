package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return (List<Ordine>) entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		ordineInstance = entityManager.merge(ordineInstance);

	}

	@Override
	public void insert(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(ordineInstance);

	}

	@Override
	public void delete(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(ordineInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public Ordine findByIdFetchingArticoli(Long id) {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o FROM Ordine o left join fetch o.articoli a where o.id = :idOrdine", Ordine.class);
		query.setParameter("idOrdine", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public Ordine getEager(Long id) {
		return entityManager.createQuery("from Ordine o left join fetch o.articoli where o.id = ?1", Ordine.class)
				.setParameter(1, id).getResultStream().findFirst().orElse(null);
	}

	@Override
	public List<Ordine> findOrdiniByCategoria(Categoria categoriaInput) {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o inner join fetch o.articoli a inner join fetch a.categorie c where c.id = ?1",
				Ordine.class);
		query.setParameter(1, categoriaInput.getId());
		return query.getResultList();
	}

	@Override
	public Ordine findOrdinePiuRecente(Categoria categoriaInput) {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o join o.articoli a join a.categorie c where c.id = ?1 order by o.dataSpedizone desc",
				Ordine.class);
		return query.setParameter(1, categoriaInput.getId()).getResultList().get(0);
	}

	@Override
	public List<String> findAllIndirizziDiOrdiniConCheckNumeroSeriale(String numeroSerialeInput) {
		TypedQuery<String> query = entityManager.createQuery(
				"select o.indirizzoSpedizone FROM Ordine o join o.articoli a where a.numeroSeriale like ?1",
				String.class);
		query.setParameter(1, "%" + numeroSerialeInput + "%");
		return query.getResultList();
	}

}
