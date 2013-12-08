package be.dno.hfrobjectifs.jsp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
		List<Evenement> events = GeneralHelper.getEventsForUser(user);
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
