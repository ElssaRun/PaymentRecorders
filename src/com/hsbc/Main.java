package com.hsbc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {

        System.out.println("Program started! Please input your payment records...");

        Scanner scanner = new Scanner(System.in);
        ConcurrentHashMap<String, String> hm = new ConcurrentHashMap<>();
        Timer timer = new Timer();
        TimerTask task = new TimerTask (){
            public void run() {
                if(hm.size()>0){
                    System.out.println("Payment records as below: ");
                    for(Map.Entry<String, String> entry: hm.entrySet()) {
                        System.out.println(entry.getKey() + " " + entry.getValue());
                    }
                }
            }
        };
        timer.schedule(task, 60000L, 60000L);

        do {
            if(scanner.hasNext()){
                String string = scanner.nextLine();
                if("quit".equals(string)){
                    //quit program
                    System.out.println("Program quited!");
                    System.exit(1);
                }
                processMap(string, hm);
            }
        } while (true);
    }

    private static boolean processMap(String arg, ConcurrentHashMap<String, String> hm){
        String[] args = arg.split(" ");
        if(args.length != 2){
            System.out.println("Error message on '"+arg+"' : Only input Currency and amount can be accepted," +
                    " the example format is HKD 125.");
            return false;
        }
        if(!args[0].matches("^([A-Za-z]{2,3})$")){
            System.out.println("Error message '"+arg+"' : The first argument should be Currency," +
                    " the example format is HKD.");
            return false;
        }
        if(!args[1].matches("^([0-9]){1,999}$")
                && !args[1].matches("^[\\-|0-9][0-9]{1,999}$")){
            System.out.println("Error message '"+arg+"': The Second argument should be amount," +
                    " the example format is 125.");
            return false;
        }

        System.out.println("'" + args[0] + " " + args[1] + "' record data processed!");

        String currency = args[0].toUpperCase();
        String amount = hm.get(currency);
        if(amount != null && !"".equals(amount)){
            //already have currency
            Integer newAmount = Integer.valueOf(amount) + Integer.valueOf(args[1]);
            if(newAmount > 0){
                hm.put(currency, String.valueOf(newAmount));
            }else{
                hm.remove(currency);
            }
        }else{
            //do not have currency
            hm.put(currency, args[1]);
        }

        return true;
    }
}
