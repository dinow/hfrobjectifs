package be.dno.hfrobjectifs.jsp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import be.dno.hfrobjectifs.entities.Evenement;
import be.dno.hfrobjectifs.entities.User;
import be.dno.hfrobjectifs.persistence.GenericDao;
import be.dno.hfrobjectifs.tools.GeneralHelper;

public class JspHelper {
	
	private static final String[] eventTypes = "Route;Trail;Piste;Orientation".split(";");
	
	public static String getEventsTypes(){
		String ret = "";
		for (String type : eventTypes){
			ret += "<option>"+type+"</option>";
		}
		return ret;
	}
	
	public static String getEventsDropDown(){
		UserService userService = UserServiceFactory.getUserService();
		GenericDao<User> userDao = new GenericDao<User>(User.class);
		User user = userDao.getById(userService.getCurrentUser().getUserId());
		Map<String,List<Evenement>> allEvents = GeneralHelper.getEventsForUser(user);
		
		List<Evenement> events = allEvents.get("my");
		events.addAll(allEvents.get("their"));
		String ret = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (Evenement evt : events){
			ret += "<option value=\""+evt.getId()+"\">"+evt.getName()+" ("+sdf.format(evt.getDateEvenement())+")</option>";
		}
		
		
		return ret;
	}
	
	public static String getNextYearsForDropDown(int yearsToCreate){
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(new Date());
		int thisYear = gc.get(GregorianCalendar.YEAR);
		String ret = "";
		for (int i = 0; i < yearsToCreate; i++){
			ret += "<option>"+(thisYear+i)+"</option>";
		}
		return ret;
	}
}
