package xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Pojos.Hospitals;
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
	
	
	public Patients unmarshalPatient(String rutaArchivo) {
		try {
			JAXBContext contexto= JAXBContext.newInstance(Patients.class);
			Unmarshaller unmarshall = contexto.createUnmarshaller();
			Patients patient = (Patients) unmarshall.unmarshal(new File(rutaArchivo));
			
			System.out.println("XML leído con éxito desde: " + rutaArchivo);
			return patient;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling patient: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	//HOSPITALS
	
	public void marshalHospital(Hospitals hospital, String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Hospitals.class);
			Marshaller marshall = contexto.createMarshaller();
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(hospital, new File(rutaArchivo));

			System.out.println("XML de hospital creado con éxito en: " + rutaArchivo);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling hospital: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Hospitals unmarshalHospital(String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Hospitals.class);
			Unmarshaller unmarshall = contexto.createUnmarshaller();
			Hospitals hospital = (Hospitals) unmarshall.unmarshal(new File(rutaArchivo));

			System.out.println("XML de hospital leído con éxito desde: " + rutaArchivo);
			return hospital;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling hospital: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	

}
