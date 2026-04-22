package Pojos;

import java.util.Objects;

public class Hospitals {
	
	public String hospitalName;
	public String hospitalCity;
	public int hospitalId;
	
	
	public Hospitals() {
	}
	
	public Hospitals(String hospitalName, String hospitalCity, int hospitalId) {
		this.hospitalName = hospitalName;
		this.hospitalCity = hospitalCity;
		this.hospitalId = hospitalId;
	}
	
	//Getters y Setters
	public int getHospitalId() {
		return hospitalId;
	}
	
	public void setHospitalId(int hospitalId) {
		this.hospitalId=hospitalId;
	}
	
	public String getHospitalName() {
		return hospitalName;
	}
	
	public void setHospitalName(String hospitalName) {
		this.hospitalName=hospitalName;
	}
	
	public String getHospitalLocation() {
		return hospitalCity;
	}
	
	public void setHospitalLocation(String hospitalCity) {
		this.hospitalCity=hospitalCity;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospitals hospital = (Hospitals) o;
        return hospitalId == hospital.hospitalId;
		
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(hospitalId);
    }

	@Override 
	public String toString() {
		return  "Hospital:" + 
	            "hospitalID=" + hospitalId +
                ", name='" + hospitalName + '\'' +
                ", location=" + hospitalCity +
                '}';
	}
}
