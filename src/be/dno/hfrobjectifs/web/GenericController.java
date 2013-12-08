package be.dno.hfrobjectifs.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.entities.Objectif;
import be.dno.hfrobjectifs.entities.User;
import be.dno.hfrobjectifs.persistence.GenericDao;
import be.dno.hfrobjectifs.tools.GeneralHelper;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class GenericController {
	private static final Logger log = Logger.getLogger(GenericController.class.getName());
	private static GenericDao<User> userDao = new GenericDao<User>(User.class);
	
	UserService userService = UserServiceFactory.getUserService();
	
	@RequestMapping(value = "/myObjectives", method = RequestMethod.GET)
	public ModelAndView myObjectives(HttpServletRequest request) {
		log.info("gotohome called");
		if (userService.getCurrentUser() != null){
			User user = userDao.getById(userService.getCurrentUser().getUserId());
			if (user == null){
				log.info("First login of " + userService.getCurrentUser().getNickname());
				user = new User();
				user.setUserID(userService.getCurrentUser().getUserId());
				user = userDao.create(user);
			}
			return new ModelAndView("myObjectives", "objectives", GeneralHelper.getObjectivesForUser(user));
		}
		return new ModelAndView("myObjectives");
	}
	
	@RequestMapping(value = "/myPreferences", method = RequestMethod.GET)
	public ModelAndView myPreferences(HttpServletRequest request) {
		log.info("myPreferences called");
		return new ModelAndView("myPreferences");
	}
	
	@RequestMapping(value = "/myEvents", method = RequestMethod.GET)
	public ModelAndView myEvents(HttpServletRequest request) {
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		log.info("myEvents called");
		return new ModelAndView("myEvents","events",GeneralHelper.getEventsForUser(user));
	}
	
	@RequestMapping(value = "/allRanking", method = RequestMethod.GET)
	public ModelAndView allRanking(HttpServletRequest request) {
		log.info("allRanking called");
		return new ModelAndView("allRanking");
	}
	
	@RequestMapping(value = "/delete_objectif", method = RequestMethod.POST)
	public ModelAndView delete_objectif(HttpServletRequest request){
		String objectifId = request.getParameter("objectifId");
		
		Long lObjectifId = Long.parseLong(objectifId);
		
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		
		user.getObjectifsIds().remove(lObjectifId);
		userDao.update(user);
		
		GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
		objectifDao.delete(lObjectifId);
		
		return new ModelAndView("myObjectives","objectives", GeneralHelper.getObjectivesForUser(user) );
	}
	
	@RequestMapping(value = "/delete_event", method = RequestMethod.POST)
	public ModelAndView delete_event(HttpServletRequest request){
		String eventId = request.getParameter("eventId");
		
		Long lEventId = Long.parseLong(eventId);
		
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		
		user.getEventsIds().remove(lEventId);
		userDao.update(user);
		
		GenericDao<Evenement> objectifDao = new GenericDao<Evenement>(Evenement.class);
		objectifDao.delete(lEventId);
		
		return new ModelAndView("myEvents","events", GeneralHelper.getEventsForUser(user) );
	}
	
	
	
	
	@RequestMapping(value = "/save_event", method = RequestMethod.POST)
	public ModelAndView save_event(HttpServletRequest request) {
		GenericDao<Evenement> evenementDao = new GenericDao<Evenement>(Evenement.class);
		
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		if (user.getEventsIds() == null){
			user.setEventsIds(new ArrayList<Long>());
		}
		
		String nom = request.getParameter("name");
		String date = request.getParameter("date");
		String pays = request.getParameter("pays");
		String ville = request.getParameter("city");
		String distance = request.getParameter("length");
		String urlOff  = request.getParameter("officialUrl");
		String urlResult = request.getParameter("resultUrl");
		String type = request.getParameter("type");
		String prive = request.getParameter("evenementPrive");
		
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
		
		if (prive == null){
			prive = "off";
		}
		
		Evenement event = new Evenement();
		event.setName(nom);
		event.setDistance(dist);
		event.setUrlEvenement(urlOff);
		event.setUrlResultats(urlResult);
		event.setVille(ville);
		event.setDateEvenement(new java.util.Date());
		event.setEvenementPrive(prive.equals("on"));
		event.setPays(pays);
		event.setType(type);
		event.setDateEvenement(dateEvt);
		
		
		event = evenementDao.create(event);
		user.getEventsIds().add(event.getId());
		
		userDao.update(user);
		
		return new ModelAndView("myEvents","events", GeneralHelper.getEventsForUser(user) );
		
	}
	
	
	@RequestMapping(value = "/save_objectif", method = RequestMethod.POST)
	public ModelAndView save_objectif(HttpServletRequest request) {
		GenericDao<Objectif> objectifDao = new GenericDao<Objectif>(Objectif.class);
		log.info("save_objectif called");
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		String evenementId = request.getParameter("evenement");
		String annee = request.getParameter("annee");
		String tempsPrevu = request.getParameter("tempsPrevu");
		String poids = request.getParameter("poids");
		String objectifPrive = request.getParameter("objectifPrive");
		
		
		if (objectifPrive == null){
			objectifPrive = "off";
		}
		
		if (annee.isEmpty()){
			return new ModelAndView("myObjectives","objectives", GeneralHelper.getObjectivesForUser(user) );
		}
		float fPoids = 0f;
		if (!poids.isEmpty()){
			fPoids = Float.parseFloat(poids);
		}
		
		int tempsSeconds = 0;
		if(!tempsPrevu.isEmpty()){
			
		}
		
		Objectif objectif = new Objectif();
		
		if (!evenementId.isEmpty()){
			objectif.setEvenementId(Long.parseLong(evenementId));
		}
		
		
		objectif.setAnnee(Integer.parseInt(annee));
		objectif.setObjectifPrive(objectifPrive.equals("on"));
		objectif.setTempsPrevu(tempsSeconds);
		objectif.setPoids(fPoids);
		
		objectif = objectifDao.create(objectif);
	
		
		if (user.getObjectifsIds() == null){
			user.setObjectifsIds(new ArrayList<Long>());
		}
		user.getObjectifsIds().add(objectif.getId());
		userDao.update(user);
		return new ModelAndView("myObjectives","objectives", GeneralHelper.getObjectivesForUser(user) );
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		if (userService.getCurrentUser() != null){
			User user = userDao.getById(userService.getCurrentUser().getUserId());
			if (user == null){
				log.info("First login of " + userService.getCurrentUser().getNickname());
				user = new User();
				user.setUserID(userService.getCurrentUser().getUserId());
				user = userDao.create(user);
				return new ModelAndView("editProfile","user",user);
			}	
			return new ModelAndView("myObjectives", "objectives", GeneralHelper.getObjectivesForUser(user));
		}
		return new ModelAndView("myObjectives");
	}
	
}
