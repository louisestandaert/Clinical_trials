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
