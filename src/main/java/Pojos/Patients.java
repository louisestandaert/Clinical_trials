package Pojos;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlRootElement(name = "Patients")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patients", propOrder = { "patientsId", "patientName", "results", "trialId", "hospitalId",
		"descriptionId" })

public class Patients {

	@XmlAttribute(name = "patientsId")
	private int patientsId;

	@XmlAttribute(name = "patientName")
	private String patientName;

	@XmlAttribute(name = "results")
	private String results;

	@XmlElement(name = "trialId")
	private int trialId;

	@XmlElement(name = "hospitalId")
	private int hospitalId;

	@XmlElement(name = "descriptionId")
	private int descriptionId;

	public Patients() {
	}

	public Patients(int patientsId, String patientName, String results, int trialId, int hospitalId,
			int descriptionId) {
		this.patientsId = patientsId;
		this.patientName = patientName;
		this.results = results;
		this.trialId = trialId;
		this.hospitalId = hospitalId;
		this.descriptionId = descriptionId;
	}

	// Getters y Setters
	public int getPatientsId() {
		return patientsId;
	}

	public void setPatientsId(int patientsId) {
		this.patientsId = patientsId;
	}
	
	public int getTrialId() {
		return trialId;
	}
	
	public void setTrialId(int trialId) {
		this.trialId = trialId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Patients patient = (Patients) o;
		return patientsId == patient.patientsId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(patientsId);
	}

	@Override
	public String toString() {
		return "Patients{" + "patientsId=" + patientsId + ", patientName='" + patientName + '\'' + ", results='"
				+ results + '\'' + ", trialId=" + trialId + ", hospitalId=" + hospitalId + ", descriptionId="
				+ descriptionId + '}' + "\n";
	}
}
