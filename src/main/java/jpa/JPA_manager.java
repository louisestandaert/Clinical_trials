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
	
	public String getRoleByUser(User user) {
	
		return user.getRole().getRole();
    		
	}
	
	
	public boolean checkLogin(String username, String password) {
	    try {
	        User user = findUserByUsername(username);

	        if (user == null) {
	            return false;
	        }

	        boolean passwordCorrect = PasswordUtil.verifyPassword(password, user.getPassword());

	        return passwordCorrect;

	    } catch (Exception e) {
	        System.err.println("Login failed: " + e.getMessage());
	        return false;
	    }
	}
	
}
	
	
	