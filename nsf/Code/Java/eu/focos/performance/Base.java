package eu.focos.performance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Base implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> results = new ArrayList<String>();

	public List<String> getResults() {
		return results;
	}
	
	public void addResult(String res) {
		this.results.add(res);
	}
	
	public String getLabel() {
		return this.getClass().getSimpleName();
	}
	
}
