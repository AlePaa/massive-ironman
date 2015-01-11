package com.track;

/**
 * 
 * @author alpa
 *
 * The main functionality class, uses the sensors and the turret to
 * search for and track movement.
 */
public class Tracker {
	
	private String left = "left";
	private String right = "right";
	
	private Scanner scanner;
	private int searchDistance;
	private Turret turret;
	
	/**
	 * 
	 * @param max maximum turning radius for the turret.
	 */
	public Tracker(int max) {
		scanner = new Scanner();
		turret = new Turret(max);
		searchDistance = -1;
	}
	
	/**
	 * Instruct the turret to turn left or right,
	 * then call the main chase.
	 * 
	 * @param direction String value depicting the direction to which to turn.
	 */
	private void chase(String direction) {
		if (direction.equals(left)) {
			turret.turnClockwise();
		}
		else { 
			turret.turnCounterclockwise();
		}
		chaseMain();
	}
	
	/**
	 * Attempts to follow the previously seen moving object
	 * by turning left or right depending on which sensor saw it last.
	 */
	private void chaseMain() {
		
		while(turret.canTurn()) {
			boolean seenByLeft = isInSights(left);
			boolean seenByRight = isInSights(right);
			if (seenByLeft && seenByRight) {
				turret.stop();
				break;
				}
			else if (seenByLeft) {
				turret.turnCounterclockwise();
			}
			else if (seenByRight) {
				turret.turnClockwise();
			}
		}
	}
	
	/**
	 * See if the target can be seen by a sensor.
	 * 
	 * @param usedSensor String value representing a specific sensor
	 * 
	 * @return boolean true if object detected near the distance of search.
	 */
	private boolean isInSights(String usedSensor) {
		int distance = 0;
			distance = scanner.getDistance(usedSensor);
		if (distance > 0 && distance != 255 &&
				distance < searchDistance+30 && distance > searchDistance-30) {
			searchDistance = distance;
			System.out.println("Target sighted at: " + distance);
			return true;
		}
		return false;
	}

	/**
	 * Scans an area slowly.
	 * If movement is detected, enter tracking mode.
	 */
	public void patrol() {
		while(true) {
			System.out.println("Scanning..");
			turret.routineTurn();
			// If movement is detected, enter the tracking mode
			if (scanner.scan(left)) {
				track(left);
			}
			else if(scanner.scan(right)) {
				track(right);
			}
		}
	}

	/**
	 * Attempts to lock on to the target.
	 */
	private void track(String detectedSensor) {
		// Use count to automatically drop the lock if the target
		// stays immobile for too long
		int count = 0;
		// 
		turret.setSpeed(200);

		while(count < 20 && turret.canTurn() && turret.canRotate()){

		// Set the initial distance from which the target is sought
		searchDistance = scanner.getDistance(detectedSensor);
		// If the target hasn't moved, wait for movement
		if(isInSights(left) && isInSights(right)){	
			searchDistance = scanner.getDistance(left);
			wait(10);
			count++;
		} 
		
		else {
		// Target lost, reset the count
			count = 0;
		// Figure out which way it went,
		// then head into that direction until the target is found or
		// turning is not allowed
			if (isInSights(right) && !isInSights(left)) {
				chase(right);
			}
			else if (isInSights(left) && !isInSights(right)) { 
				chase(left);
			}
		}
		}
	}
	
	/**
	 * Wait for the specified time.
	 * 
	 * @param x The rest time in milliseconds
	 */
	private void wait(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
