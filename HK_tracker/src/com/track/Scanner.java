package com.track;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/*
 * Reads input from the ultrasonic sensors 
 */
public class Scanner {

	UltrasonicSensor s1;
	
	public Scanner() {
		s1 = new UltrasonicSensor(SensorPort.S1);
	}
	
	/* Read data from the current position of the ultrasonic sensors */
	public boolean scan() {
		// Take two readings within a specified timeframe
		int range1 = s1.getDistance();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int range2 = s1.getDistance();
		// Compare the readings
		int rangeShift = range1 - range2;
		System.out.println(rangeShift);
		return false;
	}
}
