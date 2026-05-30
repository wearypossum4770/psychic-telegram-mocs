package org.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

public class InventoryManagerTest {

  InventoryManager manager;

  String[] itemNames =
      new String[] {
        "Amos Banana Peelerz",
        "Amos Banana Peelerz",
        "Push Pops Jumbo Fruit Frenzy",
        "Ring Pop Fruit Fest",
        "Juicy Drop Taffy",
        "Juicy Drop Pop",
        "Amos Banana Peelerz",
        "Juicy Drop Gummy Mystery Cube Original",
        "Juicy Drop Gummy Mystery Cube Wild Berry",
        "Lednessa Dubai Chocholate Cones",
        "Mini Taco",
      };

  String[] itemIds =
      new String[] {
        "8-10051-80797-4",
        "8-10051-80753-0",
        "0-41116-00527-5",
        "0-41116-00519-0",
        "0-41116-00582-4",
        "0-41116-00592-3",
        "8-10051-80798-1",
        "0-4116-13245-2",
        "0-4116-13247-6",
        "7-94711-01506-3",
        "6-30855-12206-2"
      };

  int[] itemQuantities = new int[] {12, 12, 18, 24, 16, 18, 12, 12, 12, 12, 16};

  @BeforeEach
  void setup() {
    manager = new InventoryManager();
  }

  @ParameterizedTest(name = "containsExistingItemTest - [{index}] - {0}")
  @MethodSource("inventoryData")
  void containsExistingItemTest(String name, String id, int quantity, double price) {

    manager.addItem(new Item(name, id, quantity, price));

    assertTrue(manager.contains(id));
  }

  @ParameterizedTest(name = "deleteExistingItemTest - [{index}] - {0}")
  @MethodSource("inventoryData")
  void deleteExistingItemTest(String name, String id, int quantity, double price) {

    manager.addItem(new Item(name, id, quantity, price));

    int before = manager.count();

    assertTrue(manager.deleteItem(id));
    assertFalse(manager.contains(id));
    assertEquals(before - 1, manager.count());
  }

  @ParameterizedTest(name = "updatePriceTest - [{index}] - {0}")
  @MethodSource("inventoryData")
  void updatePriceTest(String name, String id, int quantity, double price) {

    manager.addItem(new Item(name, id, quantity, price));

    assertTrue(manager.updateItemPrice(id, 9.99));

    Item item = manager.scan(id);

    assertNotNull(item);
    assertEquals(9.99, item.getPrice());
  }

  @ParameterizedTest(name = "incrementInventoryTest - [{index}] - {0}")
  @MethodSource("inventoryData")
  void incrementInventoryTest(String name, String id, int quantity, double price) {

    manager.addItem(new Item(name, id, quantity, price));

    assertTrue(manager.incrementInventory(id, 10));

    Item item = manager.scan(id);

    assertNotNull(item);
    assertEquals(quantity + 10, item.count());
  }

  @Test
  @DisplayName("linkedHashMap preserves insertion order")
  void insertionOrderTest() {

    manager.addItem(new Item("A", "1", 1, 1.0));
    manager.addItem(new Item("B", "2", 1, 1.0));
    manager.addItem(new Item("C", "3", 1, 1.0));

    Iterator<Item> it = manager.getItems().iterator();

    assertEquals("1", it.next().getItemId());
    assertEquals("2", it.next().getItemId());
    assertEquals("3", it.next().getItemId());
  }

  @ParameterizedTest(name = "scanReturnsCorrectItem - [{index}] - {0}")
  @MethodSource("inventoryData")
  void scanReturnsCorrectItem(String name, String id, int quantity, double price) {

    manager.addItem(new Item(name, id, quantity, price));

    Item item = manager.scan(id);

    assertNotNull(item);
    assertEquals(name, item.getItemName());
    assertEquals(id, item.getItemId());
  }

  @ParameterizedTest(name = "missingBarcodeTest - [{index}] - ''{0}''")
  @MethodSource("invalidBarcodeData")
  void missingBarcodeTest(String id) {

    assertNull(manager.scan(id));
    assertFalse(manager.contains(id));
    assertFalse(manager.deleteItem(id));
  }

