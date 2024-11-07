import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

// Holding the account details of user
class BankAccount{
    protected int balance;          // balance in account
    private int PIN;                // atm pin
    private int failedAttempts;     // no. of wrong pin attempts
    private LocalDateTime blockedUntil;         // time till card gets blocked
    static List<String> transactionHistory = new ArrayList<>();      // holds transaction history
    public static int transactionCount = 0;      // no. of times user uses atm services
    static final int MAX_DAILY_TRANSACTIONS = 5;   // max time user can use atm services
    private static final int MAX_WITHDRAWAL_AMOUNT = 70000;     // max withdrawal user can make at once


    BankAccount(int initialBalance , int PIN){
        balance = initialBalance;
        this.PIN = PIN;
        this.failedAttempts = 0;
        this.blockedUntil = null;        
    }

    public boolean isBlocked(){


        if(blockedUntil !=null){
            LocalDateTime  now = LocalDateTime.now();
            if( now.isBefore(blockedUntil) ){      
                System.out.println("1. View remaining time for unblocking.");
                System.out.println("2. Exit");
                Scanner sc = new Scanner(System.in);
                while(true) {
                    System.out.print("Enter your choice : ");
                    int choice = 0;
                    try {
                        choice = sc.nextInt();
                        if (choice == 1) {
                            long hoursLeft = ChronoUnit.HOURS.between(now, blockedUntil);
                            long minLeft = ChronoUnit.MINUTES.between(now, blockedUntil);
                            System.out.println("Time remaining for card unblocking : " + hoursLeft +  " hours "+minLeft+" minutes.");

                        } else if (choice == 2) {
                            System.out.println("Exiting...");
                            break;
                        } else {
                            System.out.println("Invalid choice.");

                        }
                    } catch (Exception e) {
                        System.out.println("You have to choose either 1 or 2.");
                        sc.next();
                    }

                }

                return true;
            }
            else{
                blockedUntil = null;   
                failedAttempts=0;
            }
        }

        return false;
    }

    public boolean verifyPin(int PIN){
        if(PIN == this.PIN){
            failedAttempts = 0;
            return true;
        }
        else{
            failedAttempts++;
            if(failedAttempts >= 3){
                blockedUntil = LocalDateTime.now().plusHours(48);
                System.out.println("Incorrect PIN entered 3 times.");
            }
            else {
                System.out.println("Incorrect PIN. Attempt " + failedAttempts + " of 3.");
            }
            return false;
        }
    }

    public int checkBalance(){
        return balance;
    }



    public void withdrawMoney(int amount){
        if(amount<=balance && amount>=500){
            if (amount > MAX_WITHDRAWAL_AMOUNT) {
                System.out.println("You have reached your daily transaction limit of 70000rs. ");
            }
            else{
            balance-=amount;
            System.out.println(amount+" rs. withdrawan successfully");
            System.out.println();
            ATMSystem.addTransaction("Withdrawal", amount);
            }
        }
        else if(amount<500){
            System.out.println("Minimum Withdrawal amount : 500rs.");
        }
        else{
            System.out.println("Insufficient balance in your account");
            System.out.println();
        }
    }


    public void depositMoney(int amount){
        if(amount>=500){
            balance += amount;
            System.out.println(amount+" rs. deposited successfully");
            System.out.println();
            ATMSystem.addTransaction("Deposit", amount);
        }
        else if(amount<500 && amount>0){
            System.out.println("Minimum deposit amount : 500rs. ");
        }
        else{
            System.out.println("Invalid amount entered");
            System.out.println();
        }
    }


    public void changePin(int PIN){
        this.PIN = PIN;
        System.out.println("Your PIN changed successfully");
        ATMSystem.addTransaction("PIN Change", 0);
        System.out.println();
    }
}

