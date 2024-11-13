import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;


// Holding the account details of user
class BankAccount {
    private int balance;       // a/c balance
    private int PIN;           // atm pin for a/c
    private int failedAttempts; // no. of incorrect pins attempts
    private LocalDateTime blockedUntil;  // till when card get block
    private List<Transaction> transactionHistory = new ArrayList<>();  // contains list of transactions
    private static final int MAX_DAILY_TRANSACTIONS = 5;  // max daily transaction
    private static final int MAX_WITHDRAWAL_AMOUNT = 70000;  // max withdrawal amount
    private int transactionCount = 0;   // no. of transaction processed


    BankAccount(int initialBalance, int PIN) {
        this.balance = initialBalance;
        this.PIN = PIN;
        this.failedAttempts = 0;
        this.blockedUntil = null;
    }


    public boolean isBlocked() {
        if (blockedUntil != null) {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(blockedUntil)) {
                System.out.println("Your card is blocked. Remaining time for unblocking: " +
                        ChronoUnit.HOURS.between(now, blockedUntil) + " hours " +
                        ChronoUnit.MINUTES.between(now, blockedUntil) % 60 + " minutes.\n");
                return true;
            } else {
                blockedUntil = null;
                failedAttempts = 0;
            }
        }
        return false;
    }


    public boolean verifyPin(int PIN) {
        if (PIN == this.PIN) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) {
                blockedUntil = LocalDateTime.now().plusHours(48);
                System.out.println("Incorrect PIN entered 3 times. Card is blocked for 48 hours.");
            } else {
                System.out.println("Incorrect PIN. Attempt " + failedAttempts + " of 3.");
            }
            return false;
        }
    }


    public int checkBalance() {
        return balance;
    }


    public void withdrawMoney(int amount) {
        if (amount <= balance && amount >= 500 && amount <= MAX_WITHDRAWAL_AMOUNT) {
            balance -= amount;
            System.out.println(amount + " rs. withdrawn successfully");
            addTransaction("Withdrawal", amount);
            offerReceipt("Withdrawal", amount);
        } else if (amount < 500) {
            System.out.println("Minimum withdrawal amount: 500 rs.");
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }


    public void depositMoney(int amount) {
        if (amount >= 500) {
            balance += amount;
            System.out.println(amount + " rs. deposited successfully");
            addTransaction("Deposit", amount);
            offerReceipt("Deposit", amount);
        } else {
            System.out.println("Minimum deposit amount: 500 rs.");
        }
    }


    public void changePin(int PIN) {
        this.PIN = PIN;
        System.out.println("Your PIN changed successfully.");
        addTransaction("PIN Change", 0);
    }


    private void addTransaction(String type, int amount) {
        transactionHistory.add(new Transaction(type, amount));
        transactionCount++;
    }


    private void offerReceipt(String transactionType, double amount) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Would you like a receipt for this transaction? (yes/no): ");
        String response = scanner.next().toLowerCase();
        if (response.equals("yes")) {
            printReceipt(transactionType, amount);
        } else {
            System.out.println("Returning to main menu...");
        }
    }


    private void printReceipt(String transactionType, double amount) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("\n--- Virtual Receipt ---");
        System.out.println("Transaction Type: " + transactionType);
        System.out.println("Transaction Amount: " + amount + " rs.");
        System.out.println("Account Balance: " + balance + " rs.");
        System.out.println("Date & Time: " + now.format(formatter));
        System.out.println("-----------------------\n");
    }


    public void showMiniStatement() {
        System.out.println("---- Mini Statement ----");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions to display.");
        } else {
            transactionHistory.forEach(Transaction::displayTransaction);
        }
        System.out.println("Current Balance: " + balance + " rs.");
        System.out.println("------------------------");
    }
}


// Holds details for transaction processing
class Transaction {
    private String type;  // type of transaction
    private int amount;   // amount processed
    private LocalDateTime date;   // date time when transaction processed


    Transaction(String type, int amount) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }


    public void displayTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        if(type.equals("Withdrawal") || type.equals("Deposit")){
            System.out.println(type + " of " + amount + " rs on " + date.format(formatter));
        }
        else{
            if(type.equals("PIN Change")){
                System.out.println("\n"+type + "  " + date.format(formatter));
            }
        }
    }
}


class Feedback {
    private static int[] ratings = new int[100];   // array holding ratings
    private static int ratingIndex = 0;            // 0-indexing of above ratings array


    // Asking for Feedback from User
    public static void getFeedback(Scanner sc) {
        int rating=0;
        while (true) {
            System.out.print("Please rate your ATM experience (1 to 5): ");
            try{
                rating = sc.nextInt();
            }
            catch(Exception e){
                System.out.println("Please enter a number between 1 and 5.");
                sc.next();
                continue;
            }
            if (rating >= 1 && rating <= 5) {
                ratings[ratingIndex++] = rating;
                System.out.println("Thank you for your feedback!");
                break;
            } else {
                System.out.println("Invalid rating. Please enter a number between 1 and 5.");
            }
        }
        System.out.println("You rated the service: " + rating + " stars.");
    }
}


class ATMSystem {
    public static void main(String[] args) {

        BankAccount account = new BankAccount(2000, 9956); // Creating account of user
        Scanner sc = new Scanner(System.in);
        boolean exit = true;


        while (exit) {
            if (account.isBlocked()) {
                System.out.println("Your card is blocked. You cannot use ATM services.");
                break;
            }

            // Main Menu content
            System.out.println("\n1. Check Balance\n2. Withdraw Money\n3. Deposit Money\n4. Change PIN\n5. Mini Statement\n6. Exit");
            System.out.print("Enter your choice: ");
            try{
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        if (account.verifyPin(validPin(sc))) {
                            System.out.println("Available Balance: " + account.checkBalance() + " rs.");
                        }
                        break;
                    case 2:
                        if (account.verifyPin(validPin(sc))) {
                            System.out.print("Enter amount to withdraw: ");
                            account.withdrawMoney(sc.nextInt());
                        }
                        break;
                    case 3:
                        if (account.verifyPin(validPin(sc))) {
                            System.out.print("Enter amount to deposit: ");
                            account.depositMoney(sc.nextInt());
                        }
                        break;
                    case 4:
                        if (account.verifyPin(validPin(sc))) {
                            System.out.print("Enter your new PIN: ");
                            account.changePin(sc.nextInt());
                        }
                        break;
                    case 5:
                        if (account.verifyPin(validPin(sc))) {
                            account.showMiniStatement();
                        }
                        break;
                    case 6:
                        Feedback.getFeedback(sc);
                        exit = false;
                        break;
                    default:
                        System.out.println("Invalid Choice. Choose a number from 1 to 6.");
                }
            }
            catch(Exception e){
                System.out.println("Error! You have to enter an Integer");
                sc.next();
            }
        }
    }


    // Method for determining Pin getting valid or invalid
    private static int validPin(Scanner sc){
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
}
