package com.eap.plh24;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BetOrganization {

	//We use a design pattern to create a unique instance of BetOrganization using
	//Singleton Lazy Initialization with on-demand holder
	private BetOrganization(){}
	private static class BetOrganizationHolder {
		static BetOrganization betOrganization = new BetOrganization();
	}
	public static BetOrganization getInstance() {
		return BetOrganizationHolder.betOrganization;
	}

	//List with the available players
	private final List<Customer> cList = new ArrayList<>();
	//List with the available bets for basketball and football games.
	private final List<Bet> betList = new ArrayList<>();
	public boolean checkCustomerBetInList(CustomerBet customerBet){
		for (Bet bet : betList){
			if (bet.getGame().equals(customerBet.getBetName()))
				return true;
		}
		return false;
	}
	public List<Bet> getBetList() {
		return Collections.unmodifiableList(betList);
	}
	public List<Customer> getCustomerList() {
		return Collections.unmodifiableList(cList);
	}
	protected void addCustomer(Customer customer) {
		cList.add(customer);
	}
	protected void addBet(Bet bet) {
		betList.add(bet);
	}

	//Η μέθοδος υπολογίζει τα κέρδη του παίχτη που δίδεται ως παράμετρός της.
	//Πιο συγκεκριμένα, η παράμετρος αφορά στην λίστα στοιχημάτων του εκάστοτε παίχτη
	private double calculateGainsPerCustomer(IGiveBetList customer) {
		//Για κάθε ένα στοίχημα που έχει κάνει ο παίχτης
			//Ψάχνουμε να το αντιστοιχήσουμε με τη λίστα των στοιχημάτων του BetOrganization
					//Στη συνέχεια, εφόσον το βρούμε, κοιτάζουμε αν έχει κερδίσει η επιλογή του παίχτη
					//και αν ναι, προσθέτουμε το ποσό στα κέρδη (επιστρεφόμενη τιμή της μεθόδου)

		List<CustomerBet> betList = customer.getCustomerBetList();
		GameEmulator ge = GameEmulator.getInstance();

		double gains = 0.0;

		for(CustomerBet customerBet: betList) {
			if(checkCustomerBetInList(customerBet)){
				if(customerBet.getChoice().equals(ge.getEmulatedGamesResults().get(customerBet.getBetName()))){
					for(Bet bet: getBetList()){
						if(bet.getGame().equals(customerBet.getBetName())){
							double odd = bet.getOdd();
							gains += customerBet.getStake()*odd;
						}
					}
				}
			}
		}
		return gains;
	}

	//Η μέθοδος υπολογίζει τα κέρδη του παίχτη που δίδεται ως παράμετρός της.
	//Πιο συγκεκριμένα, η παράμετρος αφορά στην λίστα στοιχημάτων του εκάστοτε παίχτη
	private double calculateGainsPerCustomerV2(IGiveBetList customer) {
		// For each bet made by the player,
		// we search to match it with the list of bets in BetOrganization.
		// Then, if found, we check if the player's choice has won,
		// and if so, add the amount to the gains (return value of the method).

		List<CustomerBet> betList2 = customer.getCustomerBetList();
		GameEmulator ge = GameEmulator.getInstance();

		/* In this part of the method, we use stream API. The first filter, filters all the CustomerBets that exist in
		 * the list of Bets in BetOrganization. The second filter, filters all the winning bets that the customer made.
		 * The mapToDouble returns a StreamDouble while applying the function in the parenthesis to all objects of
		 * the original stream. The function inside the parenthesis of the mapToDouble returns the gains per customerBet.
		 * For each bet it filters every bet that matches the customerBet in name. Then a new mapToDouble is applied that
		 * gets the odd for every bet. If a value is found the odd is equal to that value, else it is equal to 0.0 .
		 * Finally, the gain is returned and the total gains are getting summed with .sum() resulting in the double value
		 * of the total gains which is returned.
		 */
        return betList2.stream()
						.filter(this::checkCustomerBetInList)
						.filter(customerBet -> customerBet.getChoice().equals(ge.getEmulatedGamesResults().get(customerBet.getBetName())))
						.mapToDouble(customerBet -> {
							double odd = getBetList().stream()
										.filter(b -> b.getGame().equals(customerBet.getBetName()))
										.mapToDouble(Bet::getOdd) //Maps the bet to its odd
										.findFirst()
										.orElse(0.0);
							return odd*customerBet.getStake();
							}//Maps each CustomerBet to its gain which is the odd multiplied by the stake of the bet.
						).sum();
	}


	/* This method constructs the Results in a specific format while returning that same text so it can be used
	 * in printCustomersResultsToTextFile. We use StringBuilder to build the String and in append(System.lineSeparator())
	 * we take into consideration the fact that the generated txt may be viewed in different platforms. That's why we use
	 * System.lineSeparator() instead of \n. The official documentation of BufferedWriter which will be used in
	 * printCustomersResultsToTextFile explains the reason it is preferred instead of \n in greater details.
	 */
	private String returnCustomersResults(){

		List<Customer> customerList = getCustomerList();
		StringBuilder results = new StringBuilder();
		System.out.println();
		results.append("------------------Results-------------------").append(System.lineSeparator());

		for (Customer customer : customerList){
            results.append("####################################").append(System.lineSeparator())
                    .append("Customer name: ").append(customer.getName()).append(System.lineSeparator())
                    .append("Customer money paid: ").append(customer.getMoneyPlayed()).append(System.lineSeparator())
					.append("Customer gains: ").append(calculateGainsPerCustomerV2(customer))
					.append(System.lineSeparator());
		}
		results.append("--------------End-of-Results----------------").append(System.lineSeparator());

        return results.toString();
	}

	//Prints the results
	public void showCustomerResults(){
		System.out.println(returnCustomersResults());
	}

	//Return the gains of a specific customer
	public double getCustomerGains(IGiveBetList customer){
        return calculateGainsPerCustomerV2(customer);
	}


	//Εγγραφή των αποτελεσμάτων των παιχτών στο αρχείο κειμένου "bet-results.txt"
	//Το αρχείο αυτό θα αντικαθίσταται από νέο αρχείο, κάθε φορά που εκτελείται το πρόγραμμα (δεν κρατάμε τα προηγούμενα δεδομένα)
	//Το format του αρχείου να είναι ίδιο με την εκτύπωση των αποτελεσμάτων (showCustomersResults)
	public void printCustomersResultsToTextFile(){
		String fileName = "bet-results.txt";
		String results = returnCustomersResults();

		//try-with-arguments to auto close the BufferedWriter and FileWriter after the process is completed.
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
			writer.write(results);
		} catch (IOException e) {
			System.out.println("An error occurred while writing to the txt file: "+e.getMessage());;
		}

	}
}
