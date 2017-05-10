package eu.focos.performance;

import java.util.concurrent.TimeUnit;

public class Timer {
	
	private long start;
	private long stop;
	
	public void start() {
		this.start = System.nanoTime();
	}
	
	public void stop() {
		this.stop = System.nanoTime();
	}

	public void reset() {
		this.start = 0;
		this.stop = 0;
	}
	
	public long getElapsedTime() {
		return (stop - start);
	}

	public long getElapsedTimeMs() {
		return TimeUnit.NANOSECONDS.toMillis(stop-start);
	}
	
	
}
