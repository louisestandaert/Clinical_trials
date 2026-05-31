package Pojos;

import java.util.List;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name= "Role")
public class Role {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "role_id")
	private int role_id; 
	
	
	
	@Column(name= "role", unique = true, nullable = false)
	private String role;
	

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private List<User> users;
	
	
	public Role() {
	
	}
	
	public Role(String role) {
		this.role = role;
	}
	
	public Role(int id, String role, List<User> users) {
		this.role_id = id;
		this.role = role;
		this.users = users;
	}
	
	public int getRole_id() {
		return role_id;
	}
	
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	
	public String getRole() {
		return role;
	}

	public void setRole(String string) {
		this.role = string;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}	
	
	public String toString() {
		return "Role [id=" + role_id + ", role=" + role + "]";
	}
	
}

