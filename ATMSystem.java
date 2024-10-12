import java.util.Scanner;

class BankAccount {
    private double balance;
    private final int PIN = 1234;

    BankAccount(int initialBalance){
        balance = initialBalance;
    }

    public double checkBalance(){
        return balance;
    }


    public void depositMoney(double amount){
        if(amount > 0){
            balance += amount;
            System.out.println(amount+" amount deposited successfully");
        }
        else{
            System.out.println("Invalid deposit amount");
        }
    }


    public void withdrawMoney(double amount){
        if(amount>0 && amount<=balance){
            balance -= amount;
            System.out.println(amount+" amount withdrawal successfully");
        }
        else if(amount<=0){
            System.out.println("Invalid amount for withdrawals");
        }
        else if(amount>balance){
            System.out.println("Insufficient balance");
        }
    }


    public boolean verifyPin(int inputPIN){
        return PIN == inputPIN;
    }


}

public class ATMSystem {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BankAccount account = new BankAccount(1000);
        boolean exit = true;

        while(exit){
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Deposit Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            try{
                    int choice = sc.nextInt();

                    if (choice < 4 && choice >= 1) {
                        System.out.print("Enter your PIN : ");
                        try {
                            int inputPIN = sc.nextInt();
                            if( !account.verifyPin(inputPIN)){
                                System.out.println("Invalid PIN. Try again");
                                continue;
                            }
                        }
                        catch (Exception e){
                            System.out.println("Error : Enter 4-digit PIN");
                            sc.next();
                            continue;
                        }
                    }

                    switch (choice) {
                        case 1:
                            System.out.println("Current balance : " + account.checkBalance());
                            break;
                        case 2:
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawAmount = sc.nextDouble();
                            account.withdrawMoney(withdrawAmount);
                            break;
                        case 3:
                            System.out.println("Enter amount to deposit: ");
                            double depositAmount = sc.nextDouble();
                            account.depositMoney(depositAmount);
                            break;
                        case 4:
                            exit = false;
                            System.out.println("Exiting .... Thankyou for using the ATM.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
            }

            catch(Exception e){
                System.out.println("Error : You have to enter a value b/w 1 to 4 ");
                sc.next();     // showing main menu options after throwing error
            }
        }



    }
}
