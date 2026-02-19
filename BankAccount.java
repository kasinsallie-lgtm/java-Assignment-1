public class BankAccount {

    private int accountNumber;
    private String accountHolder;
    private double currentBalance;

    // Default Constructor
    public BankAccount() {
    }

    // Parameterized Constructor
    public BankAccount(int accountNumber, String accountHolder, double currentBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.currentBalance = currentBalance;
    }

    // Getters
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double amount) {
        this.currentBalance = amount;
    }

    // Deposit
    public void depositAmount(double amount) {
        if (amount > 0) {
            currentBalance += amount;
        }
    }

    // Withdraw
    public boolean withdrawAmount(double amount) {
        if (amount > 0 && (currentBalance - amount) >= 1000) {
            currentBalance -= amount;
            return true;
        }
        return false;
    }

    // Method Overloading
    public void display() {
        System.out.println(accountNumber + " | " + accountHolder + " | " + currentBalance);
    }

    public void display(boolean detailed) {
        if (detailed) {
            System.out.println("\n----- ACCOUNT DETAILS -----");
            System.out.println("Account No : " + accountNumber);
            System.out.println("Holder Name: " + accountHolder);
            System.out.println("Balance    : " + currentBalance);
            System.out.println("---------------------------");
        }
    }
}