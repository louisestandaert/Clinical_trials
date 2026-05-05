package Pojos;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name= "Role")


public class Role {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int role_id; 
	
	
	
	@Column(name= "role", unique = true, nullable = false)
	private String role;
	

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<User> users;
	
	
	//constructores
	
	public Role() {
	
	}
	
	public Role(int id, String role, List<User> users) {
		this.role_id = id;
		this.role = role;
		this.users = users;
	}

	public void setRole(String string) {
		this.role = string;
	}
	
	
}
