package com.eap.plh24;

import java.util.List;

public class BetMain {

	public static void main(String[] args) {
		BetOrganization bo = BetOrganization.getInstance();
		GameEmulator ge = GameEmulator.getInstance();
		//Κατά την προσομοίωση ΔΕΝ ζητούνται δεδομένα εισόδου στο πρόγραμμα
		//Όλα τα δεδομένα είναι hardcoded μέσα στον κώδικά σας

		//1.Δημιουργία διαθέσιμων στοιχημάτων για το ποδόσφαιρο

		createFootballBets();

		//2.Δημιουργία διαθέσιμων στοιχημάτων για το μπάσκετ

		createBasketballBets();

		//3.Δημιουργία παιχτών και προσθήκη στο σύστημα

		createCustomers();

		//4.Δημιουργία στοιχημάτων των παιχτών
		//Each customer places 3 bets.
		createCustomerBets();

		//5.Προσομοίωση αγώνων

		ge.doGameEmulation();
		ge.printEmulatedResults();

		//6.Προβολή συνολικών αποτελεσμάτων παιχτών

		bo.showCustomerResults();

		//7.Και εγγραφή αυτών σε αρχείο κειμένου

		bo.printCustomersResultsToTextFile();

		//8. Αποθήκευση των αποτελεσμάτων των παιχτών σε βάση δεδομένων
		StoreDataInMySQL.storeData();

	}

	//Creates 5 Football bets and adds them to the list of bets of BetOrganization
	private static void createFootballBets() {
		BetOrganization bo = BetOrganization.getInstance();

		bo.addBet(new FootballBet("FC Barcelona vs. Real Madrid", 2.10));
		bo.addBet(new FootballBet("Manchester United vs. Liverpool", 2.50));
		bo.addBet(new FootballBet("Juventus vs. AC Milan", 1.15));
		bo.addBet(new FootballBet("Bayern Munich vs. Borussia Dortmund", 1.05));
		bo.addBet(new FootballBet("Arsenal vs. Chelsea", 2.20));

	}

	//Creates 5 Basketball bets and adds them to the list of bets of BetOrganization
	private static void createBasketballBets() {
		BetOrganization bo = BetOrganization.getInstance();

		bo.addBet(new BasketballBet("Los Angeles Lakers vs. Boston Celtics", 1.35));
		bo.addBet(new BasketballBet("Golden State Warriors vs. Toronto Raptors", 1.70));
		bo.addBet(new BasketballBet("Chicago Bulls vs. Miami Heat", 2.20));
		bo.addBet(new BasketballBet("Houston Rockets vs. Dallas Mavericks", 2.10));
		bo.addBet(new BasketballBet("Philadelphia 76ers vs. Milwaukee Bucks", 1.50));

	}

	private static void createCustomers() {
		BetOrganization bo = BetOrganization.getInstance();

		bo.addCustomer(new Customer("Sophia Anderson"));
		bo.addCustomer(new Customer("Noah Johnson"));
		bo.addCustomer(new GoldCustomer("Isabella Smith"));
		bo.addCustomer(new GoldCustomer("William Brown"));
		bo.addCustomer(new PlatinumCustomer("Charlotte Davis"));
		bo.addCustomer(new PlatinumCustomer("James Wilson"));
	}

	private static void createCustomerBets() {
		BetOrganization bo = BetOrganization.getInstance();
		List<Customer> customers = bo.getCustomerList();

		//Bets of customer1 (Normal Customer)
		Customer c1 = customers.get(0);
		c1.addCustomerBet(CustomerBet.createCustomerBet("Arsenal vs. Chelsea","Football",50,"1"));//Should be ok
		c1.addCustomerBet(CustomerBet.createCustomerBet("Houston Rockets vs. Dallas Mavericks","Basketball",99,"2"));//Should be ok
		c1.addCustomerBet(CustomerBet.createCustomerBet("Los Angeles Lakers vs. Boston Celtics","Basketball",28,"2"));//Should be ok

		//Bets of customer2 (Normal Customer)
		Customer c2 = customers.get(1);
		c2.addCustomerBet(CustomerBet.createCustomerBet("Bayern Munich vs. Borussia Dortmund","Football",12,"1"));//Should be ok
		c2.addCustomerBet(CustomerBet.createCustomerBet("Chicago Bulls vs. Miami Heat","Basketball",66,"1"));//Should be ok
		c2.addCustomerBet(CustomerBet.createCustomerBet("Golden State Warriors vs. Toronto Raptors","Basketball",78,"1"));//Should be ok

		//Bets of customer3 (Gold Customer)
		Customer c3 = customers.get(2);
		c3.addCustomerBet(CustomerBet.createCustomerBet("Juventus vs. AC Milan","Football",331,"1"));//Should be ok
		c3.addCustomerBet(CustomerBet.createCustomerBet("Manchester United vs. Liverpool","Football",938,"X"));//Should be ok
		c3.addCustomerBet(CustomerBet.createCustomerBet("FC Barcelona vs. Real Madrid","Football",426,"X"));//Should be ok

		//Bets of customer4 (Gold Customer)
		Customer c4 = customers.get(3);
		c4.addCustomerBet(CustomerBet.createCustomerBet("Philadelphia 76ers vs. Milwaukee Bucks","Basketball",999,"1"));//Should be ok
		c4.addCustomerBet(CustomerBet.createCustomerBet("Golden State Warriors vs. Toronto Raptors","Basketball",707,"2"));//Should be ok
		c4.addCustomerBet(CustomerBet.createCustomerBet("Manchester United vs. Liverpool","Football",185,"2"));//Should be ok

		//Bets of customer5 (Platinum Customer)
		Customer c5 = customers.get(4);
		c5.addCustomerBet(CustomerBet.createCustomerBet("Houston Rockets vs. Dallas Mavericks","Basketball",1400,"2"));//Should be ok
		c5.addCustomerBet(CustomerBet.createCustomerBet("Chicago Bulls vs. Miami Heat","Basketball",1863,"1"));//Should be ok
		c5.addCustomerBet(CustomerBet.createCustomerBet("FC Barcelona vs. Real Madrid","Football",1604,"X"));//Should be ok

		//Bets of customer6 (Platinum Customer)
		Customer c6 = customers.get(5);
		c6.addCustomerBet(CustomerBet.createCustomerBet("Juventus vs. AC Milan","Football",438,"1"));//Should be ok
		c6.addCustomerBet(CustomerBet.createCustomerBet("Los Angeles Lakers vs. Boston Celtics","Basketball",1612,"1"));//Should be ok
		c6.addCustomerBet(CustomerBet.createCustomerBet("Philadelphia 76ers vs. Milwaukee Bucks","Basketball",1586,"1"));//Should be ok



	}

}
