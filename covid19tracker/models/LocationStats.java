package dev.dwt.covid19tracker.models;

// Created model to save information in memory. //

public class LocationStats {
	
	private String state;
	private String county;
	private String country;
	
	/* Member variable created total number of latest cases.
	 * Member variable then set in service.
	 */
	
	private int latestTotalCases;
	
	/* Member variable created for difference in reported cases 
	 * Member variable then set in service.
	 */
	
	private int diffFromPrevDay;
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCounty(String county) {
		return county;
	}
		
	public void setCounty(String county) {
		this.county = county;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	
	@Override
	
	// @toString method to print data. //
	
	public String toString() {
		return "LocationStats [state=" + state + ", county=" + county + ", country=" + country + ", latestTotalCases="
				+ latestTotalCases + "]";
	}
	
	public int getDiffFromPrevDay() {
		return diffFromPrevDay;
	}
	
	public void setDiffFromPrevDay(int diffFromPrevDay) {
		this.diffFromPrevDay = diffFromPrevDay;
	}
	
}
