package be.dno.hfrobjectifs.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.web.PMFactory;

public class EventDao {
	protected static final PersistenceManager pm = PMFactory.getPM();

	@SuppressWarnings("unchecked")
	public List<Evenement> getEventByName(String name){
		Query q = pm.newQuery(Evenement.class);
		q.setFilter("name == '" + name+"'");
		List<Evenement> results = (List<Evenement>) q.execute();
		q.closeAll();
		if (results == null) results = new ArrayList<Evenement>();
		return results;
	}
}
