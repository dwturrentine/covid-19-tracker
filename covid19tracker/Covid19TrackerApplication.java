package dev.dwt.covid19tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/* This application was created by Spring Initializr. 
 * The settings can be found here: [https://tinyurl.com/y3a7bvzw] 
 * The data used in this project was provided by the 
 * Center for Systems Science and Engineering (CSSE) 
 * at Johns Hopkins University: [https://github.com/CSSEGISandData/COVID-19]
 * The specific data set used in this project is the 
 * Time Series Summary for Confirmed Cases Globally, 
 * and is updated daily: [https://tinyurl.com/cssetimeglobal]
 * Apache Commons CSV was used to parse the 
 * data file: [https://commons.apache.org/proper/commons-csv/] 
 * The Maven dependency was added to the 
 * pom.xml file post project creation by Spring Initializr.
 */

/* -- One More Thing... --
 * 
 * Typically you do not save a state in a service; you will want to make services state-less in 
 * Spring, particularly when using multiple services.
 * 
 * Additionally, you would also write test cases. No test cases were written for this project.
 *
 * Finally, when creating any project utilizing public or private date, the source would need 
 * to be validated.
 * 
*/

@SpringBootApplication

/* The @EnableScheduling annotation is added to direct Spring to schedule methods.
 * Spring will wrap in a proxy to call @Schedule methods.
 */

@EnableScheduling

public class Covid19TrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TrackerApplication.class, args);
	}
}


