package org.example;

import io.github.thibaultmeyer.cuid.CUID;

public class Account {
  private String id = "";
  private double balance = 0.0;
  private boolean isActive = false;

  public final double getBalance() {
    return balance;
  }

  public final Account setOpeningBalance(double amount) {
    if (amount >= 0) {
      balance = amount;
    }
    return this;
  }

  public final String getAccountId() {
    return id;
  }

  public final double checkBalance() {
    System.out.printf("Current Balance: $%.2f%n%n", balance);
    return balance;
  }

  private Account setAccountActive() {
    isActive = true;
    return this;
  }

  public final boolean canWithdraw(double amount) {
    return isActive && amount > 0 && Double.isFinite(amount) && amount <= balance;
  }

  public final boolean withdraw(double amount) {
    if (canWithdraw(amount)) {
      balance -= amount;
      checkBalance();
      return true;
    }
    return false;
  }

  public boolean deposit(double amount) {
    if (!isActive || amount <= 0 || !Double.isFinite(amount)) {
      return false;
    }
    balance += amount;
    checkBalance();
    return true;
  }

  public final boolean accountActive() {
    return isActive;
  }

  public final boolean openAccount() {
    if (isActive) return false;
    setAccountActive();
    setAccountId();
    return true;
  }

  public final Account setAccountId() {
    id = CUID.randomCUID2().toString();
    return this;
  }

  public final boolean closeAccount() {
    if (isActive) {
      isActive = false;
      return true;
    }
    return false;
  }
}
