package Pojos;

import java.util.Objects;

public class Doctors {

	private int doctorId;
	private String doctorName;
	private String doctorGender;
	private DoctorSpecialty doctorSpecialty;
	
	public Doctors() {
	}
	
	public Doctors(int doctorId, String doctorName, String doctorGender, DoctorSpecialty doctorSpeciatly) {
		this.doctorId=doctorId;
		this.doctorName=doctorName;
		this.doctorGender=doctorGender;
		this.doctorSpecialty=doctorSpecialty;
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
	
	public DoctorSpecialty getDoctorSpecialty() {
		        return doctorSpecialty;
	}
	
	public void setDoctorSpecialty(DoctorSpecialty doctorSpecialty) {
        this.doctorSpecialty=doctorSpecialty;
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
                ", name='" + doctorName + 
                ", gender=" + doctorGender +
                ", specialty=" + doctorSpecialty +
                '}';
	}
}
