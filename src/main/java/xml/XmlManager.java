package xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Pojos.Description;
import Pojos.Doctors;
import Pojos.Hospitals;
import Pojos.Patients;
import Pojos.Trial;

public class XmlManager {
	
	//PATIENTS
	public void marshalPatient(Patients patient, String rutaArchivo) {
		
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
	
	
	//TRIAL
	public void marshalTrial(Trial trial, String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Trial.class);
			Marshaller marshaler = contexto.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(trial, new File(rutaArchivo));

			System.out.println("XML de trial creado con éxito en: " + rutaArchivo);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling trial: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Trial unmarshalTrial(String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Trial.class);
			Unmarshaller unmarshall = contexto.createUnmarshaller();
			Trial trial = (Trial) unmarshall.unmarshal(new File(rutaArchivo));

			System.out.println("XML de trial leído con éxito desde: " + rutaArchivo);
			return trial;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling trial: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	//DOCTOR
	public void marshalDoctor(Doctors doctor, String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Doctors.class);
			Marshaller marshaler = contexto.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(doctor, new File(rutaArchivo));

			System.out.println("XML de doctor creado con éxito en: " + rutaArchivo);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling doctor: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Doctors unmarshalDoctor(String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Doctors.class);
			Unmarshaller unmarshall = contexto.createUnmarshaller();
			Doctors doctor = (Doctors) unmarshall.unmarshal(new File(rutaArchivo));

			System.out.println("XML de doctor leído con éxito desde: " + rutaArchivo);
			return doctor;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling doctor: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	// DESCRIPTION
	public void marshalDescription(Description description, String rutaArchivo) {
		try {
			JAXBContext contexto = JAXBContext.newInstance(Description.class);
			Marshaller marshaler = contexto.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(description, new File(rutaArchivo));

			System.out.println("XML de descripción creado con éxito en: " + rutaArchivo);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling description: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Description unmarshalDescription(String rutaArchivo){
		try {
			JAXBContext contexto = JAXBContext.newInstance(Description.class);
			Unmarshaller unmarshall = contexto.createUnmarshaller();
			Description description = (Description) unmarshall.unmarshal(new File(rutaArchivo));

			System.out.println("XML de descripción leído con éxito desde: " + rutaArchivo);
			return description;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling description: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	

}
