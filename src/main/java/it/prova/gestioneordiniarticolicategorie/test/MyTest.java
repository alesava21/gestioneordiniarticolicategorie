package it.prova.gestioneordiniarticolicategorie.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.OrdineConArticoliAssociatiException;
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

			testRimozioneOrdineConExceptionPersonalizzato(ordineService, articoloService);

			testTrovaOrdiniPerCategoriaEffettuati(categoriaService, ordineService, articoloService);

			testCercaOrdiniPiuRecenti(ordineService, categoriaService, articoloService);

			testTrovaIndirizzoContenenteStringNumeroDiSerieArticoli(ordineService, articoloService);

			testTrovaDistinctCategoriaArticoloOrdine(ordineService, articoloService, categoriaService);

			testTrovaCodiciCategoriaDiOrdiniEffettuatiInUnDeterminatoMese(ordineService, articoloService,
					categoriaService);

			testSommaPrezziArticoliDellaCategoria(ordineService, articoloService, categoriaService);

			testSommaPrezziIndirizzatiAdUnaPersona(ordineService, articoloService, categoriaService);
			
			testErrori(ordineService, articoloService, categoriaService);

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

	private static void testRimozioneOrdineConExceptionPersonalizzato(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {

		System.out.println(".......testRimozioneOrdineConExceptionPersonalizzato inizio.............");

		Date nuovaData = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021");
		Ordine nuovoOrdine = new Ordine("Alessandro sava", "Roma - Via Mosca 52", nuovaData);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null) {
			throw new RuntimeException("testRimozioneOrdineConExceptionPersonalizzato fallito, ordine non inserito ");
		}

		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40,
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testRimozioneOrdineConExceptionPersonalizzato fallito, articolo non presente ");
		}

		try {
			if (nuovoArticolo.getOrdine().equals(nuovoOrdine)) {
				throw new OrdineConArticoliAssociatiException(
						"testRimozioneOrdineCustomExceptionSeArticoliPresenti FAILED");
			}
		} catch (OrdineConArticoliAssociatiException e) {
			e.printStackTrace();
			articoloServiceInstance.rimuovi(nuovoArticolo.getId());
			ordineServiceInstance.rimuovi(nuovoOrdine.getId());
		}
		System.out.println(".......testRimozioneOrdineConExceptionPersonalizzato fine: PASSED.............");

	}

	private static void testTrovaOrdiniPerCategoriaEffettuati(CategoriaService categoriaServiceInstance,
			OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneOrdineConExceptionPersonalizzato inizio.............");

		Date nuovaData = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021");
		Ordine nuovoOrdine = new Ordine("Alessandro sava", "Roma - Via Mosca 52", nuovaData);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null) {
			throw new RuntimeException("testTrovaOrdiniPerCategoriaEffettuati fallito, ordine non inserito ");
		}

		Articolo nuovoArticolo = new Articolo("I Fantastici Quattro", "GS3HST26", 40,
				new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2021"), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null) {
			throw new RuntimeException("testTrovaOrdiniPerCategoriaEffettuati fallito, articolo non presente ");
		}

		Categoria nuovaCategoria = new Categoria("VideoGiochi", "GD57SG53");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null) {
			throw new RuntimeException("testTrovaOrdiniPerCategoriaEffettuati fallito, categoria non presente ");
		}

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);

		Articolo articoloReload = articoloServiceInstance.caricaSingoloElementoEagerArticolo(nuovoArticolo.getId());

		if (articoloReload.getCategorie().isEmpty()) {
			throw new RuntimeException("testTrovaOrdiniPerCategoriaEffettuati FAILED: categoria non aggiunta.");

		}

		List<Ordine> ordiniEfettuatiConUnaDeterminataCategoria = ordineServiceInstance
				.cercaOrdiniTramiteCategoria(nuovaCategoria);

		if (ordiniEfettuatiConUnaDeterminataCategoria.size() != 1)
			throw new RuntimeException(
					"testTrovaOrdiniEffettuatiPerCategoria FAILED: non ci sono ordini effettuati per quella determinata categoria.");

		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(nuovaCategoria.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(".......testTrovaOrdiniPerCategoriaEffettuati fine: PASSED.............");

	}

	private static void testCercaOrdiniPiuRecenti(OrdineService ordineServiceInstance,
			CategoriaService categoriaServiceInstance, ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testCercaOrdiniPiuRecenti inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("21-06-2021");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("30-06-2021");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, ordine non inserito ");

		Date dataSpedizione1 = new SimpleDateFormat("dd-MM-yyyy").parse("19-04-2021");
		Date dataScadenza1 = new SimpleDateFormat("dd-MM-yyyy").parse("20-04-2021");
		Ordine nuovoOrdine1 = new Ordine("Marco Sava", "Via Fedro 41", dataSpedizione1, dataScadenza1);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine1);

		if (nuovoOrdine1.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, ordine non inserito ");

		Articolo nuoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuoArticolo);

		if (nuoArticolo.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, articolo non inserito ");

		Articolo nuovoArticolo1 = new Articolo("Ipad Pro", "HAGD5739SAGTD", 1200, new Date(), nuovoOrdine1);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo1);

		if (nuovoArticolo1.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, articolo non inserito ");

		Categoria categoriaInstance = new Categoria("Elettronica", "HSGDT36SJA73");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);

		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, categoria non inserita ");

		articoloServiceInstance.aggiungiCategoria(nuoArticolo, categoriaInstance);

		Articolo articoloReloaded = articoloServiceInstance.caricaSingoloElementoEagerArticolo(nuoArticolo.getId());
		if (articoloReloaded.getCategorie().isEmpty())
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria FAILED: categoria non aggiunta.");

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo1, categoriaInstance);

		Articolo articoloReloaded1 = articoloServiceInstance.caricaSingoloElementoEagerArticolo(nuovoArticolo1.getId());
		if (articoloReloaded1.getCategorie().isEmpty())
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria FAILED: categoria non aggiunta.");

		Ordine ordinePiuRecenteAventeDeterminataCategoria = ordineServiceInstance
				.cercaOrdinePiuRecente(categoriaInstance);

		if (ordinePiuRecenteAventeDeterminataCategoria.equals(null))
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria FAILED: si e'verificato un errore.");

		// reset tabelle
		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(categoriaInstance.getId());
		articoloServiceInstance.rimuovi(nuoArticolo.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo1.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine1.getId());

		System.out.println(".......testTrovaOrdinePiurecenteConCategoria fine: PASSED.............");

	}

	public static void testTrovaIndirizzoContenenteStringNumeroDiSerieArticoli(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testTrovaIndirizzoContenenteStringNumeroDiSerieArticoli inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("21-06-2021");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("30-06-2021");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, ordine non inserito ");

		Articolo nuoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuoArticolo);

		if (nuoArticolo.getId() == null)
			throw new RuntimeException("testTrovaOrdinePiurecenteConCategoria fallito, articolo non inserito ");

		List<String> indirizziOrdiniAventiArticoliConNumeroSerialeContenenteStringa = ordineServiceInstance
				.cercaTuttiIndirizziDiOrdiniConCheckNumeroSeriale("HDGS");

		if (indirizziOrdiniAventiArticoliConNumeroSerialeContenenteStringa.size() != 1)
			throw new RuntimeException(
					"testTrovaIndirizziDiOrdiniContenentiStringaNelNumeroSerialeDegliArticoli FAILED: non e stato possibile processare la tua richiesta.");

		articoloServiceInstance.rimuovi(nuoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(".......testTrovaIndirizzoContenenteStringNumeroDiSerieArticoli fine: PASSED.............");

	}

	private static void testTrovaDistinctCategoriaArticoloOrdine(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(".......testTrovaDistinctCategoriaArticoloOrdine inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("21-06-2021");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("30-06-2021");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testTrovaDistinctCategoriaArticoloOrdine fallito, ordine non inserito ");

		Articolo nuovoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testTrovaDistinctCategoriaArticoloOrdine fallito, articolo non inserito ");

		Categoria categoriaInstance = new Categoria("Elettronica", "HSGDT36SJA73");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);

		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testTrovaDistinctCategoriaArticoloOrdine fallito, categoria non inserita ");

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, categoriaInstance);

		List<Categoria> categorieFacentiParteDellOrdine = categoriaServiceInstance
				.trovaDistinctCategoriaArticoliDiOrdine(nuovoOrdine);

		if (categorieFacentiParteDellOrdine.size() != 1)
			throw new RuntimeException("testTrovaDistinctCategoriaArticoloOrdine FAILED: errore nell'esecuzione ");

		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(categoriaInstance.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(".......testTrovaDistinctCategoriaArticoloOrdine fine: PASSED.............");

	}

	private static void testTrovaCodiciCategoriaDiOrdiniEffettuatiInUnDeterminatoMese(
			OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testTrovaDistinctCategoriaArticoloOrdine inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("29-05-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("30-05-2022");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException(
					"testTrovaCodiciCategoriaDiOrdiniEffettuatiInUnDeterminatoMese fallito, ordine non inserito ");

		Articolo nuovoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null)
			throw new RuntimeException(
					"testTrovaCodiciCategoriaDiOrdiniEffettuatiInUnDeterminatoMese fallito, articolo non inserito ");

		Categoria categoriaInstance = new Categoria("Elettronica", "HSGDT36SJA73");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);

		if (categoriaInstance.getId() == null)
			throw new RuntimeException(
					"testTrovaCodiciCategoriaDiOrdiniEffettuatiInUnDeterminatoMese fallito, categoria non inserita ");

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, categoriaInstance);

		Date dataPerConfronto = new SimpleDateFormat("MM-yyyy").parse("02-2022");
		List<String> descrizioniCodiciCategorieDiOrdiniEffettuatiInMese = categoriaServiceInstance
				.trovaCodiciDiCategorieDiOrdiniEffettuatiInUnDeterminatoMese(dataPerConfronto);

		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(categoriaInstance.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(
				".......testTrovaCodiciCategoriaDiOrdiniEffettuatiInUnDeterminatoMese fine: PASSED.............");

	}

	private static void testSommaPrezziArticoliDellaCategoria(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(".......testSommaPrezziArticoliDellaCategoria inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("29-05-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("30-05-2022");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testSommaPrezziArticoliDellaCategoria fallito, ordine non inserito ");

		Articolo nuovoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testSommaPrezziArticoliDellaCategoria fallito, articolo non inserito ");

		Categoria categoriaInstance = new Categoria("Elettronica", "HSGDT36SJA73");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);

		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziArticoliDellaCategoria fallito, categoria non inserita ");

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, categoriaInstance);

		int sommaPrezziDiArticoliDiUnaDeterminataCategoria = articoloServiceInstance
				.prezziDegliArticoliDellaCategoria(categoriaInstance);

		if (sommaPrezziDiArticoliDiUnaDeterminataCategoria != (nuovoArticolo.getPrezzoSingolo()))
			throw new RuntimeException(
					"testSommaPrezziArticoliDellaCategoria FAILED: si e'verificato un errore durante l'esecuzione della query.");

		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(categoriaInstance.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(".......testSommaPrezziArticoliDellaCategoria fine: PASSED.............");

	}

	private static void testSommaPrezziIndirizzatiAdUnaPersona(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance, CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testSommaPrezziIndirizzatiAdUnaPersona inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("29-05-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("30-05-2022");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testSommaPrezziIndirizzatiAdUnaPersona fallito, ordine non inserito ");

		Articolo nuovoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testSommaPrezziIndirizzatiAdUnaPersona fallito, articolo non inserito ");

		Categoria categoriaInstance = new Categoria("Elettronica", "HSGDT36SJA73");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);

		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testSommaPrezziIndirizzatiAdUnaPersona fallito, categoria non inserita ");

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, categoriaInstance);

		int sommaPrezziArticoliIndirizzatiA = articoloServiceInstance
				.voglioLaSommaDeiPrezziDegliArticoliIndirizzati("Alessandro Sava");

		if (sommaPrezziArticoliIndirizzatiA == 0)
			throw new RuntimeException("testSommaPrezziIndirizzatiAdUnaPersona FAILED: errore durante l'esecuzione.");

		articoloServiceInstance.rimuoviTutteLeCategorieDallaTabellaDiJoin();
		categoriaServiceInstance.rimuovi(categoriaInstance.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(".......testSommaPrezziIndirizzatiAdUnaPersona fine: PASSED.............");
	}

	private static void testErrori(OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(".......testErrori inizio.............");

		Date dataSpedizione = new SimpleDateFormat("dd-MM-yyyy").parse("30-05-2022");
		Date dataScadenza = new SimpleDateFormat("dd-MM-yyyy").parse("1-05-2022");
		Ordine nuovoOrdine = new Ordine("Alessandro Sava", "Via mosca 52", dataSpedizione, dataScadenza);
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testErrori fallito, ordine non inserito ");

		Articolo nuovoArticolo = new Articolo("Iphone 14PRO", "HDGS573HSTDD", 1500, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testErrori fallito, articolo non inserito ");

		Categoria categoriaInstance = new Categoria("Elettronica", "HSGDT36SJA73");
		categoriaServiceInstance.inserisciNuovo(categoriaInstance);

		if (categoriaInstance.getId() == null)
			throw new RuntimeException("testErrori fallito, categoria non inserita ");

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, categoriaInstance);

		List<Articolo> articoliAventiOrdineConErroreNelleDate = articoloServiceInstance.articoliDiOrdineConErrori();

		if (articoliAventiOrdineConErroreNelleDate.size() != 2)
			throw new RuntimeException("testErrori FAILED: ci sono stati dei errori con le date inserite.");

		categoriaServiceInstance.rimuovi(categoriaInstance.getId());
		articoloServiceInstance.rimuovi(nuovoArticolo.getId());
		ordineServiceInstance.rimuovi(nuovoOrdine.getId());

		System.out.println(".......testErrori fine: PASSED.............");

	}

}
