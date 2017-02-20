package com.cooksys.assessment;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class computerParts {

List<String> name;
	
@XmlElement(name="item")
	public List<String> getList() {
		if (name == null) {
			name = new ArrayList<String>();
		}
		return name;
	}
	
	public void setList(List<String> name) {
		this.name = name;
	}
}
