package com.eap.plh24;

import java.util.*;
import java.util.stream.Collectors;

//Η συγκεκριμένη κλάση χρησιμοποιείται κατά την εκτέλεση του προγράμματος, για την τυχαία παραγωγή των αποτελεσμάτων των αγώνων.
public class GameEmulator {
	//Αντικείμενο που μπορεί να χρησιμοποιηθεί για την παραγωγή τυχαίων αριθμών
    Random r = new Random();
	//Η συγκεκριμένη δομή HashMap θα μας βοηθήσει να αντιστοιχήσουμε κάθε αγώνα με ένα αποτέλεσμα.
	//Συγκεκριμένα, για κάθε μοναδικό όνομα αγώνα (πρώτη παράμετρος String),
	//θα αντιστοιχούμε ένα μονάδικο αποτέλεσμα (1-Χ-2) ή (1-2), ανάλογα με το είδος του αγώνα (ποδόσφαιρο ή μπάσκετ)
    private final Map<String,String> emulatedGamesResults = new HashMap<>();
    //Διαθέσιμες επιλογές για ποδόσφαιρο
    private final String[] footballChoices = FootballBet.getChoices().toArray(new String[0]);
    //Διαθέσιμες επιλογές για μπάσκετ
    private final String[] basketballChoices = BasketballBet.getChoices().toArray(new String[0]);

    //We use a design pattern to create a unique instance of GameEmulator using
    //Singleton Lazy Initialization with on-demand holder
    private GameEmulator(){}
    private static class GameEmulatorHolder {
        static GameEmulator gameEmulator = new GameEmulator();
    }
    public static GameEmulator getInstance() {
        return GameEmulatorHolder.gameEmulator;
    }

    //Method to print the results of the emulation. Used for testing purposes.
    public void printEmulatedResults() {
        System.out.println("\nResults of Emulation: \n");
        Map<String, String> emulatedResults = getEmulatedGamesResults();

        for (Map.Entry<String, String> entry : emulatedResults.entrySet()) {
            String gameName = entry.getKey();
            String result = entry.getValue();
            System.out.println("Game Name: " + gameName + ", Result: " + result);
        }
    }


    // This method generates the data of HashMap "emulatedGamesResults"
    // for example that in the football match "FC Barcelona vs. Real Madrid" the result is "1".
    // We are sure that Bet is either instanceof FootballBet or BasketballBet, so we use the ternary operator inside the
    // function to set the value section of the map.
    public void doGameEmulation(){
        BetOrganization betOrganization = BetOrganization.getInstance();
        List<Bet> betList = betOrganization.getBetList();

        Map<String, String> results = betList.stream()
                .collect(Collectors.toMap(
                        Bet::getGame,
                        bet -> bet instanceof FootballBet? footballChoices[r.nextInt(3)] : basketballChoices[r.nextInt(2)]
                ));
        emulatedGamesResults.putAll(results);
    }

    //Returns an immutable copy of the emulatedGamesResults
    public Map<String, String> getEmulatedGamesResults() {
        return Map.copyOf(emulatedGamesResults);
    }

}
