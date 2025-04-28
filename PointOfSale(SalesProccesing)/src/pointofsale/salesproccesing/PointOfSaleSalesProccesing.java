package pointofsale.salesproccesing;

import java.util.*;

public class PointOfSaleSalesProccesing {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> itemName = new ArrayList<String>();
        ArrayList<Double> itemPrice = new ArrayList<Double>();
        double discount = 0.0;

        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Apply Discount");
            System.out.println("4. Print Recipt");
            System.out.println("5. View Items");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            System.out.println("----------------------------------------");
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter item price: ");
                    double price = scanner.nextDouble();
                    itemName.add(name);
                    itemPrice.add(price);
                    System.out.println("----------------------------------------");
                    System.out.println("Item Succesfully Added!");
                    System.out.println("----------------------------------------");
                    break;
                case 2:
                    System.out.print("Enter Item Name to remove: ");
                    String removeItem = scanner.nextLine();
                    int index = itemName.indexOf(removeItem);
                    if (index != -1) {
                        itemName.remove(index);
                        itemPrice.remove(index);
                        System.out.println("Item Has been Removed!");
                        System.out.println("----------------------------------------");
                    } else {
                        System.out.println("Item not found!");
                        System.out.println("----------------------------------------");
                    }
                    break;

                case 3:
                    System.out.print("Enter Discount: ");
                    discount = scanner.nextDouble();
                    System.out.println("----------------------------------------");
                    break;

                case 4:
                     double total = 0;
                    System.out.println("--- Receipt ---");
                    for (int i = 0; i < itemName.size(); i++) {
                        System.out.println(itemName.get(i) + " - php" + itemPrice.get(i));
                        total += itemPrice.get(i);
                    }
                    if (discount > 0) {
                        double discountAmount = total * (discount / 100);
                        total -= discountAmount;
                        System.out.println("Discount: " + discount + "% (-php" + discountAmount + ")");
                    }
                    System.out.println("Total: php " + total);
                    System.out.println("---------------");
                    break;
                    
                case 5:
                    System.out.println("Added Items:");
                    if (itemName.isEmpty()) {
                        System.out.println("No Items...");
                    } else {
                        for (int i = 0; i < itemName.size(); i++) {
                            System.out.println(" " + (i + 1) + ". " + itemName.get(i) + "- php" + itemPrice.get(i));
                        }
                    }
                    break;
                case 6:
                    System.out.println("Goodbye");
                    return;

                default:
                    System.out.println("Invalid Option, Try again...");
            }

        }

    }
}
