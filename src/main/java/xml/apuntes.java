package xml;

public class apuntes {
	
	/* 
	 
	 notes de la xml! 
	 
	 anotaciones: 
	 
	 @xmlaccessorType(XmlAccessType.FIELD) -> para que se mapeen los campos privados de la clase
	 @xmlrootElement (name = 'clinique') -> para indicar que esta clase es la raíz del documento XML
	 @xmlType (propOrder = ("id", "username" --> el orden del xml. -> para indicar el orden de los elementos en el XML
	 
	 
	 marshel -  traduccir java a xml  
	 unmarshel al reves. 
	 
	 
	 
	 crear una caroeta xml files
	 
	 de momento va estar vasia 
	 
	 un nuevo paquete ( ya lo tienes este ) 
	 
	un nueva carpeta que se llama climical trial .utils 
	
	y un manager tmb. 


	 no vamos a trabar y hacer cosas con la xml , solo vamos a crear la estructura de clases y anotaciones para poder hacer el marshel y unmarshel en un futuro.
	 
	 
	 tenemos que modificar los anataciones de los pojos que quiero hacer el marshel y unmarshel.
	 
	 @XmlAtribute -> para indicar que un campo se mapea como un atributo en el XML
	 @XmlElement -> para indicar que un campo se mapea como un elemento en el XML
	 @XmlTransient -> para indicar que un campo no se mapea en el XML (especial , un poco raro) 
	 
	 recuerda, que los metodos no estan guardados en el xml. 
	 
	 
	 muchas veces los elements son listas. (porque hay varios) 
	 
	  @xmlElementWrapper(name = "doctors") -> para envolver una lista de elementos en un elemento contenedor en el XML	
	  
	  elemento machineeesssss y despues dentro hayvarious machines, escrito individuales 
	 
	 
	 
	 despues para crear los ficheros y llenarlos (marshels) 
	 esto se hace en el manager! 
	 
	 
	 vas a tener un export data y un import data. 
	 
	 
	 los atributos : dtdpath, xmlpath , xslpath. 
	 
	 metodo 1: 
	 
	 la parte mas importante son esta tres lineas! - y el file esta creado antes. 
	 
	 JAXBContext jaxbContext = JAXBContext.newInstance(DatabaseWrapper.class/ puede ser Hospital.Class);   (el contexto)
	 marshaller marshaller = jaxbContext.createMarshaller();
	 marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.true); //no lo pongas el principio, no hace falta. 
	 
	 marshaller.marshal(databaseWrapperInstance, new File("path/to/output.xml")/file); mejor poner lo como file = new File("xml_files/output.xml")
	 
	 
	 clase DatabaseWrapper { //puntos extras !!
	 
	 es la clase 
	 
	 Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	 xmlFile = new File("xml_files/output.xml") -> para crear el fichero xml
	 
	 unmarshall.unmarshal(xmlFile) -> para leer el xml y convertirlo a objetos java.
	 
	 */

}
