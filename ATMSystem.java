import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;


class BankAccount{
    private int balance;
    private int PIN;
    private int failedAttempts;
    private LocalDateTime blockedUntil;
    static List<String> transactionHistory = new ArrayList<>();


    BankAccount(int initialBalance , int PIN){
        balance = initialBalance;
        this.PIN = PIN;
        this.failedAttempts = 0;
        this.blockedUntil = null;        // card is not yet blocked
    }

    public boolean isBlocked(){


        if(blockedUntil !=null){
            LocalDateTime  now = LocalDateTime.now();
            if( now.isBefore(blockedUntil) ){      // Current date is before the blockedUntil date
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
                blockedUntil = null;   // card gets unblocked
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
        if(amount<=balance){
            balance-=amount;
            System.out.println(amount+" withdrawan successfully");
            System.out.println();
            ATMSystem.addTransaction("Withdrawal", amount);
        }
        else{
            System.out.println("Insufficient balance in your account");
            System.out.println();
        }
    }


    public void depositMoney(int amount){
        if(amount>0){
            balance += amount;
            System.out.println(amount+" deposited successfully");
            System.out.println();
            ATMSystem.addTransaction("Deposit", amount);
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
                switch(choice){
                    case 1 : int pin = validPin(sc);

                        if(account.verifyPin(pin)){
                            int balance = account.checkBalance();
                            System.out.println("Available Balance : "+balance);
                            System.out.println();
                        }
                        else{

                            System.out.println();
                        }
                        addTransaction("Balance Check", 0);
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

                        continue;
                    case 3 : pin = validPin(sc);
                        if(account.verifyPin(pin)){
                            System.out.print("Enter amount to be depsoited : ");
                            int amount = sc.nextInt();
                            account.depositMoney(amount);

                        }
                        else{

                            System.out.println();
                        }

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
                        continue;
                    case 5 : pin = validPin(sc);
                                 if(account.verifyPin(pin)) {
                                     showMiniStatement();
                                     continue;
                                 }
                                 else{

                                     continue;
                                 }

                    case 6 : System.out.println("Exiting....Thank you for using our ATM Service ");
                        exit = false;
                        break;
                    default : System.out.println("Invalid Choice. Choose 1 to 5 digit ! ");


                }
            }
            catch(Exception e){
                System.out.println("Error! You have to enter a digit");
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

// adding things in mini statement
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

    // displaying mini statement
    public static void showMiniStatement() {

        System.out.println("---- Mini Statement ----");
        if (BankAccount.transactionHistory.isEmpty()) {
            System.out.println("No transactions to display.");
        } else {
            for (String transaction : BankAccount.transactionHistory) {
                System.out.println(transaction);
            }
        }
        System.out.println("------------------------");
    }
}