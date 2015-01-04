package com.track;

import lejos.nxt.Motor;

/* 
 *	The control class for the moving part,
 *	assumes the motor is plugged into port C 
 */
public class Turret {
	private int tacho;
	private int direction;
	
	public Turret() {
		tacho = 0;
		direction = 1;
	}
	public void turn() {
		/* If the angle is greater than 180,
		* Change the direction of rotation to prevent the tangling of connectors 
		*/
		if (tacho > 360 || tacho < -360) {
			direction *= -1;
		}
		Motor.C.rotate(60*direction);
		tacho = Motor.C.getTachoCount();
	}
	
	public int getTacho() { return tacho; }
}
