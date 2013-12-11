package be.dno.hfrobjectifs.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.entities.Objectif;
import be.dno.hfrobjectifs.entities.User;
import be.dno.hfrobjectifs.persistence.GenericDao;

public class GeneralHelper {
	
	public static Map<String, List<Objectif>> getObjectivesForUser(User user){
		GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
		List<Long> objectivesIds = user.getObjectifsIds();
		List<Objectif> objectives = new ArrayList<Objectif>();
		for (Long objId : objectivesIds){
			objectives.add(objectifDao.getById(objId));
		}
		
		List<Objectif> allObjectives = objectifDao.getAll();
		List<Objectif> theirs = new ArrayList<Objectif>();
		for (Objectif evt : allObjectives){
			if (!evt.isObjectifPrive()){
				if (!objectives.contains(evt)){
					theirs.add(evt);
				}
			}
		}
		Map<String, List<Objectif>> map = new HashMap<String,List<Objectif>>();
		map.put("my", objectives);
		map.put("their", theirs);
		
		return map;
	}
	
	public static Map<String, List<Objectif>> getObjectivesForUser(String userId){
		GenericDao<User> userDao = new GenericDao<User>(User.class);
		User user = userDao.getById(userId);
		return getObjectivesForUser(user);
	}
	
	public static Map<String, List<Evenement>> getEventsForUser(User user){
		GenericDao<Evenement> eventDao = new GenericDao<Evenement>(Evenement.class);
		List<Long> objectivesIds = user.getEventsIds();
		List<Evenement> objectives = new ArrayList<Evenement>();
		for (Long objId : objectivesIds){
			objectives.add(eventDao.getById(objId));
		}
		
		List<Evenement> allEvents = eventDao.getAll();
		List<Evenement> theirs = new ArrayList<Evenement>();
		for (Evenement evt : allEvents){
			if (!evt.isEvenementPrive()){
				if (!objectives.contains(evt)){
					theirs.add(evt);
				}
			}
		}
		Map<String, List<Evenement>> map = new HashMap<String,List<Evenement>>();
		map.put("my", objectives);
		map.put("their", theirs);
		
		return map;
	}
	
	public static Map<String, List<Evenement>> getEventsForUser(String userId){
		GenericDao<User> userDao = new GenericDao<User>(User.class);
		User user = userDao.getById(userId);
		return getEventsForUser(user);
	}
	
	public static String noNull(String in){
		return in == null ? "" : in;
	}
}
