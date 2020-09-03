package dev.dwt.covid19tracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.dwt.covid19tracker.models.LocationStats;
import dev.dwt.covid19tracker.services.CoronavirusDataService;


// The @Controller annotation is used to make the Spring Controller render in HTML UI. //

@Controller

public class HomeController {
	
	// @Autowired annotation allows access the service. //
	
	@Autowired
	
	// No getter created because this is a private instance. //
	
	CoronavirusDataService coronavirusDataService;
	

	/* The @GetMapping annotation is used to map to the root URL ("/" is the root URL).
	 * When there is @GetMapping to the root URL, it will  return the home template.
	 * This works because of the Thymeleaf dependency.
	 */
	
	@GetMapping("/")
	
	/*This returns a template value. This is not making a rest controller. 
	 * (@RestController is shorthand for all methods in a controller to return REST responses;
	 * this has to be converted to a JSON response, then returned back).
	 * Here, this (home) is being used to return a name that binds to a template.
	 * This should be mapped to an HTML file.
	 * Here the model concept is utilized. When a controller is called and the URL is accessed in 
	 * the controller, you can fetch data and set it in the context of whatever is rendering this 
	 * page. Here, information is placed on the model (data needed, value) in the controller.
	 * When HTML is rendered, information from the model can be accessed and constructed in 
	 * HTML directly. Access is granted in the HTML file using Thymeleaf (syntax).
	 * To show data when the application starts, a value is retrieved from the service layer 
	 * (a member variable of the instance) and is set to an attribute in the model; then, the 
	 * object instance data service is retrieved. Because this is a service, the @Autowired 
	 * annotation is used to gain access to the service. The attribute is then set in the 
	 * controller, and looked up in the template.
	 */
	
	public String home(Model model ) {
		

		/* Here, a new attribute will be created on the model for the header of total reported cases. 
		 * 
		 * This can be done in two ways:
		 * 
		 * [1] loop through 'getAllStats', add them up, then set the value on the attribute, -- or, 
		 * [2] make the attribute a part of coronavirus service.
		 *
		 * Because this is a UI concern and not a business logic one, this application will employ 
		 * option [1] in the controller. 
		 * 
		 * To do this, create a local variable -- "allStats". It will be taking list of objects,
		 * converting them into a stream, then mapping each object to an integer value which is 
		 * the total cases for that record, and then will sum them up and assign them to an integer.
		 */
		
		List<LocationStats> allStats = coronavirusDataService.getAllStats();
		
		// Used to sum allStats into an integer. //
		
		int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
	    
		// Used to add total new reported cases; the difference from the previous day. //
		
		int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
		
		// Setting 'locationStats' to 'allStats'. //
		
		model.addAttribute("locationStats", allStats);
		
		// Setting to 'totalReportedCases'. //
		
		model.addAttribute("totalReportedCases", totalReportedCases);
		
		// Setting 'totalNewCases'. //
		
		model.addAttribute("totalNewCases", totalNewCases);
		return "home";
	}
}
