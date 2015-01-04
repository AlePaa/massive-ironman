package com.track;

public class Main {

	public static void main(String[] args) {
		Tracker tracker = new Tracker();
		patrol();
	}
	
	public static void patrol() {
		Turret turret = new Turret();
		Scanner scanner = new Scanner();
		while(true) {
			turret.turn();
			if (scanner.scan()) {}
		}
	}
}