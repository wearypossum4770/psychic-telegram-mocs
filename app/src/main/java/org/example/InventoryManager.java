package org.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class InventoryManager {

  private final Map<String, Item> inventory = new LinkedHashMap<>();

  public boolean addItem(Item item) {
    if (item == null) return false;
    if (inventory.containsKey(item.getItemId())) return false;
    inventory.put(item.getItemId(), item);
    return true;
  }

  public Item scan(String id) {
    return inventory.get(id);
  }

  public boolean contains(String id) {
    return inventory.containsKey(id);
  }

  public boolean deleteItem(String id) {
    return inventory.remove(id) != null;
  }

  public boolean updateItemName(String id, String name) {
    Item item = inventory.get(id);
    if (item == null) return false;
    item.setName(name);
    return true;
  }

  public boolean updateItemPrice(String id, double price) {
    Item item = inventory.get(id);
    if (item == null) return false;
    item.setPrice(price);
    return true;
  }

  public boolean updateItemInventory(String id, int quantity) {
    Item item = inventory.get(id);
    if (item == null) return false;
    item.updateItemInventory(quantity);
    return true;
  }

  public boolean incrementInventory(String id, int quantity) {
    Item item = inventory.get(id);
    if (item == null) return false;
    item.incrementItemQuantity(quantity);
    return true;
  }

  public boolean decrementInventory(String id, int quantity) {
    Item item = inventory.get(id);
    if (item == null) return false;
    item.decrementItemQuantity(quantity);
    return true;
  }

  public int count() {
    return inventory.size();
  }

  public Item getFirst() {
    return inventory.values().stream().findFirst().orElse(null);
  }

  public Collection<Item> getItems() {
    return inventory.values();
  }

  public String[] printInventory() {
    String[] inventoryList = new String[inventory.size()];

    int index = 0;

    for (Item item : inventory.values()) {
      inventoryList[index++] = item.toString();
    }

    return inventoryList;
  }

  public void print() {
    Arrays.stream(printInventory()).forEach(System.out::println);
  }

  public static void main(String[] args) {
    Item[] itemList =
        new Item[] {
          new Item("Amos Banana Peelerz", "8-10051-80797-4", 12, 2.45),
          new Item("Amos Banana Peelerz", "8-10051-80753-0", 12, 2.45),
          new Item("Push Pops Jumbo Fruit Frenzy", "0-41116-00527-5", 12, 2.45),
        };
    Item first = itemList[1];
    String id = first.getItemId();

    InventoryManager manager = new InventoryManager();
    for (Item item : itemList) {
      manager.addItem(item);
    }
    manager.updateItemInventory(id, 99);
    manager.deleteItem(itemList[0].getItemId());
    manager.print();
  }
}
