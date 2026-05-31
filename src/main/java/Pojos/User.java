package Pojos;

import java.util.List;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "User")

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;


	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "user_id")
	private int user_id;
	

	@Column(name="username", unique = true, nullable = false) 
	private String username;
	
	@Column(name="password", nullable = false)
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id") 										
	private Role role;

	
	
	public User() {
	}

	public User(int id, String username, String password, Role role) {
		this.user_id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	// Getters y Setters
	public int getId() {
		return user_id;
	}

	public void setId(int id) {
		this.user_id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public String toString() {
		return "User [id=" + user_id + ", username=" + username + ", password=" + password + ", role=" + role + "] \n";
	}

}
