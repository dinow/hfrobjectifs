package be.dno.hfrobjectifs.tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.entities.Objectif;
import be.dno.hfrobjectifs.entities.User;
import be.dno.hfrobjectifs.persistence.GenericDao;

public class GeneralHelper {
	private static final GenericDao<User> userDao = new GenericDao<User>(User.class);
	private static final UserService userService = UserServiceFactory.getUserService();
	private static final GenericDao<Evenement> eventDao = new GenericDao<Evenement>(Evenement.class);
	private static final GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
	
	private static final String[] eventTypes = "Route;Trail;Piste;Orientation".split(";");
	static class EventComparator implements Comparator<Evenement>
	 {
	     public int compare(Evenement c1, Evenement c2)
	     {
	         return c1.getDateEvenement().compareTo(c2.getDateEvenement());
	     }
	 }
	static class UserComparator implements Comparator<User>
	 {
	     public int compare(User c1, User c2)
	     {
	         return c1.getHfrUserName().compareTo(c2.getHfrUserName());
	     }
	 }
	
	
	static class ObjectifComparator implements Comparator<Objectif>
	 {
	     public int compare(Objectif c1, Objectif c2)
	     {
	         return Integer.compare(c2.getAnnee(),c1.getAnnee());
	     }
	 }
	
	public static int setEventBooleanValue(Evenement event, User inUser){
		List<User> allUsers = userDao.getAll();
		for (User user : allUsers){
			if (user.getUserID().equals(inUser)){
				user = inUser;
			}
			if (user.getEventParticipationIds().contains(event.getId())){
				//check if it's not only the creator
				if (!user.getEventsIds().contains(event.getId())){
					event.setUsedBySomeoneElse(true);
					eventDao.update(event);
					return 1;
				}
			}
		}
		event.setUsedBySomeoneElse(false);
		eventDao.update(event);
		return 0;
	}
	
	public static boolean isEmptyString(String input){
		return input.trim().isEmpty() || input.trim().equals("0") || input.trim().equals("0.0");
	}

	public static String getObjectifClass(Objectif objectif){
		if (objectif.getTempsPrevu() > 0d){
			if (objectif.getTempsRealise() == 0d){
				return "resultNeutral";
			}else{
				if (objectif.getTempsRealise() < objectif.getTempsPrevu()){
					return "resultOk";
				}else{
					return "resultKo";
				}
			}
		}
		if (objectif.getPoidsPrevu() > 0f){
			if (objectif.getPoidsRealise() == 0f){
				return "resultNeutral";
			}else{
				if (objectif.getPoidsRealise() < objectif.getPoidsPrevu()){
					return "resultOk";
				}else{
					return "resultKo";
				}
			}
		}
		return "resultNeutral";
		
	}
	
	@SuppressWarnings("rawtypes")
	public static List getPublicObjectivesForUser(User user){
		List<Objectif> retList = new ArrayList<Objectif>();
		for (Long id : user.getObjectifsIds()){
			Objectif obj = objectifDao.getById(id);
			if (!obj.isObjectifPrive()){
				retList.add(obj);
			}
		}
		return retList;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getParticipationsForUser(User user){
		List<Evenement> retList = new ArrayList<Evenement>();
		for (Long id : user.getEventParticipationIds()){
			retList.add(eventDao.getById(id));
		}
		return retList;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getParticipants(Evenement evt){
		List<User> retList = new ArrayList<User>();
		List<User> allUsers = userDao.getAll();
		for (User user : allUsers){
			if (user.getEventParticipationIds().contains(evt.getId())){
				retList.add(user);
			}
		}
		
		return retList;
	}
	
	public static boolean isParticipating(Evenement evt){
		UserService userService = UserServiceFactory.getUserService();
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		return user.getEventParticipationIds().contains(evt.getId());
	}
	
	public static String getEventsTypes(Evenement event){
		String selectedType = "";
		if (event != null){
			selectedType = event.getType();
		}
		String ret = "";
		for (String type : eventTypes){
			ret += "<option "+ (selectedType.equalsIgnoreCase(type) ? "selected" : "")+">"+type+"</option>";
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static String getEventsDropDown(Objectif objectif){
		Long selectedEvent = 0l;
		if (objectif != null){
			selectedEvent = objectif.getEvenementId();
		}
		Map<String,List<Evenement>> allEvents = GeneralHelper.getEventsForUser();
		List<Evenement> events = allEvents.get("my");
		events.addAll(allEvents.get("their"));
		String ret = "<option value=\"-1\"> </option>";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Evenement evt : events){
			ret += "<option value=\""+evt.getId()+"\" "+ (selectedEvent.longValue() == evt.getId().longValue() ? "selected" : "")+"  >"+evt.getName()+" ("+sdf.format(evt.getDateEvenement())+")</option>";
		}
		
		
		return ret;
	}
	
	public static String getNextYearsForDropDown(int yearsToCreate, Objectif objectif){
		int selectedYear = 0;
		if (objectif != null){
			selectedYear = objectif.getAnnee();
		}
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		int thisYear = gc.get(GregorianCalendar.YEAR);
		String ret = "";
		for (int i = 0; i < yearsToCreate; i++){
			ret += "<option "+ (selectedYear == (thisYear+i) ? "selected" : "")+" >"+(thisYear+i)+"</option>";
		}
		return ret;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getUsers(){
		List<User> users = userDao.getAll();
		List<User> usersWithStuff = new ArrayList<User>();
		for (User user : users) {
			if (!user.getObjectifsIds().isEmpty() && ! user.getEventParticipationIds().isEmpty()){
				usersWithStuff.add(user);
			}
		}
		Collections.sort(usersWithStuff, new UserComparator());
		return usersWithStuff;
	}
	
	@SuppressWarnings("rawtypes")
	public static List getObjectivesForUser(){
		if (userService.getCurrentUser() == null){
			return new ArrayList<Objectif>();
		}
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
		List<Long> objectivesIds = user.getObjectifsIds();
		List<Objectif> objectives = new ArrayList<Objectif>();
		for (Long objId : objectivesIds){
			objectives.add(objectifDao.getById(objId));
		}
		Collections.sort(objectives, new ObjectifComparator());
		return objectives;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map getEventsForUser(){
		if (userService.getCurrentUser() == null){
			Map<String, List<Evenement>> map = new HashMap<String,List<Evenement>>();
			map.put("my", new ArrayList<Evenement>());
			map.put("their", new ArrayList<Evenement>());
			return map;
		}
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		GenericDao<Evenement> eventDao = new GenericDao<Evenement>(Evenement.class);
		List<Long> objectivesIds = user.getEventsIds();
		List<Evenement> objectives = new ArrayList<Evenement>();
		for (Long objId : objectivesIds){
			objectives.add(eventDao.getById(objId));
		}
		
		List<Evenement> allEvents = eventDao.getAll();
		List<Evenement> theirs = new ArrayList<Evenement>();
		for (Evenement evt : allEvents){
			if (!objectives.contains(evt)){
				theirs.add(evt);
			}
		}
		Map<String, List<Evenement>> map = new HashMap<String,List<Evenement>>();
		Collections.sort(objectives, new EventComparator());
		Collections.sort(theirs, new EventComparator());
		map.put("my", objectives);
		map.put("their", theirs);
		
		return map;
	}
	
	public static Evenement getEventFromObjectif(Objectif objectif){
		return eventDao.getById(objectif.getEvenementId());
	}
	
	public static String noNull(String in){
		return in == null ? "" : in;
	}
}
