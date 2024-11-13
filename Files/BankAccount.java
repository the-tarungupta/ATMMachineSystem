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
