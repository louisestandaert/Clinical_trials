package xml;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import Pojos.Description;
import Pojos.Doctors;
import Pojos.Hospitals;
import Pojos.Patients;
import Pojos.Trial;
import Pojos.HospitalTrial;

public class XmlManager {
	

	public void marshalPatient(Patients patient, String filePath) {
		
		try {
		JAXBContext context = JAXBContext.newInstance(Patients.class);

        Marshaller marshall = context.createMarshaller();
        marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                
        marshall.marshal(patient, new File(filePath));
        
        System.out.println("XML correctly created in: " + filePath);
        
		}catch(Exception e) {
			System.err.println("JAXB Error: " + e.getMessage());
            e.printStackTrace();
			
		}
		
	}
	
	
	public Patients unmarshalPatient(String filePath) {
		try {
			JAXBContext context= JAXBContext.newInstance(Patients.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			Patients patient = (Patients) unmarshall.unmarshal(new File(filePath));
			
			System.out.println("XML correctly created in: " + filePath);
			return patient;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling patient: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	

	public void marshalHospital(Hospitals hospital, String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Hospitals.class);
			Marshaller marshall = context.createMarshaller();
			marshall.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshall.marshal(hospital, new File(filePath));

			System.out.println("XML correctly created in:" + filePath);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling hospital: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Hospitals unmarshalHospital(String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Hospitals.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			Hospitals hospital = (Hospitals) unmarshall.unmarshal(new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
			return hospital;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling hospital: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	

	public void marshalTrial(Trial trial, String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Trial.class);
			Marshaller marshaler = context.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(trial, new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling trial: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Trial unmarshalTrial(String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Trial.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			Trial trial = (Trial) unmarshall.unmarshal(new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
			return trial;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling trial: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	

	public void marshalDoctor(Doctors doctor, String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Doctors.class);
			Marshaller marshaler = context.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(doctor, new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling doctor: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Doctors unmarshalDoctor(String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Doctors.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			Doctors doctor = (Doctors) unmarshall.unmarshal(new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
			return doctor;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling doctor: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void marshalDescription(Description description, String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(Description.class);
			Marshaller marshaler = context.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(description, new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling description: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Description unmarshalDescription(String filePath){
		try {
			JAXBContext context = JAXBContext.newInstance(Description.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			Description description = (Description) unmarshall.unmarshal(new File(filePath));

			System.out.println("Description's XML created: " + filePath);
			return description;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling description: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	

	public void marshalHospitalTrial(HospitalTrial hospitalTrial, String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(HospitalTrial.class);
			Marshaller marshaler = context.createMarshaller();
			marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaler.marshal(hospitalTrial, new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
		} catch (Exception e) {
			System.err.println("JAXB Error while marshalling hospital trial: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public HospitalTrial unmarshalHospitalTrial(String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(HospitalTrial.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			HospitalTrial hospitalTrial = (HospitalTrial) unmarshall.unmarshal(new File(filePath));

			System.out.println("XML correctly created in: " + filePath);
			return hospitalTrial;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling hospital trial: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	

	public void marshalDatabase(ClinicalTrialsXMLDataBase database, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ClinicalTrialsXMLDataBase.class);
            Marshaller marshaler = context.createMarshaller();
            marshaler.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaler.marshal(database, new File(filePath));

            System.out.println("Database XML correctly created in: " + filePath);
        } catch (Exception e) {
            System.err.println("JAXB Error while marshalling database: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	public ClinicalTrialsXMLDataBase unmarshalDatabase(String filePath) {
		try {
			JAXBContext context = JAXBContext.newInstance(ClinicalTrialsXMLDataBase.class);
			Unmarshaller unmarshall = context.createUnmarshaller();
			ClinicalTrialsXMLDataBase database = (ClinicalTrialsXMLDataBase) unmarshall.unmarshal(new File(filePath));

			System.out.println("Database XML correctly created in: " + filePath);
			return database;
		} catch (Exception e) {
			System.err.println("JAXB Error while unmarshalling database: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}	
	
	
	public void transformXMLToHTML(String xmlPath, String xslPath, String htmlPath) {
	    try {
	        TransformerFactory factory = TransformerFactory.newInstance();

	        Source xslt = new StreamSource(new File(xslPath));
	        Transformer transformer = factory.newTransformer(xslt);

	        Source xml = new StreamSource(new File(xmlPath));
	        transformer.transform(xml, new StreamResult(new File(htmlPath)));

	        System.out.println("HTML file correctly created in: " + htmlPath);

	    } catch (Exception e) {
	        System.err.println("Error transforming XML to HTML: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

}
