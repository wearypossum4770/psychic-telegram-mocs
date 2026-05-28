package org.example;

import java.util.Scanner;

public class BankingMenu {

  private final Bank bank;
  private final String[] menu = {"Check Balance", "Deposit", "Withdraw", "Exit"};

  public BankingMenu(Bank bank) {
    this.bank = bank;
  }

  private void printTitle() {
    System.out.println("\n==== Banking Menu ====");
  }

  private void printOptions() {
    for (int i = 0; i < menu.length; i++) {
      System.out.println((i + 1) + ". " + menu[i]);
    }
  }

  private void promptChoice() {
    System.out.print("Enter your choice: ");
  }

  private int getUserSelection(Scanner scanner) {
    String input = getNextLineSafe(scanner);
    if (input == null || input.trim().isEmpty()) {
      return -1;
    }
    try {
      return Integer.parseInt(input.trim());
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private static String getNextLineSafe(Scanner scanner) {
    try {
      return scanner.hasNextLine() ? scanner.nextLine() : null;
    } catch (Exception e) {
      return null;
    }
  }

  private static double getDoubleInput(Scanner scanner) {
    String input = getNextLineSafe(scanner);
    if (input == null) return 0.0;
    try {
      return Double.parseDouble(input.trim());
    } catch (Exception e) {
      System.out.println("Invalid number entered.");
      return 0.0;
    }
  }

  private void invalidInputMessage() {
    System.out.println("Invalid Input! Please enter a valid option to continue");
  }

  public void mainMenu(Scanner scanner, Account account) {
    printTitle();
    printOptions();
    promptChoice();

    int choice = getUserSelection(scanner);

    if (choice > 0 && choice <= menu.length) {
      if (menu[choice - 1].equalsIgnoreCase("Exit")) {
        System.out.println("Thank you for using our banking app!");
        System.exit(0);
      }
      selectScreen(choice, scanner, account);
    } else {
      invalidInputMessage();
    }
  }

  public void selectScreen(int screen, Scanner scanner, Account account) {
    switch (screen) {
      case 1:
        bank.checkBalance(account);
        break;
      case 2:
        System.out.print("Enter deposit amount: ");
        double depAmount = getDoubleInput(scanner);
        bank.deposit(account, depAmount);
        break;
      case 3:
        System.out.print("Enter withdrawal amount: ");
        double wdAmount = getDoubleInput(scanner);
        bank.withdraw(account, wdAmount);
        break;
      default:
        System.out.println("❌ Unknown option.");
    }
  }

  public static void main(String[] args) {
    Bank bank = new Bank();
    Account account = bank.openNewAccount(1000.0);

    BankingMenu menu = new BankingMenu(bank);

    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println("Welcome to Simple Banking App!");
      while (true) {
        menu.mainMenu(scanner, account);
      }
    }
  }
}
