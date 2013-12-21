package be.dno.hfrobjectifs.entities;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import be.dno.hfrobjectifs.tools.CalcHelper;

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
	private double tempsPrevu;
	
	@Persistent
	private double tempsRealise;
	
	@Persistent
	private boolean result;
	
	@Persistent
	private String lienActivite;
	
	@Persistent
	private String lienHFR;
	
	@Persistent
	private boolean objectifPrive;
	
	@Persistent
	private float poidsPrevu;
	
	@Persistent
	private float poidsRealise;
	
	

	public float getPoidsPrevu() {
		return poidsPrevu;
	}

	public void setPoidsPrevu(float poidsPrevu) {
		this.poidsPrevu = poidsPrevu;
	}

	public float getPoidsRealise() {
		return poidsRealise;
	}

	public void setPoidsRealise(float poidsRealise) {
		this.poidsRealise = poidsRealise;
	}

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

	public double getTempsPrevu() {
		return tempsPrevu;
	}

	public void setTempsPrevu(double tempsPrevu) {
		this.tempsPrevu = tempsPrevu;
	}

	public double getTempsRealise() {
		return tempsRealise;
	}

	public void setTempsRealise(double tempsRealise) {
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
	
	public String getTempsPrevuStr(){
		if (tempsPrevu == 0){
			return "";
		}
		return CalcHelper.toTime(tempsPrevu);
	}
	
	public String getTempsRealiseStr(){
		if (tempsRealise == 0){
			return "";
		}
		return CalcHelper.toTime(tempsRealise);
	}
}
