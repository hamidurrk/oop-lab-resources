import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class FoodOrderSystem {
    private ArrayList<String> itemNames = new ArrayList<>();
    private ArrayList<Integer> quantities = new ArrayList<>();
    private ArrayList<Double> itemTotals = new ArrayList<>();
    private double total = 0.0;

    public void addItem(String itemName, int quantity) {
        double price = 0;

        if (itemName.equalsIgnoreCase("burger")) {
            price = 5.0;
        } else if (itemName.equalsIgnoreCase("pizza")) {
            price = 8.0;
        } else if (itemName.equalsIgnoreCase("pasta")) {
            price = 7.0;
        } else if (itemName.equalsIgnoreCase("cola")) {
            price = 2.0;
        } else {
            System.out.println("Item not found.");
            return;
        }

        double itemTotal = price * quantity;
        itemNames.add(itemName);
        quantities.add(quantity);
        itemTotals.add(itemTotal);
        total += itemTotal;

        System.out.println(quantity + " " + itemName + "(s) added to order.");
    }

    public void applyDiscount(String customerType) {
        if (customerType.equalsIgnoreCase("student")) {
            total = total * 0.9;
            System.out.println("10% student discount applied.");
        } else if (customerType.equalsIgnoreCase("vip")) {
            total = total * 0.8;
            System.out.println("20% VIP discount applied.");
        } else if (customerType.equalsIgnoreCase("regular")) {
            System.out.println("No discount applied.");
        } else {
            System.out.println("Unknown customer type. No discount applied.");
        }
    }

    public void processPayment(String paymentMethod) {
        if (paymentMethod.equalsIgnoreCase("cash")) {
            System.out.println("Payment done by cash.");
        } else if (paymentMethod.equalsIgnoreCase("card")) {
            System.out.println("Payment done by card.");
        } else if (paymentMethod.equalsIgnoreCase("mobile")) {
            System.out.println("Payment done by mobile banking.");
        } else {
            System.out.println("Invalid payment method.");
        }
    }

    public void printReceipt() {
        System.out.println("\n----- RECEIPT -----");
        for (int i = 0; i < itemNames.size(); i++) {
            System.out.println("Item: " + itemNames.get(i)
                    + ", Quantity: " + quantities.get(i)
                    + ", Price: $" + itemTotals.get(i));
        }
        System.out.printf("Total Bill: $%.2f%n", total);
        System.out.println("-------------------\n");
    }

    public void saveOrder() {
        try {
            FileWriter writer = new FileWriter("orders.txt", true);
            writer.write("New Order\n");
            for (int i = 0; i < itemNames.size(); i++) {
                writer.write("Item: " + itemNames.get(i)
                        + ", Quantity: " + quantities.get(i)
                        + ", Price: $" + itemTotals.get(i) + "\n");
            }
            writer.write(String.format("Total Bill: $%.2f%n", total));
            writer.write("-------------------\n");
            writer.close();
            System.out.println("Order saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving order.");
        }
    }

    public void sendNotification() {
        System.out.println("Notification sent to customer: Your order has been confirmed.");
    }

    public void placeOrder(String customerType, String paymentMethod) {
        applyDiscount(customerType);
        processPayment(paymentMethod);
        printReceipt();
        saveOrder();
        sendNotification();
    }
}

public class App {
    public static void main(String[] args) {
        FoodOrderSystem system = new FoodOrderSystem();

        system.addItem("burger", 2);
        system.addItem("pizza", 1);
        system.addItem("cola", 3);

        system.placeOrder("student", "card");
    }
}