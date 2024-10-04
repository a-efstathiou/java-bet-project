package com.eap.plh24;
//Η κλάση είναι η "γενική" κλάση των στοιχημάτων.
//Η κλάση είναι δηλωμένη ως abstract διότι δεν μπορούμε να έχουμε αντικείμενα αυτής.
//Μπορούμε να έχουμε μόνο αντικείμενα των υποκλάσεών της. 
//Είτε στοιχήματα ποδοσφαίρου, είτε στοιχήματα μπάσκετ.
public abstract class Bet {
	//Το όνομα του αγώνα. Δεχόμαστε ότι το όνομα είναι μοναδικό.
	private final String game;
	//Η απόδοση του στοιχήματος. Υποθέτουμε ίδια απόδοση, ανεξάρτητα από το ποια ομάδα θα κερδίσει
	private final double odd;
	public Bet(String game, double odd) {
		this.game = game;
		this.odd = odd;
	}
	public String getGame() {
		return game;
	}
	public double getOdd() {
		return odd;
	}
}



