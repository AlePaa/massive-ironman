package com.track;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * 
 * @author alpa
 * 
 * Handles motion detection and distance data using two
 * ultrasonic sensors
 */
public class Scanner {

	private UltrasonicSensor s1;
	private UltrasonicSensor s2;
	
	public Scanner() {
		s1 = new UltrasonicSensor(SensorPort.S1);
		s2 = new UltrasonicSensor(SensorPort.S2);
	}
	
	/**
	 * 
	 * @return the sonar value of the ultrasonic sensor 1
	 */
	public int getS1Distance() {
		return (int) s1.getRange();
	}
	
	/**
	 * 
	 * @return the sonar value of the ultrasonic sensor 2
	 */
	public int getS2Distance() {
		return (int) s2.getRange();
	}
	
	/**
	 *  Read data from the current position of the ultrasonic sensors.
	 * @return boolean true if movement is detected, else returns false.
	 */
	public boolean scan() {
		for(int i = 0; i< 20; i++){
			int rangeShift = getMoveReadings();
			// Use the absolute value to see if there has been movement between the readings
			if (rangeShift > 3 && rangeShift < 255) {			// Try to compensate for possible distance reading errors
				return true;
				}
		}
		return false;
	}
	
	/**
	 * @return The absolute value of the difference of two sensor readings.
	 */
	private int getMoveReadings() {
		// Take two readings with a ten millisecond wait in between
		int rangeAS1 = getS1Distance();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int rangeBS1 = getS1Distance();
		return abs(rangeAS1 - rangeBS1);
	}
	
	/** 
	 * @param int val The int value to be valuated.
	 * @return The absolute value of val.
	 */
	private int abs(int val) {
		if(val < 0 ) {
			val *= -1;
		}		
		return val;
	}

}
