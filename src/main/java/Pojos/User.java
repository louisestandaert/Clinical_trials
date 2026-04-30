package Pojos;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "User")

public class User {
	
	//DECLARANDO LOS ATRIBUTOS

	@Id // para indicar que es la primary key es el id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // para que el id se genere automaticamente y no se repita
	private int user_id;
	

	@Column(unique = true) // para que username sea unico en la base de datos
	private String username;
	

	private String password;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // para indicar que un user puede tener muchos roles y que el
															// mapeo se hace desde la clase Role
	private List<Role> roles;

	
	//CONSTRUCTORES
	
	public User() {
	}

	public User(int id, String username, String password, List<Role> roles) {
		this.user_id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;

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

}
