package be.dno.hfrobjectifs.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Evenement implements Serializable{

	private static final long serialVersionUID = -390561232329034347L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.SEQUENCE)
	private Long id;
	
	@Persistent
	private String name;
	
	@Persistent
	private String pays;
	
	@Persistent
	private String ville;
	
	@Persistent
	private float distance;
	
	@Persistent
	private String urlEvenement;
	
	@Persistent
	private String urlResultats;
	
	@Persistent
	private Date dateEvenement;
	
	@Persistent
	private String type;
	
	@Persistent
	private boolean usedBySomeoneElse;
	
	

	public boolean isUsedBySomeoneElse() {
		return usedBySomeoneElse;
	}

	public void setUsedBySomeoneElse(boolean usedBySomeoneElse) {
		this.usedBySomeoneElse = usedBySomeoneElse;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public String getUrlEvenement() {
		return urlEvenement;
	}

	public void setUrlEvenement(String urlEvenement) {
		this.urlEvenement = urlEvenement;
	}

	public String getUrlResultats() {
		return urlResultats;
	}

	public void setUrlResultats(String urlResultats) {
		this.urlResultats = urlResultats;
	}

	public Date getDateEvenement() {
		return dateEvenement;
	}

	public void setDateEvenement(Date dateEvenement) {
		this.dateEvenement = dateEvenement;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDateEvenementStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.dateEvenement);
	}
}
