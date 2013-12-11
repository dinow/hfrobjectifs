package be.dno.hfrobjectifs.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class User implements Serializable{
	
	private static final long serialVersionUID = -1025824059457534369L;

	@PrimaryKey
	@Persistent
	private String userID;
	
	@Persistent
	private String hfrUserName;
	
	@Persistent
	private int taille;
	
	@Persistent
	private char sexe;
	
	@Persistent
	private Date dateNaissance;
	
	@Persistent
	private List<Long> objectifsIds;
	
	@Persistent
	private List<Long> eventsIds;

	
	
	public List<Long> getEventsIds() {
		if (eventsIds == null){
			eventsIds = new ArrayList<Long>();
		}
		return eventsIds;
	}

	public void setEventsIds(List<Long> eventsIds) {
		this.eventsIds = eventsIds;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getHfrUserName() {
		return hfrUserName;
	}

	public void setHfrUserName(String hfrUserName) {
		this.hfrUserName = hfrUserName;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public char getSexe() {
		return sexe;
	}

	public void setSexe(char sexe) {
		this.sexe = sexe;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public List<Long> getObjectifsIds() {
		if (objectifsIds == null){
			objectifsIds = new ArrayList<Long>();
		}
		return objectifsIds;
	}

	public void setObjectifsIds(List<Long> objectifsIds) {
		this.objectifsIds = objectifsIds;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", hfrUserName=" + hfrUserName
				+ ", taille=" + taille + ", sexe=" + sexe
				+ ", dateNaissance=" + dateNaissance + ", objectifsIds="
				+ objectifsIds + "]";
	}
	
	
}
