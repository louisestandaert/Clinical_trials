package jpa;

public class user {

	@entity
	@table("users")
	
	
	// añadir usario 
	// eliminar usuario
	// update usuario
	
	//necesitas es secure password tmb. 
	
	//libaries: 
	// eclipse link + una mas
	
	//jpamanager - crear el link 
	
	// carpeta metainf - fichero xml
	// hay que cambiar el persistence unit. en el fichero. -- con un nombre tuyo 
	//y poner solo el user y el role. 
	// cambia el path de los role y user. 
	//cambia la ruta del db  
	
	// crear los nuevos pojos de user y role.
	//todo igual - contructuores 
	//anadir - @enitity - @Table("name = users") y punch 
	//implements serializable
	
	// add @id - @generatedvalue(strategy = GenerationType.IDENTITY) - para el id - @tablegenerator 
	//@column(unique=true) - para el username
	
	
	//@manytoone - para el role - @joincolumn(name = "role_id") - esto es para el role. 
	// fetch = fetchtype.eager - esto es para que se cargue el role al cargar el user.
	
	
	
	//@onetomany - para el user - mappedby = "user" - esto es para que se cargue el user al cargar el role.
	// fetch = fetchtype.lazy - esto es para que no se cargue el user al cargar el role. 
	
	
	//con esto hemos configurado la relacion entre user y role.
	
	
	//ahora hacemos el manager 
	
	//jpamanager 
	//primero el constructor - segundo los metodos 
	
	//el constructor
	//el nombre que has puesto en el persistence unit. (xml)
	//un if importante - if my list de roles esta vacía - entonces le añades un role por defecto.
	//tenemos que crear los roles y asignarlos 
	
	//ahora los metodos
	// 
	
	//empezar con el create role: 
	//parametro - un role role 
	//em.gettransaction.begin() - esto es para empezar la transaccion
	//em.persist(role) - esto es para persistir el role en la base de datos
	//em.gettransaction.commit() - esto es para confirmar la transaccion
	// y punchhhh! 
	
	
	//para los metodos de usar un query - em.createquery("SELECT r FROM Role r") - esto es para seleccionar todos los roles de la base de datos
	
	// modificar - son transactiones 
	// si es solo para ver cosas - query q - q.getresultlist() - esto es para obtener una lista de resultados - haces una query directamente y el segundo parametro , a donde , user.class! 
	// q.setparameter("name", name) - esto es para establecer un parametro en la consulta
	// q.getsingleResult() - esto es para obtener un solo resultado
	
	// ahora tines que crear el user - user user = (user) q.getsingleResult() - esto es para obtener un solo resultado y castearlo a user.
	
	
	//metodo login _ el mas importante 
	// tiene que devolverte el user  
	// los dos parametros - username y password
	// query q = em.createNativeQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
	//q.setParameter("username", username);
	// verificar los username existe 
	// aqui empieza el rolle de la seguridad. - pero hacerlo despues. 
	//  
	
	// create tu password 
	//ahora la forma de lo primero es comprobar que hay un match con el username y el password.
	
	
	
	//update password 
	//
	
	
	
	
}
