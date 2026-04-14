package Pojos;
import java.util.Objects;

public class Trials {

    
    private int trialId;      
    private String trialName; 
    private int startingDate; // ahora esta como int pero creo que sera mejor hacer como LocalDate

    
    public Trials() {
    }

   
    public Trials(int trialId, String trialName, int startingDate) {
        this.trialId = trialId;
        this.trialName = trialName;
        this.startingDate = startingDate;
    }

    // Getters y Setters
    public int getTrialId() {
        return trialId;
    }

    public void setTrialId(int trialId) {
        this.trialId = trialId;
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
    }

    public int getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(int startingDate) {
        this.startingDate = startingDate;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trials trial = (Trials) o;
        return trialId == trial.trialId;
    }

    // hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(trialId);
    }

    @Override
    public String toString() {
        return "Trial{" +
                "trialId=" + trialId +
                ", trialName='" + trialName + '\'' +
                ", startingDate=" + startingDate +
                '}';
    }
}

