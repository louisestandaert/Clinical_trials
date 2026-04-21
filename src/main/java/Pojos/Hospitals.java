package Pojos;

import java.util.Objects;

public class Hospitals {
	
	public String hospitalName;
	public String hospitalLocation;
	public int hospitalId;
	
	
	public Hospitals() {
	}
	
	public Hospitals(String hospitalName, String hospitalLocation, int hospitalId) {
		this.hospitalName = hospitalName;
		this.hospitalLocation = hospitalLocation;
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
		return hospitalLocation;
	}
	
	public void setHospitalLocation(String hospitalLocation) {
		this.hospitalLocation=hospitalLocation;
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
                ", location=" + hospitalLocation +
                '}';
	}
}
