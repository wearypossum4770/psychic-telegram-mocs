package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

public class BankTest {
  private Bank bank;
  private Account sender;
  private Account receiver;

  @BeforeEach
  void setUp() {
    double openingAmount = 1_000.00;
    bank = new Bank();
    sender = bank.openNewAccount(openingAmount);
    receiver = bank.openNewAccount(openingAmount / 2);
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, 750.0, 100.0, 500.0, 1000.75, 9999.99})
  void testOpenNewAccountVariousBalances(double initialBalance) {
    Account acc = bank.openNewAccount(initialBalance);
    assertNotNull(acc);
    assertTrue(acc.accountActive());
    assertEquals(initialBalance, acc.getBalance(), 0.01);
  }

  @Test
  void testFindAccountById() {
    String id = sender.getAccountId();
    assertTrue(bank.findAccountById(id).isPresent());
  }

  @Test
  void testCheckBalance() {
    assertDoesNotThrow(() -> bank.checkBalance(sender));
    assertDoesNotThrow(() -> bank.checkBalance(receiver));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "g19l5ppxlbxynw3xjsxzahd4",
        "rkfsrsrqjgun74sj4he0ljkm",
        "rmvw5nlcmuktdclejv2vlvh2"
      })
  void findAccountByIdTest(String id) {
    assertFalse(bank.findAccountById(id).isPresent());
  }
}
