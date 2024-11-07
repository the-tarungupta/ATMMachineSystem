# ATM Machine System #

This is a console-based ATM Machine System implemented in Java, using core concepts such as Object-Oriented Programming (OOP), Exception Handling, Collections Framework, and simple multithreading. This application simulates basic ATM functionalities, enabling users to perform various banking transactions with restrictions for security and operational limits.

## Features ##

* **Check Balance**: View the current account balance.
* **Withdraw Money**: Withdraw a specified amount from the account.
* **Limit**: Daily transaction limit of 5 and a maximum withdrawal limit of ₹70,000 per transaction.
* **Deposit** Money: Deposit a specified amount into the account.
* **Change PIN**: Change the account PIN securely by verifying the old PIN. <br/>
              1. Allows PIN updates while enforcing a 4-digit requirement.<br/>
              2. Ensures the new PIN is different from the current one.
* **Mini Statement**: Shows an invoice of recent transactions (withdrawals, deposits, PIN changes) with the current date and time.
* **Daily Transaction Limit**: Limits users to 5 transactions per day.
* **PIN Retry Limit**: If the PIN is entered incorrectly 3 times, the card is blocked for 48 hours.


## Technologies Used ##

* **Java**: Core logic using OOP, Collections Framework, Exception Handling.
* **Java.util Package**: Used for handling date, time, and collection utilities.
* **Java.time Package**: Manages time-based restrictions, like card blocking for 48 hours.

This README provides an overview of our ATM Machine System .
