package eu.focos.performance;

import java.util.concurrent.TimeUnit;

import org.openntf.domino.Document;
import org.openntf.domino.utils.XSPUtil;

public class Timer {
	
	private long start;
	private long stop;
	private String type;
	
	public Timer(String type) {
		this.type = type;
	}
	
	public void start() {
		this.start = System.nanoTime();
	}
	
	public void stopAndSave() {
		this.stop = System.nanoTime();
		this.save();
		}
	
	private void save() {
		Document doc = XSPUtil.getCurrentDatabase().createDocument();
		doc.put("form", "fResult");
		doc.put("type", type);
		doc.put("elapsedTime", getElapsedTimeMs());
		doc.save();
	}


	public void reset() {
		this.start = 0;
		this.stop = 0;
	}
	
	public long getElapsedTimeNanoseconds() {
		return (stop - start);
	}

	public long getElapsedTimeMs() {
		return TimeUnit.NANOSECONDS.toMillis(stop-start);
	}
	
	
}
