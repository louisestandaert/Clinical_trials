package xml;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import Pojos.Doctors;
import java.util.List;
import Pojos.Description;
import Pojos.Hospitals;
import Pojos.Patients;
import Pojos.Trial;
import Pojos.HospitalTrial;

@XmlRootElement(name = "ClinicalTrials")
@XmlAccessorType(XmlAccessType.FIELD)

public class ClinicalTrialsXMLDataBase {
	
	@XmlElementWrapper(name = "Doctors")
	@XmlElement(name = "Doctor")
	private List<Doctors> doctors;
	
	@XmlElementWrapper(name = "Hospitals")
	@XmlElement(name = "Hospital")
	private List<Hospitals> hospitals;
	
	@XmlElementWrapper(name = "Patients")
	@XmlElement(name = "Patient")
	private List<Patients> patients;
	
	@XmlElementWrapper(name = "Descriptions")
	@XmlElement(name = "Description")
	private List<Description> descriptions;
	
	@XmlElementWrapper(name = "Trials")
	@XmlElement(name = "Trial")
	private List<Trial> trials;
	
	@XmlElementWrapper(name = "HospitalTrials")
	@XmlElement(name = "HospitalTrial")
	private List<HospitalTrial> hospitalTrials;
	
	public ClinicalTrialsXMLDataBase() {
	}
	
	public ClinicalTrialsXMLDataBase(List<Doctors> doctors, List<Hospitals> hospitals, List<Patients> patients,
			List<Description> descriptions, List<Trial> trials, List<HospitalTrial> hospitalTrials) {
		this.doctors = doctors;
		this.hospitals = hospitals;
		this.patients = patients;
		this.descriptions = descriptions;
		this.trials = trials;
		this.hospitalTrials = hospitalTrials;
	}
	
	public List<Doctors> getDoctors() {
		return doctors;
	}
	
	public void setDoctors(List<Doctors> doctors) {
		this.doctors = doctors;
	}
	
	public List<Hospitals> getHospitals() {
		return hospitals;
	}
	
	public void setHospitals(List<Hospitals> hospitals) {
		this.hospitals = hospitals;
	}
	
	public List<Patients> getPatients() {
		return patients;
	}
	
	public void setPatients(List<Patients> patients) {
		this.patients = patients;
	}
	
	public List<Description> getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(List<Description> descriptions) {
		this.descriptions = descriptions;
	}
	
	public List<Trial> getTrials() {
		return trials;
	}
	
	public void setTrials(List<Trial> trials) {
		this.trials = trials;
	}

	public List<HospitalTrial> getHospitalTrials() {
		return hospitalTrials;
	}
	
	public void setHospitalTrials(List<HospitalTrial> hospitalTrials) {
		this.hospitalTrials = hospitalTrials;
	}
}
