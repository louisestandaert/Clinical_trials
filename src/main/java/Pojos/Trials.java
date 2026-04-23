package Pojos;
import java.util.Objects;
import java.time.LocalDate;

public class Trials {

    
    private int trialId;      
    private String trialName; 
    private LocalDate startingDate;
    private int durationDays;
    private double budget;
    private int targetPatients;

    
    public Trials() {
    }

   
    public Trials(int trialId, String trialName, LocalDate startingDate) {
        this.trialId = trialId;
        this.trialName = trialName;
        this.startingDate = startingDate;
    }
    
    public Trials(int trialId, String trialName, LocalDate startingDate, int durationDays, double budget, int targetPatients) {
        this.trialId = trialId;
        this.trialName = trialName;
        this.startingDate = startingDate;
        this.durationDays = durationDays;
        this.budget = budget;
        this.targetPatients = targetPatients;
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

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }
    
    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getTargetPatients() {
        return targetPatients;
    }

    public void setTargetPatients(int targetPatients) {
        this.targetPatients = targetPatients;
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
        return "Trials{" +
                "trialId=" + trialId +
                ", trialName='" + trialName + '\'' +
                ", startingDate=" + startingDate +
                ", durationDays=" + durationDays +
                ", budget=" + budget +
                ", targetPatients=" + targetPatients +
                '}';
    }
}

