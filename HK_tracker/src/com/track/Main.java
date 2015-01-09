package com.track;

/**
 * 
 * @author alpa
 *
 */
public class Main {
	public static void main(String[] args) {
		// Set the maximum turning radius of the turret to 560
		Tracker tracker = new Tracker(480);
		tracker.patrol();
	}
}