package it.polito.tdp.flight.model;

import com.javadocmd.simplelatlng.LatLng;

public class Airport implements Comparable<Airport> {

	private int airportId;
	private String name;
	private String city;
	private String country;
	private String iataFaa;
	private String icao;
    private LatLng coord;
	private float timezone;
	private String dst;
	private String tz;
	
	private int num_turisti;

	public Airport(int airportId, String name, String city, String country, String iataFaa, String icao,
			double latitude, double longitude, float timezone, String dst, String tz) {
		super();
		this.airportId = airportId;
		this.name = name;
		this.city = city;
		this.country = country;
		this.iataFaa = iataFaa;
		this.icao = icao;
		this.coord = new LatLng(latitude, longitude);
		this.timezone = timezone;
		this.dst = dst;
		this.tz = tz;
	}

	public int getAirportId() {
		return airportId;
	}

	public void setAirportId(int airportId) {
		this.airportId = airportId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIataFaa() {
		return iataFaa;
	}

	public void setIataFaa(String iataFaa) {
		this.iataFaa = iataFaa;
	}

	public String getIcao() {
		return icao;
	}

	public void setIcao(String icao) {
		this.icao = icao;
	}

	

	public float getTimezone() {
		return timezone;
	}

	public void setTimezone(float timezone) {
		this.timezone = timezone;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public int getTuristi() {
		return this.num_turisti;
	}
	
	public void azzeraTuristi() {
		this.num_turisti = 0;
	}
	
	public void arrivaTurista(Integer tu) {
		this.num_turisti+=tu;
		
	}
	
	public void lasciaTurist() {
		this.num_turisti--;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + airportId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (airportId != other.airportId)
			return false;
		return true;
	}

	// @Override
	// public String toString() {
	// return "Airport [airportId=" + airportId + ", name=" + name + ", city=" +
	// city + ", country=" + country
	// + ", iataFaa=" + iataFaa + ", icao=" + icao + ", latitude=" + latitude +
	// ", longitude=" + longitude
	// + ", timezone=" + timezone + ", dst=" + dst + ", tz=" + tz + "]";
	// }

	@Override
	public String toString() {
		return name;
	}

	public LatLng getCoord() {
		return coord;
	}

	public void setCoord(LatLng coord) {
		this.coord = coord;
	}

	@Override
	public int compareTo(Airport a) {
		// TODO Auto-generated method stub
		return a.num_turisti-this.num_turisti;
	}
}
