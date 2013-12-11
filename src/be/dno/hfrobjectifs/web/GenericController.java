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
			User user = userDao.getById(userService.getCurrentUser().getUserId());
			return new ModelAndView("myObjectives", "objectives", GeneralHelper.getObjectivesForUser(user));
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
		return new ModelAndView("myObjectives","user",user);
	}
	
	@RequestMapping(value = "/myEvents", method = RequestMethod.GET)
	public ModelAndView myEvents(HttpServletRequest request) {
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		return new ModelAndView("myEvents","events",GeneralHelper.getEventsForUser(user));
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
		return new ModelAndView("myObjectives","objectives", GeneralHelper.getObjectivesForUser(user) );
	}
	
	@RequestMapping(value = "/delete_event", method = RequestMethod.POST)
	public ModelAndView delete_event(HttpServletRequest request){
		String eventId = request.getParameter("eventId");
		Long lEventId = Long.parseLong(eventId);
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		user.getEventsIds().remove(lEventId);
		userDao.update(user);
		eventDao.delete(lEventId);
		return new ModelAndView("myEvents","events",GeneralHelper.getEventsForUser(user));
	}
	
	
	
	
	@RequestMapping(value = "/save_event", method = RequestMethod.POST)
	public ModelAndView save_event(HttpServletRequest request) {
		
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		if (user.getEventsIds() == null){
			user.setEventsIds(new ArrayList<Long>());
		}
		
		String nom = GeneralHelper.noNull(request.getParameter("name"));
		String date = GeneralHelper.noNull(request.getParameter("date"));
		String pays = GeneralHelper.noNull(request.getParameter("pays"));
		String ville = GeneralHelper.noNull(request.getParameter("city"));
		String distance = GeneralHelper.noNull(request.getParameter("length"));
		String urlOff  = GeneralHelper.noNull(request.getParameter("officialUrl"));
		String urlResult = GeneralHelper.noNull(request.getParameter("resultUrl"));
		String type = GeneralHelper.noNull(request.getParameter("type"));
		String prive = GeneralHelper.noNull(request.getParameter("evenementPrive"));
		
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
		
		
		event = eventDao.create(event);
		user.getEventsIds().add(event.getId());
		userDao.update(user);
		return new ModelAndView("myEvents","events",GeneralHelper.getEventsForUser(user));
	}
	
	
	@RequestMapping(value = "/save_objectif", method = RequestMethod.POST)
	public ModelAndView save_objectif(HttpServletRequest request) {
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		
		String name 	= GeneralHelper.noNull(request.getParameter("name"));
		String evenementId 	= GeneralHelper.noNull(request.getParameter("evenement"));
		String annee 		= GeneralHelper.noNull(request.getParameter("annee"));
		String tempsPrevu 	= GeneralHelper.noNull(request.getParameter("tempsPrevu"));
		String poids 		= GeneralHelper.noNull(request.getParameter("poids"));
		String objectifPrive = GeneralHelper.noNull(request.getParameter("objectifPrive"));
		
		if (annee.isEmpty()){
			return new ModelAndView("myObjectives","objectives", GeneralHelper.getObjectivesForUser(user) );
		}
		
		double tempsSeconds = 0;
		if (!tempsPrevu.isEmpty()){
			tempsSeconds = CalcHelper.getTotSecs(tempsPrevu);
		}
		
		if (objectifPrive.isEmpty()){
			objectifPrive = "off";
		}
		
		float fPoids = 0f;
		if (!poids.isEmpty()){
			fPoids = Float.parseFloat(poids);
		}
		
		Objectif objectif = new Objectif();
		
		if (!evenementId.isEmpty()){
			objectif.setEvenementId(Long.parseLong(evenementId));
		}
		
		objectif.setName(name);
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
		
	public int genericUserCheck() {
		if (userService.getCurrentUser() != null){
			User user = userDao.getById(userService.getCurrentUser().getUserId());
			if (user == null){
				user = new User();
				user.setUserID(userService.getCurrentUser().getUserId());
				user = userDao.create(user);
				return USER_CREATED;
			}	
			return USER_OK;
		}
		return USER_NOT_CONNECTED;
	}
	
}
