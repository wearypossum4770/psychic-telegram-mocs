package org.example;

public class Item {
  private String name;
  private final String id;
  private int quantity;
  private double price;
  private final int beginningQuantity;

  public Item(String name, String id, int quantity, double price) {
    this.name = name;
    this.id = id;
    this.quantity = quantity;
    this.beginningQuantity = quantity;
    this.price = price;
  }

  public String getItemId() {
    return id;
  }

  public String getItemName() {
    return name;
  }

  public int count() {
    return quantity;
  }

  public int size() {
    return quantity;
  }

  public int length() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }

  public double inventoryCost() {
    return quantity * price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void resetInventory() {
    quantity = beginningQuantity;
  }

  public void clearInventoryCount() {
    quantity = 0;
  }

  public void updateItemInventory(int quantity) {
    this.quantity = quantity;
  }

  public void incrementItemQuantity(int amount) {
    quantity += amount;
  }

  public void decrementItemQuantity(int amount) {
    quantity -= amount;
  }

  public boolean selectItem(String id) {
    return this.id.equals(id);
  }

  @Override
  public String toString() {
    return String.format(
        "Name: %s\nId Number: %s\nQuantity: %d\n$%,.2f", name, id, quantity, price);
  }
}
