package Pojos;

import java.util.Objects;

public class Description {
	private int description_id; 
	private String gender; 
	private String cause;
	
	public Description(){
	}
	
	public Description(int description_id, String gender, String cause) {
		this.description_id=description_id;
		this.gender=gender;
		this.cause=cause;
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
                ", cause=" + cause +
                '}';
	}
	
}
