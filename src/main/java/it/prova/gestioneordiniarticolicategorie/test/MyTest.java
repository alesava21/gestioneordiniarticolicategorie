package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.OrdineService;

public class MyTest {

	public static void main(String[] args) {

		OrdineService ordineService = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloService = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaService = MyServiceFactory.getCategoriaServiceInstance();

		try {

			System.out.println(
					"****************************** INIZIO batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Ordini ci sono " + ordineService.listAll().size() + " elementi.");
			System.out.println("In tabella Articoli ci sono " + articoloService.listAll().size() + " elementi.");
			System.out.println("In tabella Categorie ci sono " + categoriaService.listAll().size() + " elementi.");

			testInserimentoNuovoOrdine(ordineService);

			testAggiornaOrdine(ordineService);

			testInserisciArticolo(articoloService);

			testAggiornaArticolo(articoloService);

			testInserisciNuovaCategoria(categoriaService);

			testAggiornaCategoria(categoriaService);

			testRimozioneOrdine(ordineService);

			testRimozioneArticolo(articoloService);
			
			testRimozioneCategoria(categoriaService);

			testCollegaArticoloAdCategoria(articoloService, categoriaService);

			testCollegaCategoriaAdArticolo(articoloService, categoriaService);

			System.out.println(
					"****************************** FINE batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Ordini ci sono " + ordineService.listAll().size() + " elementi.");
			System.out.println("In tabella Articoli ci sono " + articoloService.listAll().size() + " elementi.");
			System.out.println("In tabella Categorie ci sono " + categoriaService.listAll().size() + " elementi.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserimentoNuovoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserimentoNuovoOrdine inizio.............");
		Ordine nuovoOrdine = new Ordine("Alessandro sava", "Roma - Via Mosca 52",
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"));
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null) {
			throw new RuntimeException("testInserimentoNuovoCd fallito ");
		}

		ordineServiceInstance.rimuovi(nuovoOrdine.getId());
		System.out.println(".......testInserimentoNuovoOrdine fine: PASSED.............");

	}

	private static void testInserisciArticolo(ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testInserisciArticolo inizio.............");
		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40,
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testInserisciArticolo fallito ");
		}
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		System.out.println(".......testInserisciArticolo fine: PASSED.............");

	}

