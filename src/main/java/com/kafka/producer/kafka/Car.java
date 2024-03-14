package com.kafka.producer.kafka;
import java.text.SimpleDateFormat;
import java.util.*;
class Car {
    String make, model, dealerName, state;
    int year, price;
    Date saleTimestamp;

    public Car() {
        // Initialize fields with random values
        Random rand = new Random();
        String[] makes = {"Ford", "Acura", "Kia", "Volkswagen", "Honda"};
        String[] models = {"Focus", "Escape", "MDX", "Forte", "GTI", "Cruze Limited", "Model Y", "Soul", "Liberty"};
        String[] states = {"MO", "AL", "AR", "HI", "IL"};
        String[] dealers = {"A", "D", "F", "H", "L", "O"};

        make = makes[rand.nextInt(makes.length)];
        model = models[rand.nextInt(models.length)];
        year = 2010 + rand.nextInt(15); // Random year between 2010 and 2024
        price = 10000 + rand.nextInt(20000); // Random price between 10000 and 29999

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -rand.nextInt(30)); // Subtract up to 30 days from current date
        saleTimestamp = cal.getTime(); // Set sale timestamp within past 30 days

        //dealerName = Character.toString((char) ('A' + rand.nextInt(26))); // Random dealer name (A-Z)
        dealerName = dealers[rand.nextInt(dealers.length)];
        state = states[rand.nextInt(states.length)]; // Random state abbreviation
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return make + "\t" + model + "\t" + year + "\t" +
                sdf.format(saleTimestamp) + "\t" +
                dealerName + "\t" + state + "\t" +
                price;
    }
}

