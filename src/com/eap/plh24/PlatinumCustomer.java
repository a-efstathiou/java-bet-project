package com.eap.plh24;

public class PlatinumCustomer extends Customer{

    public PlatinumCustomer(String n) {
        super(n);
    }

    @Override
    public int getMaxStake() {
        return 2000;
    }
}

