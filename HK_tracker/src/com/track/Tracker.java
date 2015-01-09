package com.track;

/**
 * 
 * @author alpa
 *
 * The main functionality class, uses the sensors and the turret to
 * search for and track movement.
 */
public class Tracker {
	
	private Scanner scanner;
	private Turret turret;
	private int searchDistance;
	
	/**
	 * 
	 * @param max maximum turning radius for the turret
	 */
	public Tracker(int max) {
		scanner = new Scanner();
		turret = new Turret(max);
		searchDistance = -1;
	}
	
	/**
	 * Scans an area slowly.
	 * If movement is detected, enter tracking mode.
	 */
	public void patrol() {
		while(true) {
			turret.routineTurn();
			// If movement is detected, enter the tracking mode
			if (scanner.scan()) {
				track();
			}
		}
	}
	
	/**
	 * Attempts to lock on to the target.
	 */
	private void track() {
		System.out.println("Movement at: " + scanner.getS1Distance());
		// Use count to automatically drop the lock if the target
		// stays immobile for too long
		int count = 0;
		// 
		turret.setSpeed(180);
		searchDistance = scanner.getS1Distance();
		while(count < 50 && turret.canTurn()){
		// Set the initial distance from which the target is sought
		// If the target hasn't moved, wait for movement
		if(isInSights()){	
			searchDistance = scanner.getS1Distance();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
			count++;
		}
		else {
		// Target lost, reset the count
			count = 0;
		// Figure out which way it went,
		// then head into that direction until the target is found or
		// turning is not allowed
			if (isSeenByS2() && !isInSights()) {
				chaseR();
			}
			else { 
				chaseL();
			}
		}
		}
	}
	
	/**
	 * Instruct the turret to turn right,
	 * then call the main chase.
	 */
	private void chaseR() {
		turret.turnClockwise();
		chaseMain();
	}
	
	/**
	 * Instruct the turret to turn left,
	 * then call the main chase.
	 */
	private void chaseL() {
		turret.turnCounterclockwise();
		chaseMain();
	}
	
	private void chaseMain() {
		while(turret.canTurn()) {
			if (isInSights()) {
				break;
			} else if (isSeenByS2()) {
				turret.stop();
				chaseR();
			}
		}
		turret.stop();
	}
	/**
	 * 
	 * @param search The current approximation of the moving object's
	 * location.
	 * @return boolean true if object detected near the distance of search.
	 */
	private boolean isInSights() {
		int distance = scanner.getS1Distance();
		if (distance < searchDistance+40 && distance > searchDistance-40) {
			return true;
		}
		return false;
	}
	
	private boolean isSeenByS2() {
		int distance = scanner.getS2Distance();
		if (distance < searchDistance+40 && distance > searchDistance-40) {
			return true;
		}
		return false;
	}
}
