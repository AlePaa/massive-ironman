package com.track;

import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
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
		
		// Set both sensors in Ping mode
		s1.setMode(1);
		s2.setMode(1);
	}
	
	
	 /** 
	 * @param int val The int2 value to be valuated.
	 * @return The absolute value of val.
	 */
	private int abs(int val) {
		if(val < 0 ) {
			val *= -1;
		}		
		return val;
	}

	/**
	 * 
	 * @param pingRes an array of int containing results from pinging
	 * @return A single int value calculated from the parameter array
	 */
	private int calculatePingResult(int[] pingRes) {
		int result = 0;
		for (int i = 0; i < pingRes.length; i++) {
			// Ignore the designated error value 255
			if(pingRes[i] != 255){
			result += pingRes[i];
			}
		}
		return result;
	}


	/**
	 * 
	 * @return the sonar value of the ultrasonic sensor
	 */
	public int getDistance(String usedSensor) {
		if (usedSensor.equals("left")){
		return getDistance(s1);
		}
		else {
			return getDistance(s2);
		}
	}
	
	/**
	 * 
	 * @param sensor Either s1 or s2
	 * @return One value calculated from the single ping results.
	 */
	private int getDistance(UltrasonicSensor sensor) {
		sensor.ping();
		Sound.pause(20);
		int[] pingRes = new int[8];
		sensor.getDistances(pingRes);
		return calculatePingResult(pingRes);
	}
	/**
	 * @return The absolute value of the difference of two sensor readings.
	 */
	private int getDopplerReadings(UltrasonicSensor sensor) {
		// Take four readings with a twenty millisecond wait in between
		int rangeA = getDistance(sensor);
		int rangeB = getDistance(sensor);
		return abs((rangeA - rangeB));
	}
	
	/**
	 *  Read data from the current position of the ultrasonic sensors.
	 * @return boolean true if movement is detected, else returns false.
	 */
	public boolean scan(String usedSensor) {
		// Use the left sensor as default
		UltrasonicSensor sensor = s1;
		if (usedSensor.equals("right")){
		sensor = s2;
		}
	
		for(int i = 0; i< 20; i++) {
			int rangeShift = getDopplerReadings(sensor);
			// Use the absolute value to see if there has been movement between the readings
			if (rangeShift > 5 && rangeShift < 255) {			// Try to compensate for possible distance reading errors
				return true;
				}
		}
		return false;
	}

}
