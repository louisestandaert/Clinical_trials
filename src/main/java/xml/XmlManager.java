package xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;


import Pojos.Patients;

public class XmlManager {
	
	public void marshal(Patients patient, String rutaArchivo) {
		
		try {
		JAXBContext contexto = JAXBContext.newInstance(Patients.class);

        Marshaller marshall = contexto.createMarshaller();
        marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                
        marshall.marshal(patient, new File(rutaArchivo));
        
        System.out.println("XML creado con éxito en: " + rutaArchivo);
        
		}catch(Exception e) {
			System.err.println("JAXB Error: " + e.getMessage());
            e.printStackTrace();
			
		}
		
	}
	

}
