package Pojos;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import java.util.Objects;

@XmlRootElement(name = "Descriptions")
@XmlAccessorType(XmlAccessType.FIELD)

public class Description {
	
	@XmlAttribute(name = "descriptionId")
	private int description_id; 
	
	@XmlAttribute(name = "gender")
	private String gender; 
	
	@XmlAttribute(name = "cause")
	private String cause;
	
	@XmlElement(name = "patientId")
	private int patient_id;
	
	public Description(){
	}
	
	public Description(int description_id, String gender, String cause, int patient_id) {
		this.description_id=description_id;
		this.gender=gender;
		this.cause=cause;
		this.patient_id=patient_id;
	}
	
	//Getters y setters
	public int getDescription_id() {
		return description_id;
	}
	
	public void setDescription_id( int description_id) {
		this.description_id=description_id;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender=gender;
	}
	
	public String getCause() {
		return cause;
	}
	
	public void setCause(String cause) {
		this.cause=cause;
	}
	
	public int getPatient_id() {
		return patient_id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Description description = (Description) o;
        return description_id == description.description_id;
		
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(description_id);
    }

	@Override 
	public String toString() {
		return  "Description:" + 
	            "descriptionID=" + description_id +
                ", gender='" + gender + '\'' +
                ", cause=" + cause + '\'' +
                ", patientID=" + patient_id +
                '}';
	}
	
}