  @ParameterizedTest(name = "printInventoryTest - [{index}]")
  @MethodSource("inventoryManagementData")
  void printInventoryTest(int testId, String[] expected) {

    manager.addItem(new Item("Amos Banana Peelerz", "8-10051-80797-4", 12, 2.45));
    manager.addItem(new Item("Amos Banana Peelerz", "8-10051-80753-0", 12, 2.45));
    manager.addItem(new Item("Push Pops Jumbo Fruit Frenzy", "0-41116-00527-5", 18, 2.45));
    manager.addItem(new Item("Ring Pop Fruit Fest", "0-41116-00519-0", 24, 2.45));
    manager.addItem(new Item("Juicy Drop Taffy", "0-41116-00582-4", 16, 2.45));
    manager.addItem(new Item("Juicy Drop Pop", "0-41116-00592-3", 18, 2.45));
    manager.addItem(new Item("Amos Banana Peelerz", "8-10051-80798-1", 12, 2.45));
    manager.addItem(new Item("Juicy Drop Gummy Mystery Cube Original", "0-4116-13245-2", 12, 2.45));
    manager.addItem(
        new Item("Juicy Drop Gummy Mystery Cube Wild Berry", "0-4116-13247-6", 12, 2.45));
    manager.addItem(new Item("Lednessa Dubai Chocholate Cones", "7-94711-01506-3", 12, 2.45));
    manager.addItem(new Item("Mini Taco", "6-30855-12206-2", 16, 2.45));

    String[] actual = manager.printInventory();

    assertArrayEquals(expected, actual);
  }

  static Stream<Arguments> invalidBarcodeData() {
    return Stream.of(
        Arguments.of(""), Arguments.of(" "), Arguments.of("missing"), Arguments.of("999999999999"));
  }

  static Stream<Arguments> inventoryData() {
    return Stream.of(
        Arguments.of("Amos Peelerz Banana", "810051807981", 12, 3.99),
        Arguments.of("Amos Peelerz Pineapple", "810179080501", 18, 3.99),
        Arguments.of("Amos Peelerz Apple", "810179080440", 24, 3.99),
        Arguments.of("Amos Peelerz Lychee", "810051806809", 16, 3.99),
        Arguments.of("Amos Peelerz Kiwi", "810179083281", 12, 3.99),
        Arguments.of("Amos Peelerz Watermelon", "810179085070", 12, 3.99),
        Arguments.of("Amos Fruit Burst Peach", "810051808278", 18, 2.99));
  }

  static Stream<Arguments> inventoryManagementData() {
    return Stream.of(
        Arguments.of(
            1,
            new String[] {
              "Name: Amos Banana Peelerz\nId Number: 8-10051-80797-4\nQuantity: 12\n$2.45",
              "Name: Amos Banana Peelerz\nId Number: 8-10051-80753-0\nQuantity: 12\n$2.45",
              "Name: Push Pops Jumbo Fruit Frenzy\nId Number: 0-41116-00527-5\nQuantity: 18\n$2.45",
              "Name: Ring Pop Fruit Fest\nId Number: 0-41116-00519-0\nQuantity: 24\n$2.45",
              "Name: Juicy Drop Taffy\nId Number: 0-41116-00582-4\nQuantity: 16\n$2.45",
              "Name: Juicy Drop Pop\nId Number: 0-41116-00592-3\nQuantity: 18\n$2.45",
              "Name: Amos Banana Peelerz\nId Number: 8-10051-80798-1\nQuantity: 12\n$2.45",
              "Name: Juicy Drop Gummy Mystery Cube Original\nId Number: 0-4116-13245-2\nQuantity: 12\n$2.45",
              "Name: Juicy Drop Gummy Mystery Cube Wild Berry\nId Number: 0-4116-13247-6\nQuantity: 12\n$2.45",
              "Name: Lednessa Dubai Chocholate Cones\nId Number: 7-94711-01506-3\nQuantity: 12\n$2.45",
              "Name: Mini Taco\nId Number: 6-30855-12206-2\nQuantity: 16\n$2.45",
            }));
  }
}
