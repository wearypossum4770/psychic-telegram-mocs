package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

public class AccountTest {
  private final double delta = 0.01;
  private Account acct;

  @BeforeEach
  void setUp() {
    acct = new Account();
  }

  @Test
  public void accountCreationSuccessful() {
    // Before opening account.
    assertEquals("", acct.getAccountId());
    assertEquals(0.0, acct.getBalance(), delta);
    assertFalse(acct.accountActive());
    // After opening account.
    boolean created = acct.openAccount();
    assertTrue(created);
    assertNotNull(acct.getAccountId());
    assertFalse(acct.getAccountId().isEmpty());
    assertTrue(acct.accountActive());
    assertEquals(0.0, acct.getBalance(), delta);
  }

  @Test
  void shouldNotOpenAlreadyActiveAccount() {
    acct.openAccount();
    boolean secondAttempt = acct.openAccount();
    assertFalse(secondAttempt);
  }

  @ParameterizedTest
  @ValueSource(doubles = {0.0, 500.0, 1_000.75, 2_500.0, 9_999.99, Double.MAX_VALUE})
  void setOpeningBalanceTest(double openingAmount) {
    acct.openAccount();
    acct.setOpeningBalance(openingAmount);
    assertEquals(openingAmount, acct.getBalance(), delta);
  }

  @ParameterizedTest(name = "Deposit Test: active={0}, amount={1} → success={2}, finalBalance={3}")
  @MethodSource("accountDepositTestData")
  void testDeposit(boolean active, double amount, boolean completed, double expected) {
    if (active) {
      acct.openAccount();
      acct.setOpeningBalance(500.0);
    }

    boolean success = acct.deposit(amount);
    double actual = acct.getBalance();
    assertEquals(completed, success, () -> "Deposit success status mismatch for amount: " + amount);
    assertEquals(expected, actual, () -> "Final balance incorrect after deposit attempts");
  }

  @Test
  void testDepositInactiveAccount() {
    boolean success = acct.deposit(100.0);
    assertFalse(success);
    assertEquals(0.0, acct.getBalance(), delta);
  }

  @Test
  void testDepositZeroOrNegative() {
    acct.openAccount();
    assertFalse(acct.deposit(0));
    assertFalse(acct.deposit(-50));
  }

  @ParameterizedTest
  @MethodSource("withdrawFromAccountData")
  void testWithdrawSuccess(double opening, double amount, double remaining, boolean able) {
    acct.openAccount();
    acct.setOpeningBalance(opening);

    boolean success = acct.withdraw(amount);
    assertEquals(success, able);
    assertEquals(remaining, acct.getBalance(), delta);
  }

  @Test
  void testCloseAccount() {
    acct.openAccount();
    assertTrue(acct.closeAccount());
    assertFalse(acct.accountActive());
  }

  @Test
  void testCloseAccountAlreadyClosed() {
    assertFalse(acct.closeAccount());
  }

  static Stream<Arguments> accountDepositTestData() {
    return Stream.of(
        Arguments.of(true, 100.0, true, 600.0),
        Arguments.of(true, 250.50, true, 750.50),
        Arguments.of(true, 0.01, true, 500.01),
        Arguments.of(true, 9999.99, true, 10499.99),
        Arguments.of(true, 0.0, false, 500.0),
        Arguments.of(true, -50.0, false, 500.0),
        Arguments.of(true, -0.01, false, 500.0),
        Arguments.of(false, 100.0, false, 0.0),
        Arguments.of(false, 500.0, false, 0.0),
        Arguments.of(false, 0.0, false, 0.0),
        Arguments.of(false, -100.0, false, 0.0),
        Arguments.of(true, Double.MAX_VALUE, true, 500.0 + Double.MAX_VALUE),
        Arguments.of(true, Double.POSITIVE_INFINITY, false, 500.0),
        Arguments.of(true, Double.NaN, false, 500.0));
  }

  static Stream<Arguments> withdrawFromAccountData() {
    return Stream.of(
        Arguments.of(200.0, 300.0, 200.0, false),
        Arguments.of(500.0, 100, 400.0, true),
        Arguments.of(500.0, 600, 500.0, false),
        Arguments.of(500.0, 0, 500.0, false),
        Arguments.of(500.0, -10, 500.0, false),
        Arguments.of(1_000.0, 300.0, 700.0, true));
  }
}
