import java.io.*;
import java.util.*;

public class BankManager {

    private static final String FILE_NAME = "bank_records.dat";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        char choice;

        do {
            System.out.println("\n====== SMART BANK SYSTEM ======");
            System.out.println("C - Create Account");
            System.out.println("V - View All Accounts");
            System.out.println("S - Search Account");
            System.out.println("D - Deposit");
            System.out.println("W - Withdraw");
            System.out.println("T - Total Bank Balance");
            System.out.println("X - Exit");
            System.out.print("Select Option: ");

            choice = sc.next().toUpperCase().charAt(0);

            switch (choice) {
                case 'C': createAccount(); break;
                case 'V': viewAccounts(); break;
                case 'S': searchAccount(); break;
                case 'D': deposit(); break;
                case 'W': withdraw(); break;
                case 'T': totalBankBalance(); break;
                case 'X': System.out.println("Thank you for using SmartBank."); break;
                default: System.out.println("Invalid Option!");
            }

        } while (choice != 'X');
    }

    // Create Account
    private static void createAccount() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            System.out.print("Enter Account Number: ");
            int accNo = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Account Holder Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            BankAccount acc = new BankAccount(accNo, name, balance);

            bw.write(acc.getAccountNumber() + "|" + acc.getAccountHolder() + "|" + acc.getCurrentBalance());
            bw.newLine();

            System.out.println("Account Created Successfully.");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }

    // View All Accounts
    private static void viewAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String record;

            while ((record = br.readLine()) != null) {
                String[] data = record.split("\\|");

                BankAccount acc = new BankAccount(
                        Integer.parseInt(data[0]),
                        data[1],
                        Double.parseDouble(data[2])
                );

                acc.display(true);
            }

        } catch (IOException e) {
            System.out.println("No accounts found.");
        }
    }

    // Search Account
    private static void searchAccount() {
        System.out.print("Enter Account Number to search: ");
        int searchNo = sc.nextInt();

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String record;

            while ((record = br.readLine()) != null) {
                String[] data = record.split("\\|");

                if (Integer.parseInt(data[0]) == searchNo) {

                    BankAccount acc = new BankAccount(
                            Integer.parseInt(data[0]),
                            data[1],
                            Double.parseDouble(data[2])
                    );

                    acc.display(true);
                    found = true;
                    break;
                }
            }

            if (!found)
                System.out.println("Account not found.");

        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }

    // Deposit
    private static void deposit() {
        modifyBalance(true);
    }

    // Withdraw
    private static void withdraw() {
        modifyBalance(false);
    }

    // Common Method for Deposit & Withdraw
    private static void modifyBalance(boolean isDeposit) {

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        List<String> records = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String record;

            while ((record = br.readLine()) != null) {
                String[] data = record.split("\\|");

                if (Integer.parseInt(data[0]) == accNo) {

                    BankAccount acc = new BankAccount(
                            Integer.parseInt(data[0]),
                            data[1],
                            Double.parseDouble(data[2])
                    );

                    System.out.print("Enter Amount: ");
                    double amount = sc.nextDouble();

                    if (isDeposit) {
                        acc.depositAmount(amount);
                    } else {
                        if (!acc.withdrawAmount(amount)) {
                            System.out.println("Minimum balance must be 1000.");
                            return;
                        }
                    }

                    record = acc.getAccountNumber() + "|" + acc.getAccountHolder() + "|" + acc.getCurrentBalance();
                    updated = true;
                }

                records.add(record);
            }

        } catch (IOException e) {
            System.out.println("Error processing transaction.");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String rec : records) {
                bw.write(rec);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }

        if (updated)
            System.out.println("Transaction Successful.");
        else
            System.out.println("Account not found.");
    }

    // Advanced Feature: Total Bank Balance
    private static void totalBankBalance() {

        double total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String record;

            while ((record = br.readLine()) != null) {
                String[] data = record.split("\\|");
                total += Double.parseDouble(data[2]);
            }

            System.out.println("Total Bank Holding: " + total);

        } catch (IOException e) {
            System.out.println("Unable to calculate total.");
        }
    }
}
