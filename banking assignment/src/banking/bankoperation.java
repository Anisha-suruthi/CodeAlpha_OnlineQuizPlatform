package banking;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class bankoperation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        // Create customers and their accounts
        bank.addCustomer("John Doe", "john123", 1000.0);
        bank.addCustomer("Jane Smith", "jane456", 2000.0);
        bank.addCustomer("Alice Johnson", "alice789", 1500.0);
        bank.addCustomer("Bob Brown", "bob101", 3000.0);
        bank.addCustomer("Eve Wilson", "eve202", 2500.0);

        try {
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.println("Enter your choice(1 or 2):");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter your password:");
                    String password = scanner.next();
                    Customer customer = bank.authenticate(password);
                    if (customer != null) {
                        System.out.println("\nLogin successful\nWelcome, " + customer.getName() + "!\n");
                        performBankingOperations(customer);
                    } else {
                        System.out.println("Invalid password.");
                    }
                    break;
                case 2:
                    System.out.println("THANK YOU !!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.nextLine(); // Consume the newline character
        }
        scanner.close();
    }

    private static void performBankingOperations(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = customer.getAccount();

        while (true) {
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw(Available Rs.2000,500,200,100)");
            System.out.println("3. Check Balance");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: \n");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                    case 2:
                        System.out.println("Enter withdrawal amount:");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.println("Current balance:Rs." + account.getBalance()+"\n");
                        break;
                    case 4:
                        System.out.println("Logging out...\n\nThank You For Using Our Banking Service");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.\n");
                //scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                e.printStackTrace();
            } finally {
                scanner.nextLine(); // Consume the newline character
            }
        }
    }
}

class Bank {
    private Map<Integer, Customer> customers;
    private int nextAccountNumber;

    public Bank() {
        customers = new HashMap<Integer, Customer>();
        nextAccountNumber = 1000; // Starting account number
    }

    public void addCustomer(String name, String password, double initialBalance) {
        int accountNumber = nextAccountNumber++;
        BankAccount account = new BankAccount(accountNumber, initialBalance);
        Customer customer = new Customer(name, password, account);
        customers.put(accountNumber, customer);
    }

    public Customer authenticate(String password) {
        for (Customer customer : customers.values()) {
            if (customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }
}

class Customer {
    private String name;
    private String password;
    private BankAccount account;

    public Customer(String name, String password, BankAccount account) {
        this.name = name;
        this.password = password;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public BankAccount getAccount() {
        return account;
    }
}

class BankAccount {
    private int accountNumber;
    private double balance;

    public BankAccount(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be greater than zero.");
            }
            balance += amount;
            System.out.println("Deposit successful. New balance: Rs." + balance);
        } catch (IllegalArgumentException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
    }

    public void withdraw(double amount) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
            }
            if (amount > balance) {
                throw new IllegalArgumentException("Insufficient funds!");
            }
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: Rs." + balance);
        } catch (IllegalArgumentException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    public double getBalance() {
        return balance;
    }
}