package be.dno.hfrobjectifs.tools;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalcHelper {	
	private static DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
	
	/**
	 * 
	 * @param paramInt
	 * @return
	 */
	public static String toDoubleDigit(int paramInt){
		if (Math.abs(paramInt) < 10){
			return ("0" + paramInt);
		}
		return ""+paramInt;
	}

	/**
	 * 
	 * @param paramInt
	 * @return
	 */
	public static String toDoubleDecimal(double paramInt){
		return df.format(paramInt);
	}

	public static Double toDouble(String input) throws Exception{
		if (input.contains(",")){
			input = input.replaceAll(",",".");
		}
		return df.parse(input).doubleValue();
	}

	/**
	 * 
	 * @param totalSeconds
	 * @return a human readeable time
	 */
	public static String toTime(double totalSeconds){
		int hours = (int)(totalSeconds / 3600.0D);
		int minutes = (int)(totalSeconds % 3600) / 60;
		int seconds = (int)(totalSeconds% 60.0D);
		return toDoubleDigit(hours) + ":" + toDoubleDigit(minutes) + ":" + toDoubleDigit(seconds);
	}

	/**
	 * 
	 * @param time in format hh:mm:ss or mm:ss or ss
	 * @return the seconds in the given time
	 */
	public static double getTotSecs(String time){
		String[] arrayOfString = time.split(":");
		double hour = 0d;
		double minutes = 0d;
		double seconds = 0d;
		if (arrayOfString.length == 3){
			hour = Double.valueOf(arrayOfString[0]);
			minutes = Double.valueOf(arrayOfString[1]);
			seconds = Double.valueOf(arrayOfString[2]);
		}
		if (arrayOfString.length == 2){
			minutes = Double.valueOf(arrayOfString[0]);
			seconds = Double.valueOf(arrayOfString[1]);
		}else if (arrayOfString.length == 1){
			seconds = Double.valueOf(arrayOfString[0]);
		}
		return Double.valueOf(60.0D * (60.0D * hour) + 60.0D * minutes + seconds);
	}

}
