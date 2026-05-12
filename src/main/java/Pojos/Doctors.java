package Pojos;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;


import java.util.Objects;

@XmlRootElement(name = "Doctors")
@XmlAccessorType(XmlAccessType.FIELD)

public class Doctors {

	@XmlAttribute(name = "doctorId")
	private int doctorId;
	
	@XmlAttribute(name = "doctorName")
	private String doctorName;
	
    @XmlAttribute(name = "doctorGender")
	private String doctorGender;
    
    @XmlAttribute(name = "doctorSpecialty")
	private DoctorSpecialty doctorSpecialty;
    
    @XmlElement(name = "hospitalId")
	private int hospitalId;
    
    @XmlElement(name = "trialId")
    private int trialId;
	
	public Doctors() {
	}
	
	public Doctors(int doctorId, String doctorName, String doctorGender, DoctorSpecialty doctorSpeciatly, int hospitalId) {
		this.doctorId=doctorId;
		this.doctorName=doctorName;
		this.doctorGender=doctorGender;
		this.doctorSpecialty=doctorSpecialty;
		this.hospitalId=hospitalId;
	}
	
	public Doctors(int doctorId, String doctorName, String doctorGender, DoctorSpecialty doctorSpeciatly) {
		this.doctorId=doctorId;
		this.doctorName=doctorName;
		this.doctorGender=doctorGender;
		this.doctorSpecialty=doctorSpecialty;
	}
	
	public Doctors(int doctorId, String doctorName, String doctorGender, DoctorSpecialty doctorSpeciatly, int hospitalId, int trialId) {
		this.doctorId=doctorId;
        this.doctorName=doctorName;
        this.doctorGender=doctorGender;
        this.doctorSpecialty=doctorSpecialty;
        this.hospitalId=hospitalId;
        this.trialId=trialId;
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