	private static void testInserisciNuovaCategoria(CategoriaService categoriaService) throws Exception {
		System.out.println(".......testInserisciNuovaCategoria inizio.............");

		Categoria nuovaCategoria = new Categoria("Videogiochi", "AG6GS4");
		categoriaService.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testInserisciNuovaCategoria fallito ");
		}
		categoriaService.rimuovi(nuovaCategoria.getId());
		System.out.println(".......testInserisciNuovaCategoria fine: PASSED.............");
	}

	private static void testAggiornaOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiornaOrdine inizio.............");

		Date nuovaData = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021");
		Ordine nuovoOrdine = new Ordine("Alessandro sava", "Roma - Via Mosca 52", nuovaData);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null) {
			throw new RuntimeException("testInserimentoNuovoCd fallito ");
		}

		nuovoOrdine.setNomeDestinatario("Diego Mezzo");
		ordineServiceInstance.aggiorna(nuovoOrdine);

		if (!ordineServiceInstance.caricaSingoloElemento(nuovoOrdine.getId()).getNomeDestinatario()
				.equals("Diego Mezzo")
				|| !(ordineServiceInstance.caricaSingoloElemento(nuovoOrdine.getId()).getDataSpedizone()
						.compareTo(nuovaData) == 0)) {
			throw new RuntimeException("testAggiornamentoApp failed.");
		}
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());
		System.out.println(".......testAggiornaOrdine fine: PASSED.............");
	}

	private static void testAggiornaArticolo(ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testAggiornaArticolo inizio.............");

		Date dataInput = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021");

		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40, dataInput);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testAggiornaArticolo fallito ");
		}

		nuovoArticolo.setDescrizione("test");
		articoloServiceInstance.aggiorna(nuovoArticolo);

		if (!articoloServiceInstance.caricaSingoloElemento(nuovoArticolo.getId()).getDescrizione().equals("test")
				|| !(articoloServiceInstance.caricaSingoloElemento(nuovoArticolo.getId()).getDataInserimento()
						.compareTo(dataInput) == 0)) {
			throw new RuntimeException("testAggiornaArticolo failed.");
		}
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		System.out.println(".......testAggiornaArticolo fine: PASSED.............");
	}

	private static void testAggiornaCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testAggiornaArticolo inizio.............");

		Categoria nuovaCategoria = new Categoria("Videogiochi", "AG6GS4");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testInserisciNuovaCategoria fallito ");
		}

		nuovaCategoria.setDescrizione("Video");
		categoriaServiceInstance.aggiorna(nuovaCategoria);

		if (!categoriaServiceInstance.caricaSingoloElemento(nuovaCategoria.getId()).getDescrizione().equals("Video")) {
			throw new RuntimeException("testAggiornaCategoria failed.");
		}

		categoriaServiceInstance.rimuovi(nuovaCategoria.getId());
		System.out.println(".......testAggiornaCategoria fine: PASSED.............");

	}

	private static void testRimozioneOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimozioneOrdine inizio.............");
		Ordine nuovoOrdine = new Ordine("Alessandro sava", "Roma - Via Mosca 52",
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"));
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null) {
			throw new RuntimeException("testRimozioneOrdine fallito ");
		}

		ordineServiceInstance.rimuovi(nuovoOrdine.getId());
		System.out.println(".......testRimozioneOrdine fine: PASSED.............");
	}

	private static void testRimozioneArticolo(ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneArticolo inizio.............");

		Date dataInput = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021");

		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40, dataInput);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testRimozioneArticolo fallito ");
		}

		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		System.out.println(".......testRimozioneArticolo fine: PASSED.............");
	}
	
	private static void testRimozioneCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testRimozioneCategoria inizio.............");
		

		Categoria nuovaCategoria = new Categoria("Videogiochi", "AG6GS4");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testInserisciNuovaCategoria fallito ");
		}
		
		categoriaServiceInstance.rimuovi(nuovaCategoria.getId());
		System.out.println(".......testRimozioneCategoria fine: PASSED.............");

	}

	private static void testCollegaArticoloAdCategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testCollegaArticoloAdCategoria inizio.............");

		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40,
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testCollegaArticoloAdCategoria fallito ");
		}

		Categoria nuovaCategoria = new Categoria("Fantasy", "HDGU372GDU");

		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testCollegaArticoloAdCategoria fallito ");
		}

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);

		Articolo articoloReload = articoloServiceInstance.caricaSingoloElementoEagerArticolo(nuovoArticolo.getId());

		if (articoloReload.getCategorie().isEmpty()) {
			throw new RuntimeException(
					"testCollegaArticoloAdCategoria FAILED: collegamento non avvenuto con successo.");
		}

		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		categoriaServiceInstance.rimuovi(nuovaCategoria.getId());

		System.out.println(".......testCollegaArticoloAdCategoria fine: PASSED.............");
	}

	private static void testCollegaCategoriaAdArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testCollegaCategoriaAdArticolo inizio.............");

		Categoria nuovaCategoria = new Categoria("Fantasy", "HDGU372GDU");

		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testCollegaArticoloAdCategoria fallito ");
		}

		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40,
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"));
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testCollegaArticoloAdCategoria fallito ");
		}

		categoriaServiceInstance.aggiungiArticolo(nuovaCategoria, nuovoArticolo);

		Categoria categoriaReload = categoriaServiceInstance
				.caricaSingoloElementoEagerCategoria(nuovaCategoria.getId());

		if (categoriaReload.getArticoli().isEmpty()) {
			throw new RuntimeException(
					"testCollegaArticoloAdCategoria FAILED: collegamento non avvenuto con successo.");
		}

		categoriaServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(nuovaCategoria.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
	}

}
