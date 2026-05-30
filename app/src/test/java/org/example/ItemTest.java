package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

public class ItemTest {
  double delta = 0.01;

  @ParameterizedTest(name = "createItemTest - {0}")
  @MethodSource("itemData")
  void createItemTest(String name, String id, int quantity, double price) {

    Item item = new Item(name, id, quantity, price);

    assertEquals(quantity, item.size());
    assertEquals(quantity, item.count());
    assertEquals(quantity, item.length());
    assertEquals(price * quantity, item.inventoryCost(), delta);

    assertEquals(name, item.getItemName());
    assertEquals(id, item.getItemId());
  }

  @ParameterizedTest(name = "incrementItemTest - {0}")
  @MethodSource("itemData")
  void incrementItemTest(String name, String id, int quantity, double price) {

    Item item = new Item(name, id, quantity, price);

    item.incrementItemQuantity(10);

    assertEquals(quantity + 10, item.count());
  }

  @ParameterizedTest(name = "decrementItemTest - {0}")
  @MethodSource("itemData")
  void decrementItemTest(String name, String id, int quantity, double price) {

    Item item = new Item(name, id, quantity, price);

    item.decrementItemQuantity(5);

    assertEquals(quantity - 5, item.count());
  }

  @ParameterizedTest(name = "updateInventoryVerificationTest - {0}")
  @MethodSource("itemData")
  void updateInventoryVerificationTest(String name, String id, int quantity, double price) {

    Item item = new Item(name, id, quantity, price);

    item.updateItemInventory(7);
    assertEquals(7, item.count());

    item.updateItemInventory(20);
    assertEquals(20, item.count());
  }

  @ParameterizedTest(name = "updateInventoryTest - {0}")
  @MethodSource("itemData")
  void updateInventoryTest(String name, String id, int quantity, double price) {

    Item item = new Item(name, id, quantity, price);

    item.updateItemInventory(7);

    assertEquals(7, item.count());
  }

  @ParameterizedTest(name = "resetInventoryTest - {0}")
  @MethodSource("itemData")
  void resetInventoryTest(String name, String id, int quantity, double price) {

    Item item = new Item(name, id, quantity, price);

    item.incrementItemQuantity(50);
    item.resetInventory();

    assertEquals(quantity, item.count());
  }

  @Test
  @DisplayName("clearInventory sets quantity to zero")
  void clearInventoryTest() {

    Item item = new Item("Test", "123", 20, 1.0);

    item.clearInventoryCount();

    assertEquals(0, item.count());
  }

  @Test
  @DisplayName("setPrice updates price correctly")
  void setPriceTest() {

    Item item = new Item("Test", "123", 10, 2.0);

    item.setPrice(5.5);

    assertEquals(5.5, item.inventoryCost() / item.count(), delta);
  }

  @Test
  @DisplayName("setName updates name correctly")
  void setNameTest() {

    Item item = new Item("Old", "123", 10, 2.0);

    item.setName("New");

    assertEquals("New", item.getItemName());
  }

  @Test
  @DisplayName("inventoryCost handles zero quantity")
  void zeroQuantityCostTest() {

    Item item = new Item("Zero", "999", 0, 10.0);

    assertEquals(0.0, item.inventoryCost(), delta);
  }

  @Test
  @DisplayName("inventoryCost handles zero price")
  void zeroPriceCostTest() {

    Item item = new Item("Free", "999", 10, 0.0);

    assertEquals(0.0, item.inventoryCost(), delta);
  }

  static Stream<Arguments> itemData() {
    return Stream.of(
        Arguments.of("Amos Banana Peelerz", "810051807981", 12, 3.99),
        Arguments.of("Amos Peelerz Pineapple", "810179080501", 18, 3.99),
        Arguments.of("Amos Peelerz Apple", "810179080440", 24, 3.99),
        Arguments.of("Amos Peelerz Lychee", "810051806809", 16, 3.99),
        Arguments.of("Amos Peelerz Kiwi", "810179083281", 12, 3.99),
        Arguments.of("Amos Peelerz Watermelon", "810179085070", 12, 3.99),
        Arguments.of("Amos Fruit Burst Peach", "810051808278", 18, 2.99));
  }
}