class ATMSystem {
    public static void main(String[] args) {

        BankAccount account = new BankAccount(2000 , 9956);
        Scanner sc = new Scanner(System.in);

        System.out.println("--------------------------ATM Machine System---------------------------");
        System.out.println("Here are your Main Menu options : ");
        System.out.println();
        boolean exit = true;


        while(exit){
            if(account.isBlocked()){
                System.out.println("Your card is blocked. You cannot use ATM services.");
                break;
            }
            System.out.println("1]. Check Balance");
            System.out.println("2]. Withdraw Money");
            System.out.println("3]. Deposit Money");
            System.out.println("4]. Change PIN");
            System.out.println("5]. Mini Statement");
            System.out.println("6]. Exit");
            System.out.println();

            System.out.print("Enter your choice : ");
            try{
                int choice = sc.nextInt();
                if (BankAccount.transactionCount >= BankAccount.MAX_DAILY_TRANSACTIONS) {
                System.out.println("You have reached your daily transaction limit.");
                break;
                }
                switch(choice){
                    case 1 : int pin = validPin(sc);

                        if(account.verifyPin(pin)){
                            int balance = account.checkBalance();
                            System.out.println("Available Balance : "+balance+" rs.");
                            System.out.println();
                        }
                        else{

                            System.out.println();
                        }
                        addTransaction("Balance Check", 0);
                        BankAccount.transactionCount++;
                        continue;
                    case 2 : pin = validPin(sc);
                        if(account.verifyPin(pin)){
                            System.out.print("Enter amount to be withdrawan : ");
                            int amount = sc.nextInt();
                            account.withdrawMoney(amount);
                        }
                        else{

                            System.out.println();
                        }
                        BankAccount.transactionCount++;
                        continue;
                    case 3 : pin = validPin(sc);
                        if(account.verifyPin(pin)){
                            System.out.print("Enter amount to be deposited : ");
                            int amount = sc.nextInt();
                            account.depositMoney(amount);

                        }
                        else{

                            System.out.println();
                        }
                        BankAccount.transactionCount++;
                        continue;
                    case 4 : pin = validPin(sc);
                        if(account.verifyPin(pin)){
                            try{
                                System.out.print("Enter your new PIN : ");
                                int newPIN = sc.nextInt();
                                if(String.valueOf(newPIN).length() !=4){
                                    System.out.println("Your new PIN must be 4-digit. Try again.");
                                }
                                else{
                                    account.changePin(newPIN);
                                }
                            }
                            catch(Exception e){
                                System.out.println("Error! Your PIN must be in digits.");
                                sc.next();
                            }
                        }
                        else{
                            System.out.println();
                        }
                        BankAccount.transactionCount++;
                        continue;
                    case 5 : pin = validPin(sc);
                                 if(account.verifyPin(pin)) {
                                     showMiniStatement(account);
                                     BankAccount.transactionCount++;
                                     continue;
                                 }
                                 else{
                                     BankAccount.transactionCount++;
                                     continue;
                                 }
                                

                    case 6 : System.out.println("Exiting....Thank you for using our ATM Service ");
                        exit = false;
                        break;
                    default : System.out.println("Invalid Choice. Choose 1 to 5 digit ! ");


                }
            }
            catch(Exception e){
                System.out.println("Error! You have to enter an Integer");
                sc.next();
            }
        }

    }

    public static int validPin(Scanner sc){
        int PIN;
        while(true){
            try{
                System.out.print("Enter your PIN : ");
                PIN = sc.nextInt();
                if(String.valueOf(PIN).length() != 4){
                    System.out.println("Your PIN must be 4-digit. Try again.");

                }
                else{
                    break;
                }
            }
            catch(Exception e){
                System.out.println("Error! Your PIN must be in digits.");
                sc.next();
            }
        }
        return PIN;
    }


    public static void addTransaction(String type , int amount){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy           HH:mm ");
        String formattedDate = now.format(formatter);

        if (type.equals("Withdrawal")) {
            BankAccount.transactionHistory.add(type + " of " + amount + "rs      " + formattedDate);
        }
        else if (type.equals("Deposit")){
            BankAccount.transactionHistory.add(type + " of " + amount + "rs            " + formattedDate);
        }
        else {
            if(type.equals("PIN Change")){
                BankAccount.transactionHistory.add(type + "                  " + formattedDate);
            }
            else {
                BankAccount.transactionHistory.add(type + "               " + formattedDate);
            }
        }
    }


    public static void showMiniStatement(BankAccount account) {

        System.out.println("---- Mini Statement ----\n");
        if (BankAccount.transactionHistory.isEmpty()) {
            System.out.println("No transactions to display.");
        } else {
            for (String transaction : BankAccount.transactionHistory) {
                System.out.println(transaction);
            }
            System.out.println("\nCurrent Balance : "+account.balance+" rs.");
        }
        System.out.println("------------------------");
    }
}