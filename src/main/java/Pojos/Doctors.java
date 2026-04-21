package Pojos;

import java.util.Objects;

public class Doctors {

	private int doctorId;
	private String doctorName;
	private String doctorGender;
	
	public Doctors() {
	}
	
	public Doctors(int doctorId, String doctorName, String doctorGender) {
		this.doctorId=doctorId;
		this.doctorName=doctorName;
		this.doctorGender=doctorGender;
	}
	
	//Getters y setters
	public int getDoctorId() {
		return doctorId;
	}
	
	public void setDoctorId(int doctorId) {
		this.doctorId=doctorId;
	}
	
	public String getDoctorName() {
		return doctorName;
	}
	
	public void setDoctorName(String doctorName) {
		this.doctorName=doctorName;
	}
	
	public String getDoctorGender() {
		return doctorGender;
	}
	
	public void setDoctorGender(String doctorGender) {
		this.doctorGender=doctorGender;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctors doctor = (Doctors) o;
        return doctorId == doctor.doctorId;
		
	}
	
	@Override
	public int hashCode() {
        return Objects.hash(doctorId);
    }

	@Override 
	public String toString() {
		return  "Doctor:" + 
	            "doctorID" + doctorId +
                ", name='" + doctorName + '\'' +
                ", gender=" + doctorGender +
                '}';
	}
}
