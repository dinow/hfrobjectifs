package be.dno.hfrobjectifs.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.entities.Objectif;
import be.dno.hfrobjectifs.entities.User;
import be.dno.hfrobjectifs.persistence.EventDao;
import be.dno.hfrobjectifs.persistence.GenericDao;
import be.dno.hfrobjectifs.tools.CalcHelper;
import be.dno.hfrobjectifs.tools.GeneralHelper;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class GenericController {
	private static final Logger log = Logger.getLogger(GenericController.class.getName());
	private static final GenericDao<User> userDao = new GenericDao<User>(User.class);
	private static final GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
	private static final GenericDao<Evenement> eventDao = new GenericDao<Evenement>(Evenement.class);
	private static final UserService userService = UserServiceFactory.getUserService();
	
	private static final int USER_NOT_CONNECTED = 1;
	private static final int USER_CREATED = 2;
	private static final int USER_OK = 3;
	
	@RequestMapping(value = "/myObjectives", method = RequestMethod.GET)
	public ModelAndView myObjectives(HttpServletRequest request) {
		int userCheck = genericUserCheck();
		if (userCheck == USER_OK){
			return new ModelAndView("myObjectives");
		}
		if (userCheck == USER_CREATED){
			User user = userDao.getById(userService.getCurrentUser().getUserId());
			return new ModelAndView("editProfile","user",user);
		}
		return new ModelAndView("myObjectives");
	}
	
	@RequestMapping(value = "/save_profile", method = RequestMethod.POST)
	public ModelAndView editUser(HttpServletRequest request) {
		String hfrUserName = request.getParameter("hfrUserName");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		
		if (hfrUserName != null && !hfrUserName.isEmpty()){
			user.setHfrUserName(hfrUserName);
		}
		userDao.update(user);
		return new ModelAndView("editProfile","user",user);
	}
	
	
	@RequestMapping(value = "/showUsers", method = RequestMethod.GET)
	public ModelAndView showUsers(HttpServletRequest request) {
		return new ModelAndView("showUsers");
	}
	@RequestMapping(value = "/myEvents", method = RequestMethod.GET)
	public ModelAndView myEvents(HttpServletRequest request) {
		return new ModelAndView("myEvents");
	}
	
	@RequestMapping(value = "/allRanking", method = RequestMethod.GET)
	public ModelAndView allRanking(HttpServletRequest request) {
		return new ModelAndView("allRanking");
	}
	
	@RequestMapping(value = "/delete_objectif", method = RequestMethod.POST)
	public ModelAndView delete_objectif(HttpServletRequest request){
		String objectifId = request.getParameter("objectifId");
		Long lObjectifId = Long.parseLong(objectifId);
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		user.getObjectifsIds().remove(lObjectifId);
		userDao.update(user);
		objectifDao.delete(lObjectifId);
		return new ModelAndView("myObjectives","message", "objectif supprimé" );
	}
	
	@RequestMapping(value = "/delete_event", method = RequestMethod.POST)
	public ModelAndView delete_event(HttpServletRequest request){
		String eventId = request.getParameter("eventId");
		Long lEventId = Long.parseLong(eventId);
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		user.getEventsIds().remove(lEventId);
		userDao.update(user);
		eventDao.delete(lEventId);
		return new ModelAndView("myEvents","message","Evenement supprimé");
	}
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfile(HttpServletRequest request) {
		return new ModelAndView("editProfile");
	}

	@RequestMapping(value = "/removeParticipation_event", method = RequestMethod.POST)
	public ModelAndView removeParticipation_event(HttpServletRequest request) {
		String eventId = request.getParameter("eventId");
		Long lEventId = Long.parseLong(eventId);
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		user.getEventParticipationIds().remove(lEventId);
		userDao.update(user);
		GeneralHelper.setEventBooleanValue(eventDao.getById(lEventId), user);
		return new ModelAndView("myEvents","message","Participation supprimée");
	}
	
	@RequestMapping(value = "/participate_event", method = RequestMethod.POST)
	public ModelAndView participate_event(HttpServletRequest request) {
		String eventId = request.getParameter("eventId");
		Long lEventId = Long.parseLong(eventId);
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		user.getEventParticipationIds().add(lEventId);
		userDao.update(user);
		GeneralHelper.setEventBooleanValue(eventDao.getById(lEventId), user);
		return new ModelAndView("myEvents","message","Participation enregistrée");
	}
	
	@RequestMapping(value = "/save_event", method = RequestMethod.POST)
	public ModelAndView save_event(HttpServletRequest request) {
		
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		
		EventDao evtDao = new EventDao();
		
		
		
		String nom = GeneralHelper.noNull(request.getParameter("name"));
		String date = GeneralHelper.noNull(request.getParameter("date"));
		String pays = GeneralHelper.noNull(request.getParameter("pays"));
		String ville = GeneralHelper.noNull(request.getParameter("city"));
		String distance = GeneralHelper.noNull(request.getParameter("length"));
		String urlOff  = GeneralHelper.noNull(request.getParameter("officialUrl"));
		String urlResult = GeneralHelper.noNull(request.getParameter("resultUrl"));
		String type = GeneralHelper.noNull(request.getParameter("type"));
		String eventId = GeneralHelper.noNull(request.getParameter("eventId"));
		
		if (nom.isEmpty()){
			nom = System.currentTimeMillis() + "";
		}
		
		
		
		
		Date dateEvt = new java.util.Date();
		if (!date.isEmpty()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dateEvt = sdf.parse(date);
			} catch (ParseException e) {
				log.severe(e.getMessage());
			}
		}
		
		float dist = 0f;
		if (!distance.isEmpty()){
			dist = Float.parseFloat(distance);
		}
		
		
		Evenement event;
		if(eventId == null || eventId.isEmpty()){
			event = new Evenement();
		}else{
			event = eventDao.getById(Long.parseLong(eventId));
		}
	
		event.setName(nom);
		event.setDistance(dist);
		event.setUrlEvenement(urlOff);
		event.setUrlResultats(urlResult);
		event.setVille(ville);
		event.setDateEvenement(new java.util.Date());
		event.setPays(pays);
		event.setType(type);
		event.setDateEvenement(dateEvt);
		
		if(eventId == null || eventId.isEmpty()){
			List<Evenement> existings = evtDao.getEventByName(nom);
			if (existings.size() > 0){
				return new ModelAndView("myEvents","message","Un evenement avec ce nom existe déjà");
			}
			event = eventDao.create(event);
			user.getEventsIds().add(event.getId());
			userDao.update(user);
		}else{
			eventDao.update(event);
		}
		return new ModelAndView("myEvents");
	}
	
	@RequestMapping(value= "/edit_objectif", method = RequestMethod.POST)
	public ModelAndView edit_objectif(HttpServletRequest request) {
		String objId 	= GeneralHelper.noNull(request.getParameter("objectifId"));
		Long lObjId = Long.parseLong(objId);
		return new ModelAndView("myObjectives", "objectif", objectifDao.getById(lObjId) );
	}
	
	@RequestMapping(value= "/edit_event", method = RequestMethod.POST)
	public ModelAndView edit_event(HttpServletRequest request) {
		String objId 	= GeneralHelper.noNull(request.getParameter("eventId"));
		Long lObjId = Long.parseLong(objId);
		return new ModelAndView("myEvents", "inputEvent", eventDao.getById(lObjId) );
	}
	
	@RequestMapping(value = "/save_objectif", method = RequestMethod.POST)
	public ModelAndView save_objectif(HttpServletRequest request) {
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		
		String name 	= GeneralHelper.noNull(request.getParameter("name"));
		String evenementId 	= GeneralHelper.noNull(request.getParameter("evenement"));
		String annee 		= GeneralHelper.noNull(request.getParameter("annee"));
		String tempsPrevu 	= GeneralHelper.noNull(request.getParameter("tempsPrevu"));
		String tempsRealise 	= GeneralHelper.noNull(request.getParameter("tempsRealise"));
		String poidsPrevu 		= GeneralHelper.noNull(request.getParameter("poidsPrevu"));
		String poidsRealise 		= GeneralHelper.noNull(request.getParameter("poidsRealise"));
		String objectifPrive = GeneralHelper.noNull(request.getParameter("objectifPrive"));
		String linkActivity = GeneralHelper.noNull(request.getParameter("linkActivite"));
		String linkHfr = GeneralHelper.noNull(request.getParameter("linkCR"));
		String objectifId = request.getParameter("objectifId");
		
		if (annee.isEmpty()){
			return new ModelAndView("myObjectives","message", "Année incorrecte");
		}
		
		double tempsPrevuSeconds = 0;
		if (!tempsPrevu.isEmpty()){
			tempsPrevuSeconds = CalcHelper.getTotSecs(tempsPrevu);
		}
		
		double tempsRealiseSeconds = 0;
		if (!tempsRealise.isEmpty()){
			tempsRealiseSeconds = CalcHelper.getTotSecs(tempsRealise);
		}
		
		if (objectifPrive.isEmpty()){
			objectifPrive = "off";
		}
		
		float fPoidsP = 0f;
		if (!poidsPrevu.isEmpty()){
			fPoidsP = Float.parseFloat(poidsPrevu);
		}
		float fPoidsR = 0f;
		if (!poidsRealise.isEmpty()){
			fPoidsR = Float.parseFloat(poidsRealise);
		}
		
		Objectif objectif;
		if(objectifId == null){
			objectif = new Objectif();
		}else{
			objectif = objectifDao.getById(Long.parseLong(objectifId));
		}
		
		if (!evenementId.trim().isEmpty()){
			objectif.setEvenementId(Long.parseLong(evenementId));
		}
		
		objectif.setName(name);
		objectif.setAnnee(Integer.parseInt(annee));
		objectif.setObjectifPrive(objectifPrive.equals("on"));
		objectif.setTempsPrevu(tempsPrevuSeconds);
		objectif.setTempsRealise(tempsRealiseSeconds);
		objectif.setPoidsPrevu(fPoidsP);
		objectif.setPoidsRealise(fPoidsR);
		objectif.setLienActivite(linkActivity);
		objectif.setLienHFR(linkHfr);
		
		if(objectifId == null){
			objectif = objectifDao.create(objectif);
			user.getObjectifsIds().add(objectif.getId());
			userDao.update(user);
		}else{
			objectifDao.update(objectif);
		}
		return new ModelAndView("myObjectives" );
	}
		
	public int genericUserCheck() {
		if (userService.getCurrentUser() != null){
			User user = userDao.getById(userService.getCurrentUser().getUserId());
			if (user == null){
				user = new User();
				user.setUserID(userService.getCurrentUser().getUserId());
				user.setHfrUserName("");
				user = userDao.create(user);
				return USER_CREATED;
			}	
			return USER_OK;
		}
		return USER_NOT_CONNECTED;
	}
	
}
