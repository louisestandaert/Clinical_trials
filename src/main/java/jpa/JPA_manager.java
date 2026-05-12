package jpa;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import javax.persistence.NoResultException;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


import Pojos.Role;
import Pojos.User;

public class JPA_manager {

	private EntityManagerFactory emf;
	private EntityManager em;

	public JPA_manager() {

		try {
			this.emf = Persistence.createEntityManagerFactory("Clinical_trials_provider");
			this.em = emf.createEntityManager();
			this.em.getTransaction().begin(); // para empezar la transaccion
			this.em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate(); // para activar las claves foraneas en
																					// sqlite)
			this.em.getTransaction().commit(); // para confirmar la transaccion

			List<Role> roles = getAllRoles();
			
			if (roles == null || roles.isEmpty()) {
				this.createRole("default");
			}

			System.out.println("EntityManagerFactory created successfully.");
		} catch (Exception e) {
			System.err.println("Error creating EntityManagerFactory: " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	//Métodos nuevos de role 
	
	public void createRole(String roleName) {
		try {
			Role existingRole = findRoleByName(roleName);
			
			if (existingRole != null) {
				System.out.println("A role with this name already exists." + roleName);
				return;
			}
			
			em.getTransaction().begin();
			
			Role role = new Role(roleName);
			
			em.persist(role);
			
			em.getTransaction().commit();
			
			System.out.println("Role created successfully: " + roleName);
			
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				this.em.getTransaction().rollback();
			}
			System.err.println("Error checking for existing role: " + e.getMessage());
			
			 return;
		}
		
	}
	
	public Role findRoleByName(String roleName) {
	    try {
	        Query query = em.createQuery("SELECT r FROM Role r WHERE r.role = :roleName", Role.class);
	        
	        query.setParameter("roleName", roleName);
	        
	            return (Role) query.getSingleResult();
	            
	    } catch (NoResultException e) {
	        return null;
	        
	} catch (Exception e) {
	        System.err.println("Error finding role: " + e.getMessage());
	        return null;
	    }
	}
	
	public List<Role> getAllRoles() {
		Query query = em.createQuery("SELECT r FROM Role r");
		return query.getResultList();
	}
	
	public void login(String username, String password) {
	    try {
	        User user = findUserByUsername(username);

	        if (user == null) {
	            System.out.println("Login failed. User not found.");
	            return;
	        }

	        boolean passwordCorrect = PasswordUtil.verifyPassword(password, user.getPassword());

	        if (passwordCorrect) {
	            System.out.println("Login successful for user: " + user.getUsername());
	        } else {
	            System.out.println("Login failed. Incorrect password.");
	        }

	    } catch (Exception e) {
	        System.err.println("Login failed: " + e.getMessage());
	    }
	}
	
/*
 * createUser:
1. Busca si el username ya existe.
2. Busca el role con JPQL.
3. Crea un objeto User.
4. Hashea la password.
5. Asigna username, password hasheada y role.
6. Abre transacción.
7. Persiste el usuario.
8. Hace commit.
9. Si hay error, rollback.
    */

	/** CHAT ME DICE QUE QUIET ESTE, LO PONGO ASÍ POR SI ACASO 
	 * Por que dice que no es crear un user sino un role, y confunde, he creado un create role arriba, 
	 * POR SI QUEREIS MIRARLO Y CAMBIARLO O ALGO 
	 * 
	 public void createUser(String username, String password, String roleName) {
		try {
			User existingUser = findUserByUsername(username);
			if (existingUser != null) {
                System.out.println("A user with this username already exists.");
                return;
            }
			Query query = em.createQuery(
                    "SELECT r FROM Role r WHERE r.role = :roleName",
                    Role.class
            );

			query.setParameter("roleName", roleName);
            Role role = (Role) query.getSingleResult();
            User newUser = new User();
            newUser.setUsername(username);
            String hashedPassword = PasswordUtil.hashPassword(password);
            
            newUser.setPassword(hashedPassword);
            newUser.setUsername(username);
            
            this.em.getTransaction().begin();
            this.em.persist(newUser);
            this.em.getTransaction().commit();
            
            System.out.println("User created successfully: " + username);

        } catch (Exception e) {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }

            System.err.println("Error creating user: " + e.getMessage());
        }
    }
**/ 
	public void createUser(String userName, String password, String roleName) {
	    try {
	    	User existingUser = findUserByUsername(userName);
	    	
	    	if(existingUser != null) {
	    		System.out.println("A user with this username already exists." + userName);
	    		return;
	    	}
	    	
	        Role role = findRoleByName(roleName);
	        
	        if (role == null) {
	            System.out.println("Role not found: " + roleName);
	            return;
	        }
	        

	        User newUser = new User();
	        newUser.setUsername(userName);
	        
	        String hashedPassword = PasswordUtil.hashPassword(password);
	        newUser.setPassword(hashedPassword);
	        newUser.setRole(role);
	        
	        
	    	em.getTransaction().begin();
	    	
	        em.persist(newUser);
	        
	        em.getTransaction().commit();
	        
	        System.out.println("User created successfully: " + userName);

	        
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                this.em.getTransaction().rollback();
	            }

	            System.err.println("Error creating user: " + e.getMessage());
	    }
	}
	/*
	 * login:
1. Busca el usuario por username.
2. Si no existe, falla.
3. Si existe, compara la password introducida con el hash guardado.
4. Si coincide, login correcto.
5. Si no coincide, login incorrecto.

	 */
            
	public List<User> findAllUsers() {
	    try {
	        Query query = em.createQuery("SELECT u FROM User u", User.class);
	        return query.getResultList();

	    } catch (Exception e) {
	        System.err.println("Error finding all users: " + e.getMessage());
	        return null;
	    }
	}
	
	public void deleteUser(String username) {
		User user= findUserByUsername(username);
		
		if(user!=null) {
            this.em.getTransaction().begin();
            this.em.remove(user);
            this.em.getTransaction().commit();
            System.out.println("User deleted successfully.");
            
		} else {
			System.out.println("No user found with username: " + username);
		}
		
		
	}
	
	public User findUserByUsername(String username) {
		try {
			Query query=em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			List <User> users = query.getResultList();
			
			if (users.isEmpty()) {
				return null;
			} else {
				return users.get(0);
			}
		}catch(Exception e) {
			System.err.println("Error finding user: " + e.getMessage());
			return null;
		}
	}
	
	public void updatePassword(String username, String newPassword) {
	    User user = findUserByUsername(username);

	    if (user == null) {
	        System.out.println("No user found with username: " + username);
	        return;
	    }

	    try {
	        String hashedPassword = PasswordUtil.hashPassword(newPassword);

	        this.em.getTransaction().begin();
	        user.setPassword(hashedPassword);
	        this.em.getTransaction().commit();

	        System.out.println("Password updated successfully for user: " + username);

	    } catch (Exception e) {
	        if (this.em.getTransaction().isActive()) {
	            this.em.getTransaction().rollback();
	        }

	        System.err.println("Error updating password: " + e.getMessage());
	    }
	}
	
	
	public List<User> findUserByRole(String roleName) {
		try {
			Query query = em.createQuery("SELECT u FROM User u  WHERE u.role.role = :roleName", 
					User.class);
			
			query.setParameter("roleName", roleName);
			
			return query.getResultList();
			
		} catch (Exception e) {
			System.err.println("Error finding users by role: " + e.getMessage());
			return new ArrayList<User>();
		}
	}
	
	
	//Con la contraseña encriptada pero creo que hay que añadir un .jar o una libreria o algo y no se si estaría bien así
	/*public void updateEncryptedPassword(String username, String newEncryptedPassword) {
		User user = findUserByUsername(username);

        if (user == null) {
            System.out.println("No user found with username: " + username);
            return;
        }
        String encryptedPassword = BCrypt.hashpw(newEncryptedPassword, BCrypt.gensalt());
        em.getTransaction().begin();
        user.setPassword(encryptedPassword);
        em.getTransaction().commit();
        System.out.println("The password has been updated successfully for user: " + username);
	}
	
	*/

}

// ahora hacemos el manager

// jpamanager
// primero el constructor - segundo los metodos

// el constructor
// el nombre que has puesto en el persistence unit. (xml)
// un if importante - if my list de roles esta vacía - entonces le añades un
// role por defecto.
// tenemos que crear los roles y asignarlos

// ahora los metodos
//

// empezar con el create role:
// parametro - un role role
// em.gettransaction.begin() - esto es para empezar la transaccion
// em.persist(role) - esto es para persistir el role en la base de datos
// em.gettransaction.commit() - esto es para confirmar la transaccion
// y punchhhh!

// para los metodos de usar un query - em.createquery("SELECT r FROM Role r") -
// esto es para seleccionar todos los roles de la base de datos

// modificar - son transactiones
// si es solo para ver cosas - query q - q.getresultlist() - esto es para
// obtener una lista de resultados - haces una query directamente y el segundo
// parametro , a donde , user.class!
// q.setparameter("name", name) - esto es para establecer un parametro en la
// consulta
// q.getsingleResult() - esto es para obtener un solo resultado

// ahora tines que crear el user - user user = (user) q.getsingleResult() - esto
// es para obtener un solo resultado y castearlo a user.

// metodo login _ el mas importante
// tiene que devolverte el user
// los dos parametros - username y password
// query q = em.createNativeQuery("SELECT u FROM User u WHERE u.username =
// :username AND u.password = :password", User.class);
// q.setParameter("username", username);
// verificar los username existe
// aqui empieza el rolle de la seguridad. - pero hacerlo despues.
//

// create tu password
// ahora la forma de lo primero es comprobar que hay un match con el username y
// el password.

// update password
//
