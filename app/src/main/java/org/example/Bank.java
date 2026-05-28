package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Bank {

  private final List<Account> accounts = new ArrayList<>();

  public Account openNewAccount(double initialBalance) {
    Account account = new Account();
    account.openAccount();
    if (initialBalance > 0) {
      account.setOpeningBalance(initialBalance);
    }
    accounts.add(account);
    return account;
  }

  public Optional<Account> findAccountById(String accountId) {
    return accounts.stream().filter(acc -> acc.getAccountId().equals(accountId)).findFirst();
  }

  public void checkBalance(Account account) {
    if (account == null) {
      System.out.println("No account provided.");
      return;
    }
    account.checkBalance();
  }

  public boolean deposit(Account account, double amount) {
    if (account == null) {
      System.out.println("No account provided.");
      return false;
    }
    if (amount <= 0) {
      System.out.println("Deposit amount must be greater than 0.00");
      return false;
    }
    return account.deposit(amount);
  }

  public boolean withdraw(Account account, double amount) {
    if (account == null) {
      System.out.println("No account provided.");
      return false;
    }
    if (!account.canWithdraw(amount)) {
      System.out.println(
          "The withdraw amount exceeds the available funds. Please try a different amount.");
      return false;
    }
    return account.withdraw(amount);
  }

  public List<Account> getAllAccounts() {
    return Collections.unmodifiableList(accounts);
  }
}
