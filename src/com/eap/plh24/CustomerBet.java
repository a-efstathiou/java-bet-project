package com.eap.plh24;

import java.util.Arrays;
import java.util.List;

public class CustomerBet {

    //Το μοναδικό όνομα του αγώνα
    //Κατά τη δημιουργία αντικειμένων της εν λόγω κλάσης, δεν ελέγχουμε αν το όνομα αγώνα που έδωσε το παίχτης είναι σωστό.
    //Αυτό θα ελεγχθεί αργότερα από την κλάση BetOrganization
    private final String betName;
    //Το ποσό του πονταρίσματος σε ευρώ (χωρίς δεκαδικά)
    private final int stake;
    //Η επιλογή πονταρίσματος του παίχτη. Όπως αναφέρθηκε και παραπάνω πρέπει να ελεγχθεί, ανάλογα με τον τύπο του αγώνα.
    //Οι διαθέσιμες εκβάσεις ενός αγώνα μπάσκετ είναι, 1:Νικήτρια η πρώτη ομάδα, 2:Νικήτρια η δεύτερη ομάδα
    //Οι διαθέσιμες εκβάσεις ενός αγώνα ποδοσφαίρου είναι, 1:Νικήτρια η πρώτη ομάδα, Χ:Ισοπαλία, 2:Νικήτρια η δεύτερη ομάδα
    private final String choice;
    //Η παράμετρος "betType" είναι ο τύπος του αγώνα. Μπορεί να πάρει μόνο μια εκ των 2 τιμών: "Football" ή "Basketball"
    //Η δοθείσα τιμή του String betType που δίνεται κατά την προσομοίωση θα ελέγχεται στον constructor της κλάσης CustomerBet
    //και παράλληλα θα ελέγχεται αν η επιλογή, "choice", αφορά στις διαθέσιμες επιλογές του εκάστοτε τύπου αγώνα.


    private CustomerBet(CustomerBetBuilder builder) {
        this.betName = builder.betName;
        this.stake = builder.stake;
        this.choice = builder.choice;
    }



    public String getBetName() {
        return betName;
    }

    public int getStake() {
        return stake;
    }
    public String getChoice() {
        return choice;
    }

    // This method creates and returns a CustomerBet object using the builder design pattern. In case the user inputs
    // an illegal parameter which results in an exception as can be seen in the builder methods, the method instead
    // returns null. This is done because it is not desirable to create a CustomerBet with wrong parameters.
    public static CustomerBet createCustomerBet(String betName, String betType, int stake, String choice) {
        try {
            CustomerBet customerBet = new CustomerBet.CustomerBetBuilder()
                    .betName(betName)
                    .betType(betType)
                    .stake(stake)
                    .choice(choice)
                    .build();
            return customerBet;
        }
        catch (IllegalArgumentException | IllegalStateException e) {

            System.out.println("Exception occurred with Stack Trace: "+ Arrays.toString(e.getStackTrace()));
            System.out.println("and message: "+e.getMessage());
        }
        return null;
    }


    private static class CustomerBetBuilder {
        //Design Pattern -> Builder Pattern

        //Λίστα που περιέχει τις δύο διαθέσιμες επιλογές αγώνα, ποδοσφαίρου και μπάσκετ αντίστοιχα
        private final List<String> availableBetTypes = List.of("Football", "Basketball");

        private String betName;
        private int stake;
        private String choice;
        private String betType;

        private CustomerBetBuilder betName(String betName) {
            this.betName = betName;
            return this;
        }

        private CustomerBetBuilder betType(String betType) throws IllegalArgumentException{
            //We check if betType contains a valid value from the availableBetTypes
            if(availableBetTypes.contains(betType)) {
                    this.betType = betType;
            }
            else {
                throw new IllegalArgumentException("Argument type must be 'Football' or 'Basketball'. Argument provided: "+betType);
            }
            return this;

        }

        private CustomerBetBuilder stake(int stake) throws IllegalArgumentException{
            if (stake < 0) {
                throw new IllegalArgumentException("Stake can't be negative");
            }
            this.stake = stake;
            return this;
        }


        private CustomerBetBuilder choice(String choice) throws IllegalStateException,IllegalArgumentException {
            // We check whether choice has a value from the valid options of each type of bet. If it does not, we
            // throw an IllegalArgumentException
            if ((betType.equals("Football") && !FootballBet.getChoices().contains(choice)) ||
                    (betType.equals("Basketball") && !BasketballBet.getChoices().contains(choice))) {
                throw new IllegalArgumentException("Argument Choice was not valid. Argument provided: " + choice);
            }
            this.choice = choice;
            return this;
        }



        private CustomerBet build() {
            return new CustomerBet(this);
        }

    } //inner class
}
