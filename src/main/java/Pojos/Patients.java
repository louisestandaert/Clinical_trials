package Pojos;

import java.util.Objects;

public class Patients {
	    private int patientsId;     
	    private String patientName;   
	    private String results;      
	    private int trialId;         
	    private int hospitalId;      
	    private int descriptionId;   

	    public Patients() {}

	    public Patients(int patientsId, String patientName, String results, int trialId, int hospitalId, int descriptionId) {
	        this.patientsId = patientsId;
	        this.patientName = patientName;
	        this.results = results;
	        this.trialId = trialId;
	        this.hospitalId = hospitalId;
	        this.descriptionId = descriptionId;
	    }

	    // Getters y Setters
	    public int getPatientsId() { return patientsId; }
	    public void setPatientsId(int patientsId) { this.patientsId = patientsId; }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        Patients patient = (Patients) o;
	        return patientsId == patient.patientsId;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(patientsId);
	    }
	}

