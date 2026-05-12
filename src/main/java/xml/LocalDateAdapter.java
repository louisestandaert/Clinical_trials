package xml;
import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	@Override
	public LocalDate unmarshal(String value) throws Exception {
		if (value == null || value.isEmpty()) {
			return null;
		}
		return LocalDate.parse(value);
	}

	@Override
	public String marshal(LocalDate value) throws Exception {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

}
