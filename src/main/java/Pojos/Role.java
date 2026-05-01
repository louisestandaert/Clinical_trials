package Pojos;

import javax.persistence.*;

@Entity
@Table(name= "Role")


public class Role {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int role_id; 
	
	
	
	@Column(name= "role", unique = true, nullable = false)
	private String role;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	
	//constructores
	
	public Role() {
	
	}
	
	public Role(int role_id, String role, User user){
		this.role_id= role_id; 
		this.role=role;
		this.user=user;
	}
	
	
}
