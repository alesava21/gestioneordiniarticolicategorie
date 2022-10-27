package it.prova.gestioneordiniarticolicategorie.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "ordine")
public class Ordine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "nomeDestinatario")
	private String nomeDestinatario;
	@Column(name = "indirizzoSpedizone")
	private String indirizzoSpedizone;
	@Column(name = "dataSpedizone")
	private Date dataSpedizone;
	@Column(name = "dataScadenza")
	private Date dataScadenza;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ordine")
	private Set<Articolo> articoli = new HashSet<Articolo>();

	@CreationTimestamp
	private LocalDateTime createDateTime;
	@UpdateTimestamp
	private LocalDateTime updateDateTime;

	public Ordine() {
		super();
	}

	public Ordine(Long id, String nomeDestinatario, String indirizzoSpedizone, Date dataSpedizone, Date dataScadenza,
			Set<Articolo> articoli, LocalDateTime createDateTime) {
		super();
		this.id = id;
		this.nomeDestinatario = nomeDestinatario;
		this.indirizzoSpedizone = indirizzoSpedizone;
		this.dataSpedizone = dataSpedizone;
		this.dataScadenza = dataScadenza;
		this.articoli = articoli;
		this.createDateTime = createDateTime;
	}

	public Ordine(String nomeDestinatario, String indirizzoSpedizone, Date dataSpedizone, Date dataScadenza) {
		super();
		this.nomeDestinatario = nomeDestinatario;
		this.indirizzoSpedizone = indirizzoSpedizone;
		this.dataSpedizone = dataSpedizone;
		this.dataScadenza = dataScadenza;
	}

	public Ordine(String nomeDestinatario, String indirizzoSpedizone, Date dataSpedizone) {
		super();
		this.nomeDestinatario = nomeDestinatario;
		this.indirizzoSpedizone = indirizzoSpedizone;
		this.dataSpedizone = dataSpedizone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}

	public String getIndirizzoSpedizone() {
		return indirizzoSpedizone;
	}

	public void setIndirizzoSpedizone(String indirizzoSpedizone) {
		this.indirizzoSpedizone = indirizzoSpedizone;
	}

	public Date getDataSpedizone() {
		return dataSpedizone;
	}

	public void setDataSpedizone(Date dataSpedizone) {
		this.dataSpedizone = dataSpedizone;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Set<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(Set<Articolo> articoli) {
		this.articoli = articoli;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

}
