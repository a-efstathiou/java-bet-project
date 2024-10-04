package com.eap.plh24;


import org.w3c.dom.ls.LSOutput;

import java.util.List;
import java.util.stream.Collectors;

// CLASS FOR TESTING PURPOSES. Used to test CustomerBets with Illegal Arguments and in general
// situations that we expect something specific to happen. That way we can check if every method works as intended
public class Test {
	public static void main(String[] args) {


		BetOrganization bo = BetOrganization.getInstance();
		GameEmulator ge = GameEmulator.getInstance();
		//Κατά την προσομοίωση ΔΕΝ ζητούνται δεδομένα εισόδου στο πρόγραμμα
		//Όλα τα δεδομένα είναι hardcoded μέσα στον κώδικά σας

		//1.Δημιουργία διαθέσιμων στοιχημάτων για το ποδόσφαιρο
		FootballBet fbet1 = new FootballBet("FC Barcelona vs. Real Madrid", 2.10);
		FootballBet fbet2 = new FootballBet("Manchester United vs. Liverpool", 2.50);
		FootballBet fbet3 = new FootballBet("Juventus vs. AC Milan", 1.80);
		FootballBet fbet4 = new FootballBet("Bayern Munich vs. Borussia Dortmund", 1.65);
		FootballBet fbet5 = new FootballBet("Arsenal vs. Chelsea", 2.20);
		bo.addBet(fbet1);
		bo.addBet(fbet2);
		bo.addBet(fbet3);
		bo.addBet(fbet4);
		bo.addBet(fbet5);

		//2.Δημιουργία διαθέσιμων στοιχημάτων για το μπάσκετ
		BasketballBet bbet1 = new BasketballBet("Los Angeles Lakers vs. Boston Celtics", 1.85);
		BasketballBet bbet2 = new BasketballBet("Golden State Warriors vs. Toronto Raptors", 1.70);
		BasketballBet bbet3 = new BasketballBet("Chicago Bulls vs. Miami Heat", 2.20);
		BasketballBet bbet4 = new BasketballBet("Houston Rockets vs. Dallas Mavericks", 2.10);
		BasketballBet bbet5 = new BasketballBet("Philadelphia 76ers vs. Milwaukee Bucks", 1.90);
		bo.addBet(bbet1);
		bo.addBet(bbet2);
		bo.addBet(bbet3);
		bo.addBet(bbet4);
		bo.addBet(bbet5);

		List<Bet> betlist = bo.getBetList();


		//bo.addBet(new BasketballBet("Golden State Warriors vs. Toronto Raptors",100000)); //throws exception due to duplicate key

		//Create a new customer
		Customer c1 = new Customer("Sophia Anderson");

		//make some bets with illegal values
		CustomerBet c1bet2 = CustomerBet.createCustomerBet("Los Angeles Lakers vs. Boston Celtics","Basketball",100,"X"); //Not OK, choice illegal
		CustomerBet c1bet3 = CustomerBet.createCustomerBet("Los Angeles Lakers vs. Boston Celtics","Basketball",100,"4"); //Not OK, choice illegal
		CustomerBet c1bet5 = CustomerBet.createCustomerBet("Los Angeles Lakers vs. Boston Celtics","Basball",100,"1"); //Not OK, betType incorrect

		//make some OK bets
		CustomerBet c1bet1 = CustomerBet.createCustomerBet("Los Angeles Lakers vs. Boston Celtics","Basketball",100,"1"); //Should be ok
		CustomerBet c1bet4 = CustomerBet.createCustomerBet("FC Barcelona vs. Real Madrid","Football",1000,"1"); //Should be ok
		CustomerBet c1bet6 = CustomerBet.createCustomerBet("Houston Rockets vs. Dallas Mavericks","Basketball",50,"1"); //Should be ok


		c1.addCustomerBet(c1bet1); //OK
		c1.addCustomerBet(c1bet2); //expected: exception due to null Object
		c1.addCustomerBet(c1bet3); //expected: exception due to null Object
		c1.addCustomerBet(c1bet4); //expected: error due to illegal stake for customer c1
		c1.addCustomerBet(c1bet5); //expected: exception due to null Object
		c1.addCustomerBet(c1bet6); //OK



		bo.addCustomer(c1);

		//6.Προσομοίωση αγώνων
		ge.doGameEmulation();
		ge.printEmulatedResults();

		//7.Προβολή συνολικών αποτελεσμάτων παιχτών

		bo.showCustomerResults();


	}
}