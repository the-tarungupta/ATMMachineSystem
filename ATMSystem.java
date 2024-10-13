import java.util.*;

class BankAccount{
    private int balance;
    private int PIN;

    BankAccount(int initialBalance , int PIN){
        balance = initialBalance;
        this.PIN = PIN;
    }

    public int checkBalance(){
        return balance;
    }

    public boolean checkPin(int PIN){
        return this.PIN == PIN;
    }

    public void withdrawMoney(int amount){
        if(amount<=balance){
            balance-=amount;
            System.out.println(amount+" withdrawan successfully");
            System.out.println();
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
        }
        else{
            System.out.println("Invalid amount entered");
            System.out.println();
        }
    }


    public void changePin(int PIN){
        this.PIN = PIN;
        System.out.println("Your PIN changed successfully");
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
            System.out.println("1]. Check Balance");
            System.out.println("2]. Withdraw Money");
            System.out.println("3]. Deposit Money");
            System.out.println("4]. Change PIN");
            System.out.println("5]. Exit");
            System.out.println();

            System.out.print("Enter your choice : ");
            try{
                int choice = sc.nextInt();
                switch(choice){
                    case 1 : int pin = validPin(sc);

                        if(account.checkPin(pin)){
                            int balance = account.checkBalance();
                            System.out.println("Available Balance : "+balance);
                            System.out.println();
                        }
                        else{
                            System.out.println("Incorrect PIN. Try again");
                            System.out.println();
                        }
                        continue;
                    case 2 : pin = validPin(sc);
                        if(account.checkPin(pin)){
                            System.out.print("Enter amount to be withdrawan : ");
                            int amount = sc.nextInt();
                            account.withdrawMoney(amount);
                        }
                        else{
                            System.out.println("Incorrect PIN. Try again");
                            System.out.println();
                        }
                        continue;
                    case 3 : pin = validPin(sc);
                        if(account.checkPin(pin)){
                            System.out.print("Enter amount to be depsoited : ");
                            int amount = sc.nextInt();
                            account.depositMoney(amount);
                        }
                        else{
                            System.out.println("Incorrect PIN. Try again");
                            System.out.println();
                        }
                        continue;
                    case 4 : pin = validPin(sc);
                        if(account.checkPin(pin)){
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
                            System.out.println("Incorrect PIN. Try again");
                            System.out.println();
                        }
                        continue;
                    case 5 : System.out.println("Exiting....Thank you for using our ATM Service ");
                        exit = false;
                        break;
                    default : System.out.println("Invalid Choice. Choose 1 to 5 digit ! ");
                        continue;

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
                    continue;
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