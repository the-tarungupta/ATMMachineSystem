import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

class ATMSystem {
    public static void main(String[] args) {

        BankAccount account = new BankAccount(2000, 9956); // Creating account of user
        Scanner sc = new Scanner(System.in);
        boolean exit = true;

        LocalDateTime startTime = LocalDateTime.now();
        displayCurrentDateTime();

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
        
        LocalDateTime endTime = LocalDateTime.now();
        long minutesSpent = Duration.between(startTime, endTime).toMinutes();

        
        System.out.println("\nTime spent on ATM system: " + minutesSpent + " minutes.");
        displayCurrentDateTime();
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

    private static void displayCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, hh:mm a");
        System.out.println("\n------- " + now.format(formatter) + " -------");
    }
}
