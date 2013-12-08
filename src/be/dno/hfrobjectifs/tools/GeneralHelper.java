package be.dno.hfrobjectifs.tools;

import java.util.ArrayList;
import java.util.List;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.entities.Objectif;
import be.dno.hfrobjectifs.entities.User;
import be.dno.hfrobjectifs.persistence.GenericDao;

public class GeneralHelper {
	
	public static List<Objectif> getObjectivesForUser(User user){
		GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
		List<Long> objectivesIds = user.getObjectifsIds();
		List<Objectif> objectives = new ArrayList<Objectif>();
		if (objectivesIds == null) return objectives;
		for (Long objId : objectivesIds){
			objectives.add(objectifDao.getById(objId));
		}
		return objectives;
	}
	
	public static List<Objectif> getObjectivesForUser(String userId){
		GenericDao<User> userDao = new GenericDao<User>(User.class);
		User user = userDao.getById(userId);
		return getObjectivesForUser(user);
	}
	
	public static List<Evenement> getEventsForUser(User user){
		GenericDao<Evenement> objectifDao = new GenericDao<Evenement>(Evenement.class);
		List<Long> objectivesIds = user.getEventsIds();
		List<Evenement> objectives = new ArrayList<Evenement>();
		if (objectivesIds == null) return objectives;
		for (Long objId : objectivesIds){
			objectives.add(objectifDao.getById(objId));
		}
		return objectives;
	}
	
	public static List<Evenement> getEventsForUser(String userId){
		GenericDao<User> userDao = new GenericDao<User>(User.class);
		User user = userDao.getById(userId);
		return getEventsForUser(user);
	}
}
