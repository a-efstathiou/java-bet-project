package com.eap.plh24;


import java.util.List;

public class BasketballBet extends Bet{

    private static final List<String> choices = List.of("1","2");

    public BasketballBet(String game, double odd) {
        super(game, odd);
    }
    //Η κλάση περιέχει τις διαθέσιμες εκβάσεις ενός αγώνα μπάσκετ. 
	//1:Νικήτρια η πρώτη ομάδα, 2:Νικήτρια η δεύτερη ομάδα

    //Η μέθοδος επιστρέφει τη λίστα των διαθέσιμων επιλογών για έναν αγώνα μπάσκετ
	public static List<String> getChoices() {
        return choices;
    }
}
