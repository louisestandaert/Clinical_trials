package Pojos;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "TrialHospital")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrialHospital", propOrder = { "trialId", "hospitalId" })
public class HospitalTrial {
	
	@XmlElement(name = "trialId")
	private int trialId;
	
	@XmlElement(name = "hospitalId")
	private int hospitalId;
	
	public HospitalTrial() {
	}
	
	public HospitalTrial(int trialId, int hospitalId) {
		this.trialId = trialId;
		this.hospitalId = hospitalId;
	}
	
	public int getTrialId() {
		return trialId;
	}
	
	public void setTrialId(int trialId) {
		this.trialId = trialId;
	}
	
	public int getHospitalId() {
		return hospitalId;
	}
	
	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	@Override
	public String toString() {
		return "HospitalTrial [trialId=" + trialId + ", hospitalId=" + hospitalId + "]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		HospitalTrial that = (HospitalTrial) o;
		return trialId == that.trialId && hospitalId == that.hospitalId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(trialId, hospitalId);
	}
}
