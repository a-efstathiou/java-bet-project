package com.eap.plh24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


//Υπάρχουν 3 τύποι πελατών. Οι κανονικοί παίχτες (τρέχουσα κλάση), καθώς και οι παίχτες Gold και Platinum.
//Οι κανονικοί παίχτες μπορούν να ποντάρουν έως 100 ανά στοίχημα.
//Οι παίχτες Gold αντιστοιχούν στην κλάση GoldCustomer, υποκλάση της Customer, με μέγιστο επιτρεπτό όριο πονταρίσματος 1000.
//Οι παίχτες Platinum αντιστοιχούν στην κλάση PlatinumCustomer, υποκλάση της Customer, με μέγιστο επιτρεπτό όριο πονταρίσματος 2000.
//Η συγκεκριμένη κλάση θα πρέπει να υλοποιήσει τη διεπαφή IGiveBetList, η οποία επιστρέφει τη λίστα των στοιχημάτων του κάθε παίχτη.
public class Customer implements IGiveBetList{
	private final String name;
	private final List<CustomerBet> customerBetList = new ArrayList<>();

	public Customer(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	//Για να εισάγουμε ένα νέο στοίχημα στη λίστα στοιχημάτων του πελάτη,
	//πρέπει να ελέγξουμε πρώτα αν το στοίχημα έχει ποσό εντός του επιτρεπτού ορίου
	//του κάθε πελάτη (μέθοδος getMaxStake), καθώς και αν το εν λόγω στοίχημα υπάρχει
	//στη λίστα στοιχημάτων του BetOrganization
	public void addCustomerBet(CustomerBet customerBet) throws NullPointerException {

		try {
			if (customerBet == null) {
				//prevents the creation of CustomerBet objects with illegal values
				throw new NullPointerException("The provided CustomerBet object is null. Unable to add to list.");
			} else if (customerBet.getStake() <= getMaxStake()) {
				BetOrganization betOrganization = BetOrganization.getInstance();
				if (betOrganization.checkCustomerBetInList(customerBet)) {
					customerBetList.add(customerBet);
					System.out.println("CustomerBet OK with name: " + customerBet.getBetName());//ONLY FOR TESTING PURPOSES. REMOVE AFTER TESTING
				} else {
					System.out.println("Error: Couldn't find the bet in the list with name: " + customerBet.getBetName());
				}
			} else {
				System.out.println("Error in CustomerBet with name :" + customerBet.getBetName() + ". Customer " + getName() + " can't bet above " + getMaxStake());
			}
		}
		catch (NullPointerException e){
			System.out.println("Exception occurred with Stack Trace: "+ Arrays.toString(e.getStackTrace()));
			System.out.println("and message: "+e.getMessage());
		}

	}


	public int getMoneyPlayed(){
		int sum = 0;
		for (CustomerBet customerBet : customerBetList){
			sum+=customerBet.getStake();
		}
		return sum;
	}

	public int getMaxStake(){
		return 100;
	}

	//Return an unmodifiableList of customerBetList
	@Override
	public List<CustomerBet> getCustomerBetList() {
		return Collections.unmodifiableList(customerBetList);
	}
}
