package com.track;

import lejos.nxt.Motor;

/** 
 * 	
 * @author alpa
 * 
 * The control class for the moving part.
 * Assumes the motor is plugged into port C.
 */
public class Turret {
	
	private int angle;
	private int direction;
	
	private int MAX_ANGLE;
	
	/**
	 *
	 * The constructor, sets the rotation direction as clockwise and the rotation speed as slow scan speed.
	 *
	 * @param int max the maximum turning radius for the turret
	 */
	public Turret(int max) {
		MAX_ANGLE = max;
		angle = 0;
		direction = 1;
		setRoutineSpeed();
	}
	
	/**
	 * The standard turret rotation routine, will turn and change direction when necessary
	 */
	public void routineTurn() {
		// Return to routine speed
		setRoutineSpeed();
		// If the angle is greater than specified, change the direction of rotation to prevent the tangling of connectors.
		if (canTurn()) {
			rotate(120*direction);
		}
	}
	
	private void rotate(int x) {
			System.out.println("Rotating..");
			Motor.C.rotate(x);
	}
	
	/**
	 * Turn turn the turret right
	 */
	public void turnClockwise() {
		direction = -1;
		Motor.C.forward();
	}
	
	/**
	 * Turn the turret left
	 */
	public void turnCounterclockwise(){
		direction = 1;
		Motor.C.backward();
	}
	/**
	 * Stop rotating.
	 */
	public void stop() {
		Motor.C.stop();
		recover();
	}

	/**
	 * Set the rotation speed for the turret.
	 */
	public void setSpeed(int speed) {
		Motor.C.setSpeed(speed);		
	}
	
	/**
	 * Set the rotation speed as default.
	 */
	public void setRoutineSpeed() {
		Motor.C.setSpeed(50);
	}
	
	/**
	 * 
	 * @return boolean false if the turning radius is too large,
	 * else return true
	 */
	public boolean canTurn() {
		angle = Motor.C.getTachoCount();
		if (angle > MAX_ANGLE || angle < -MAX_ANGLE) {
			if((angle < -MAX_ANGLE && direction == -1)
					|| (angle > MAX_ANGLE && direction == 1)) {
			direction *= -1;
			}
			recover();
			return false;
		}
		return true;
	}
	
	/**
	 * Return from 
	 */
	public void recover() {
		System.out.println("Recovering..");
		if (angle < -MAX_ANGLE) {
			rotate(-(angle + MAX_ANGLE));
		}
		else if (angle > MAX_ANGLE) {
			rotate((angle - MAX_ANGLE) * direction);
		}
		// Do nothing
		else {
			
		}
		}

}
