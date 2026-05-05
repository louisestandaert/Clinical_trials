package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Pojos.Role;

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
				Role defaultRole = new Role();
				defaultRole.setRole("default");
				this.createRole(defaultRole);
			}

			System.out.println("EntityManagerFactory created successfully.");
		} catch (Exception e) {
			System.err.println("Error creating EntityManagerFactory: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private void createRole(Role defaultRole) {
		this.em.getTransaction().begin();
		this.em.persist(defaultRole);
		this.em.getTransaction().commit();
	}

	private List<Role> getAllRoles() {
		Query query = em.createQuery("SELECT r FROM Role r");
		return query.getResultList();
	}
}


// TODO : login , create user (dentro estas creando password) , update(change) password , delete user (opcional), un select user where ... nos inventamos. 


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
