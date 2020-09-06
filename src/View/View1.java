


/* View1.java */

package View;

import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class View1 {
	public static void consoleReport(String event) {
		Date date;
		SimpleDateFormat simpleDateFormat;
		String formattedTimeString;

		date = new Date();
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formattedTimeString = simpleDateFormat.format(date);

		System.out.println(String.format("[%s] %s", formattedTimeString, event));
	}
}
