package be.dno.hfrobjectifs.entities;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Objectif implements Serializable{
	
	private static final long serialVersionUID = -3250172380979288157L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private Long evenementId;
	
	@Persistent
	private int annee;
	
	@Persistent
	private int tempsPrevu;
	
	@Persistent
	private int tempsRealise;
	
	@Persistent
	private boolean result;
	
	@Persistent
	private String lienActivite;
	
	@Persistent
	private String lienHFR;
	
	@Persistent
	private boolean objectifPrive;
	
	@Persistent
	private float poids;

	public Long getId() {
		return id;
	}

	public Long getEvenementId() {
		return evenementId;
	}

	public void setEvenementId(Long evenementId) {
		this.evenementId = evenementId;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public int getTempsPrevu() {
		return tempsPrevu;
	}

	public void setTempsPrevu(int tempsPrevu) {
		this.tempsPrevu = tempsPrevu;
	}

	public int getTempsRealise() {
		return tempsRealise;
	}

	public void setTempsRealise(int tempsRealise) {
		this.tempsRealise = tempsRealise;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getLienActivite() {
		return lienActivite;
	}

	public void setLienActivite(String lienActivite) {
		this.lienActivite = lienActivite;
	}

	public String getLienHFR() {
		return lienHFR;
	}

	public void setLienHFR(String lienHFR) {
		this.lienHFR = lienHFR;
	}

	public boolean isObjectifPrive() {
		return objectifPrive;
	}

	public void setObjectifPrive(boolean objectifPrive) {
		this.objectifPrive = objectifPrive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	
	
	
}
