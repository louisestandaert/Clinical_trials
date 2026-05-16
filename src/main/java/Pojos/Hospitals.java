package Pojos;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Hospitals")
@XmlAccessorType(XmlAccessType.FIELD)


public class Hospitals {
	
	@XmlAttribute(name = "hospitalName")
	public String hospitalName;
	
	@XmlAttribute(name = "hospitalCity")
	public String hospitalCity;
	
	@XmlAttribute(name = "hospitalId")
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
                ", city=" + hospitalCity +
                '}' + "\n";
	}
}
