package Pojos;

public class Hospitals {
	
	public String hospitalName;
	public String hospitalLocation;
	public int hospitalId;
	
	
	public Hospitals() {
		this.hospitalName = "";
		this.hospitalLocation = "";
		this.hospitalId = 0;
		
	}
	
	public Hospitals(String hospitalName, String hospitalLocation, int hospitalId) {
		this.hospitalName = hospitalName;
		this.hospitalLocation = hospitalLocation;
		this.hospitalId = hospitalId;
	}
	

}
