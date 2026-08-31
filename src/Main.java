import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main {

    static final int MAX_PRODUCTS = 100;

    static int findProductIndex(int[] productCodes, int code, int productCount) {
        for (int i = 0; i < productCount; i++) {
            if (productCodes[i] == code) {
                return i;
            }
        }
        return -1;
    }

    static int addProduct(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int[] soldQuantities,
            int productCount,
            Scanner scanner) {

        if (productCount >= MAX_PRODUCTS) {
            System.out.println("Error: Store is full! Cannot add more products.");
            return productCount;
        }

        System.out.print("Enter product code: ");
        int code = scanner.nextInt();

        int index = findProductIndex(productCodes, code, productCount);

        if (index != -1) {
            System.out.println("Error: Product code already exists!");
            return productCount;
        }

        scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        if (name.isEmpty()) {
            System.out.println("Error: Product name cannot be empty!");
            return productCount;
        }

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        if (price <= 0) {
            System.out.println("Error: Price must be greater than 0!");
            return productCount;
        }

        System.out.print("Enter stock quantity: ");
        int stock = scanner.nextInt();

        if (stock < 0) {
            System.out.println("Error: Stock quantity cannot be negative!");
            return productCount;
        }

        productCodes[productCount] = code;
        productNames[productCount] = name;
        prices[productCount] = price;
        stockQuantities[productCount] = stock;
        soldQuantities[productCount] = 0;

        productCount++;

        System.out.println("Product added successfully!");

        return productCount;
    }

    static void displayAllProducts(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int[] soldQuantities,
            int productCount) {

        if (productCount == 0) {
            System.out.println("No products in the system.");
            return;
        }

        System.out.println();
        System.out.println("===== ALL PRODUCTS =====");

        System.out.printf(
                "%-8s %-20s %10s %10s %10s%n",
                "Code",
                "Name",
                "Price",
                "Stock",
                "Sold"
        );

        System.out.println("============================================================");

        for (int i = 0; i < productCount; i++) {
            System.out.printf(
                    "%-8d %-20s %10.2f %10d %10d%n",
                    productCodes[i],
                    productNames[i],
                    prices[i],
                    stockQuantities[i],
                    soldQuantities[i]
            );
        }

        System.out.println("============================================================");
        System.out.println("Total Products: " + productCount);
    }

    static void sellProduct(
            int[] productCodes,
            int[] stockQuantities,
            int[] soldQuantities,
            double[] prices,
            String[] productNames,
            int productCount,
            Scanner scanner) {

        System.out.print("Enter product code: ");
        int code = scanner.nextInt();

        int index = findProductIndex(productCodes, code, productCount);

        if (index == -1) {
            System.out.println("Error: Product not found!");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than 0!");
            return;
        }

        if (quantity > stockQuantities[index]) {
            System.out.println(
                    "Error: Only " + stockQuantities[index]
                            + " units available in stock!"
            );
            return;
        }

        double total = prices[index] * quantity;

        stockQuantities[index] -= quantity;
        soldQuantities[index] += quantity;

        System.out.println("Sale successful!");
        System.out.printf("Total Price: $%.2f%n", total);
        System.out.println(
                "Remaining Stock: "
                        + stockQuantities[index]
                        + " units"
        );
    }

    static void restockProduct(
            int[] productCodes,
            int[] stockQuantities,
            int productCount,
            Scanner scanner) {

        System.out.print("Enter product code: ");
        int code = scanner.nextInt();

        int index = findProductIndex(productCodes, code, productCount);

        if (index == -1) {
            System.out.println("Error: Product not found!");
            return;
        }

        System.out.print("Enter quantity to add: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Error: Quantity must be greater than 0!");
            return;
        }

        stockQuantities[index] += quantity;

        System.out.println(
                "Product restocked successfully! New stock: "
                        + stockQuantities[index]
                        + " units"
        );
    }

    static void searchProductByCode(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int[] soldQuantities,
            int productCount,
            Scanner scanner) {

        if (productCount == 0) {
            System.out.println("No products in the system.");
            return;
        }

        System.out.print("Enter product code: ");
        int code = scanner.nextInt();

        int index = findProductIndex(productCodes, code, productCount);

        if (index == -1) {
            System.out.println("Error: Product not found!");
            return;
        }

        double totalRevenue = prices[index] * soldQuantities[index];

        System.out.println();
        System.out.println("Product Found:");
        System.out.println("==============================");
        System.out.println("Code: " + productCodes[index]);
        System.out.println("Name: " + productNames[index]);
        System.out.printf("Price: $%.2f%n", prices[index]);
        System.out.println("Stock: " + stockQuantities[index] + " units");
        System.out.println("Sold: " + soldQuantities[index] + " units");

        System.out.printf(
                "Total Revenue from this product: $%.2f%n",
                totalRevenue
        );

        System.out.println("==============================");
    }

    static void showLowStockAlert(
            int[] productCodes,
            String[] productNames,
            int[] stockQuantities,
            int productCount) {

        if (productCount == 0) {
            System.out.println("No products in the system.");
            return;
        }

        int lowStockCount = 0;

        System.out.println();
        System.out.println("===== LOW STOCK ALERT =====");

        System.out.printf(
                "%-8s %-20s %10s%n",
                "Code",
                "Name",
                "Stock"
        );

        System.out.println("==========================================");

        for (int i = 0; i < productCount; i++) {
            if (stockQuantities[i] < 5) {
                System.out.printf(
                        "%-8d %-20s %10d%n",
                        productCodes[i],
                        productNames[i],
                        stockQuantities[i]
                );

                lowStockCount++;
            }
        }

        if (lowStockCount == 0) {
            System.out.println("All products are well stocked!");
        } else {
            System.out.println(
                    "Total Low Stock Items: " + lowStockCount
            );
        }
    }

    static void calculateInventoryValue(
            double[] prices,
            int[] stockQuantities,
            int productCount) {

        if (productCount == 0) {
            System.out.println("No products in the system.");
            return;
        }

        double totalValue = 0;

        for (int i = 0; i < productCount; i++) {
            totalValue += prices[i] * stockQuantities[i];
        }

        System.out.printf(
                "Total Inventory Value: $%,.2f%n",
                totalValue
        );
    }

    static void showSalesReport(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] soldQuantities,
            int productCount) {

        if (productCount == 0) {
            System.out.println("No sales recorded yet.");
            return;
        }

        int totalUnitsSold = 0;
        double totalRevenue = 0;

        for (int i = 0; i < productCount; i++) {
            totalUnitsSold += soldQuantities[i];
            totalRevenue += prices[i] * soldQuantities[i];
        }

        if (totalUnitsSold == 0) {
            System.out.println("No sales recorded yet.");
            return;
        }

        double averageSaleValue = totalRevenue / totalUnitsSold;

        System.out.println();
        System.out.println("SALES REPORT");
        System.out.println("============================================================");

        System.out.println(
                "Total Units Sold: " + totalUnitsSold + " units"
        );

        System.out.printf(
                "Total Revenue: $%,.2f%n",
                totalRevenue
        );

        System.out.printf(
                "Average Sale Value: $%,.2f%n",
                averageSaleValue
        );

        System.out.println();
        System.out.println("Product-wise Sales:");

        for (int i = 0; i < productCount; i++) {

            double productRevenue =
                    prices[i] * soldQuantities[i];

            System.out.printf(
                    "%d. %s (Code: %d): %d units sold, Revenue: $%,.2f%n",
                    i + 1,
                    productNames[i],
                    productCodes[i],
                    soldQuantities[i],
                    productRevenue
            );
        }

        System.out.println("============================================================");
    }

    static void showBestSellingProduct(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] soldQuantities,
            int productCount) {

        if (productCount == 0) {
            System.out.println("No sales recorded yet.");
            return;
        }

        int maxIndex = 0;

        for (int i = 1; i < productCount; i++) {
            if (soldQuantities[i] > soldQuantities[maxIndex]) {
                maxIndex = i;
            }
        }

        if (soldQuantities[maxIndex] == 0) {
            System.out.println("No sales recorded yet.");
            return;
        }

        double revenueGenerated =
                prices[maxIndex] * soldQuantities[maxIndex];

        System.out.println();
        System.out.println("Best Selling Product");
        System.out.println("==============================");

        System.out.println("Code: " + productCodes[maxIndex]);
        System.out.println("Name: " + productNames[maxIndex]);

        System.out.printf(
                "Price: $%.2f%n",
                prices[maxIndex]
        );

        System.out.println(
                "Units Sold: " + soldQuantities[maxIndex]
        );

        System.out.printf(
                "Revenue Generated: $%.2f%n",
                revenueGenerated
        );

        System.out.println("==============================");
    }

    static int deleteProduct(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int[] soldQuantities,
            int productCount,
            Scanner scanner) {

        if (productCount == 0) {
            System.out.println("Error: No products in the system to delete!");
            return productCount;
        }

        System.out.print("Enter product code to delete: ");
        int code = scanner.nextInt();

        int index = findProductIndex(productCodes, code, productCount);

        if (index == -1) {
            System.out.println("Error: Product not found!");
            return productCount;
        }

        String deletedName = productNames[index];

        int lastIndex = productCount - 1;

        productCodes[index] = productCodes[lastIndex];
        productNames[index] = productNames[lastIndex];
        prices[index] = prices[lastIndex];
        stockQuantities[index] = stockQuantities[lastIndex];
        soldQuantities[index] = soldQuantities[lastIndex];

        productCount--;

        System.out.println("Product '" + deletedName + "' deleted successfully!");

        return productCount;
    }

    static void updateProduct(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int productCount,
            Scanner scanner) {

        if (productCount == 0) {
            System.out.println("Error: No products in the system to update!");
            return;
        }

        System.out.print("Enter product code to update: ");
        int code = scanner.nextInt();

        int index = findProductIndex(productCodes, code, productCount);

        if (index == -1) {
            System.out.println("Error: Product not found!");
            return;
        }

        System.out.println("Current details:");
        System.out.println("Name: " + productNames[index]);
        System.out.printf("Price: $%.2f%n", prices[index]);
        System.out.println("Stock: " + stockQuantities[index]);

        scanner.nextLine();
        System.out.print("Enter new name (or press Enter to keep current): ");
        String newName = scanner.nextLine();

        if (!newName.trim().isEmpty()) {
            productNames[index] = newName;
        }

        System.out.print("Enter new price (or -1 to keep current): ");
        double newPrice = scanner.nextDouble();

        if (newPrice > 0) {
            prices[index] = newPrice;
        } else if (newPrice != -1) {
            System.out.println("Error: Price must be greater than 0! Keeping old price.");
        }

        System.out.print("Enter new stock (or -1 to keep current): ");
        int newStock = scanner.nextInt();

        if (newStock >= 0) {
            stockQuantities[index] = newStock;
        } else if (newStock != -1) {
            System.out.println("Error: Stock cannot be negative! Keeping old stock.");
        }

        System.out.println("Product updated successfully!");
    }

    static void saveProductsToFile(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int[] soldQuantities,
            int productCount) {

        try {
            FileWriter writer = new FileWriter("products.txt");

            for (int i = 0; i < productCount; i++) {
                writer.write(
                        productCodes[i] + "," +
                                productNames[i] + "," +
                                prices[i] + "," +
                                stockQuantities[i] + "," +
                                soldQuantities[i] + "\n"
                );
            }

            writer.close();
            System.out.println("Products saved successfully to products.txt");

        } catch (IOException e) {
            System.out.println("Error: Could not save products to file!");
        }
    }

    static int loadProductsFromFile(
            int[] productCodes,
            String[] productNames,
            double[] prices,
            int[] stockQuantities,
            int[] soldQuantities) {

        int productCount = 0;

        File file = new File("products.txt");

        if (!file.exists()) {
            System.out.println("No saved data found. Starting with an empty store.");
            return productCount;
        }

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine() && productCount < MAX_PRODUCTS) {
                String line = fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");

                productCodes[productCount] = Integer.parseInt(parts[0]);
                productNames[productCount] = parts[1];
                prices[productCount] = Double.parseDouble(parts[2]);
                stockQuantities[productCount] = Integer.parseInt(parts[3]);
                soldQuantities[productCount] = Integer.parseInt(parts[4]);

                productCount++;
            }

            fileScanner.close();
            System.out.println("Loaded " + productCount + " product(s) from products.txt");

        } catch (Exception e) {
            System.out.println("Error: Could not read saved data. Starting with an empty store.");
        }

        return productCount;
    }

    public static void main(String[] args) {

        int[] productCodes = new int[MAX_PRODUCTS];
        String[] productNames = new String[MAX_PRODUCTS];
        double[] prices = new double[MAX_PRODUCTS];
        int[] stockQuantities = new int[MAX_PRODUCTS];
        int[] soldQuantities = new int[MAX_PRODUCTS];

        int productCount = loadProductsFromFile(
                productCodes,
                productNames,
                prices,
                stockQuantities,
                soldQuantities
        );

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println();
            System.out.println("===== STORE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add New Product");
            System.out.println("2. Display All Products");
            System.out.println("3. Sell Product");
            System.out.println("4. Restock Product");
            System.out.println("5. Search Product by Code");
            System.out.println("6. Show Low Stock Alert (quantity < 5)");
            System.out.println("7. Calculate Total Inventory Value");
            System.out.println("8. Show Sales Report");
            System.out.println("9. Show Best Selling Product");
            System.out.println("10. Delete Product");
            System.out.println("11. Update Product");
            System.out.println("0. Exit");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    productCount = addProduct(
                            productCodes,
                            productNames,
                            prices,
                            stockQuantities,
                            soldQuantities,
                            productCount,
                            scanner
                    );
                    break;

                case 2:
                    displayAllProducts(
                            productCodes,
                            productNames,
                            prices,
                            stockQuantities,
                            soldQuantities,
                            productCount
                    );
                    break;

                case 3:
                    sellProduct(
                            productCodes,
                            stockQuantities,
                            soldQuantities,
                            prices,
                            productNames,
                            productCount,
                            scanner
                    );
                    break;

                case 4:
                    restockProduct(
                            productCodes,
                            stockQuantities,
                            productCount,
                            scanner
                    );
                    break;

                case 5:
                    searchProductByCode(
                            productCodes,
                            productNames,
                            prices,
                            stockQuantities,
                            soldQuantities,
                            productCount,
                            scanner
                    );
                    break;

                case 6:
                    showLowStockAlert(
                            productCodes,
                            productNames,
                            stockQuantities,
                            productCount
                    );
                    break;

                case 7:
                    calculateInventoryValue(
                            prices,
                            stockQuantities,
                            productCount
                    );
                    break;

                case 8:
                    showSalesReport(
                            productCodes,
                            productNames,
                            prices,
                            soldQuantities,
                            productCount
                    );
                    break;

                case 9:
                    showBestSellingProduct(
                            productCodes,
                            productNames,
                            prices,
                            soldQuantities,
                            productCount
                    );
                    break;

                case 10:
                    productCount = deleteProduct(
                            productCodes,
                            productNames,
                            prices,
                            stockQuantities,
                            soldQuantities,
                            productCount,
                            scanner
                    );
                    break;

                case 11:
                    updateProduct(
                            productCodes,
                            productNames,
                            prices,
                            stockQuantities,
                            productCount,
                            scanner
                    );
                    break;

                case 0:
                    saveProductsToFile(
                            productCodes,
                            productNames,
                            prices,
                            stockQuantities,
                            soldQuantities,
                            productCount
                    );
                    System.out.println("مع السلامة!");
                    break;

                default:
                    System.out.println("Error: Invalid choice!");
            }

        } while (choice != 0);

        scanner.close();
    }
}