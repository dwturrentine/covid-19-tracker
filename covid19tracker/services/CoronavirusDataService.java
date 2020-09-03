package dev.dwt.covid19tracker.services;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import dev.dwt.covid19tracker.models.LocationStats;

/* To make this class work and execute the methods in the service, add the @Service stereotype.
 * As a Spring Service. This allows Spring to work and execute the method in the service when 
 * the application starts.
 */

@Service

public class CoronavirusDataService {
	
	// Sets the data source for application. //
	
	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/"
			+ "COVID-19/master/csse_covid_19_data/csse_covid_19_time_series"
			+ "/time_series_covid19_confirmed_global.csv";

	private List<LocationStats> allStats = new ArrayList<>();
	
	// Created a getter for 'allStats'. //
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
	/* The @PostConstruct annotation is used to execute the method when the 
	 * application starts, but after it makes instance of the class.
	 */
	
	@PostConstruct
	
	/* The @Scheduled annotation added to schedule a run of a method on regular basis (fixed delay, rate, or chronological).
	 * Here, the string specifies how long the run will execute with each asterisk representing
	 * seconds, minutes, hours, days, months, and years, respectively. This application is set to 
	 * run the first hour of every day.
	 */
	
	@Scheduled(cron = "* * 1 * * *")
	
	public void fetchVirusData() throws IOException, InterruptedException {
		
		/* A new list created with a new instance of 'newStats'. 'allStats' is not cleared because  
		 * this prevents error responses when multiple users making information requests
		 * when accessing the service. When the list is done constructing, it will populate 'allStats'
		 * with newStats. The requester will receive the previous information, while new 
		 * information is being populated. Ideally, you don't create a state inside 
		 * of a Spring Service, but for this purpose it's OK.
		 */
		
		List<LocationStats> newStats = new ArrayList<>();

		/* This method will make the Http call to data URL. The call is made with 'HttpClient';
		 * Then with 'HttpRequest' (class), use the builder pattern; The URI will convert the 
		 * URL to a String (URI)
		 */
		
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(VIRUS_DATA_URL))
				.build();
		
		/* A response is received by sending the client the request (which can be a synchronous send),
		 * or a 'BodyHandlerRequest'. This request instructs how the data is to be received.
		 * A response is given, in this case a String. Exceptions are added to the 'client.send' 
		 * request when the operation fails.
		 */
		
		HttpResponse<String> httpResponse = client.send(request,  HttpResponse.BodyHandlers.ofString());
		
		// System.out.println is used to print the data from the URL. //
		
		System.out.println(httpResponse.body());
		
		/* Added the Commons CSV dependency to the pom.xml file, and created a
		 * reader to read the CSV file.
		 */
		
		StringReader csvBodyReader = new StringReader(httpResponse.body());
		
		// Added the code for CSV header auto-detection. //
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
		for (CSVRecord record : records) {
		
			/* Created a new instance of 'LocationStats' from the model previously created.
			 * Now, new instances of 'LocationStats' can be created, and a populate list request 
			 * is made. Previous stats are dropped, and reconstructed from scratch.
			 */
			
			LocationStats locationStat = new LocationStats();
			
			/* Setting to retrieve data from data set. Here, the String follows the header 
			 * conventions in data file.
			 */
			
			locationStat.setState(record.get("Province/State"));
			
			locationStat.setCountry(record.get("Country/Region"));
			
			/* Local variables created to find the difference in confirmed cases from the 
			 * previous day. Commons CSV can specify a number to get column. To do this,
			 * you have to convert the data from string to integer.
			 * (-1 and -2 refer to the column position).
			 */
			
			int latestCases = Integer.parseInt(record.get(record.size() - 1));
			
			int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
			
			/* Setting to 'latestCases' to retrieve data; this reflects the column 
			 * position in the data set (most recent).
			 */
			
			locationStat.setLatestTotalCases(latestCases);
			
			// Setting to find the difference of confirmed cases. //
			
			locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
			
			// Setting to 'newStats'. //
			
			newStats.add(locationStat);
		}
		
		// Add at end of the loop when the method is about to exit (not completely proof!) //
		
		this.allStats = newStats;
	}
}

