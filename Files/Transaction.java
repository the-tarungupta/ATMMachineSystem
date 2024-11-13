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