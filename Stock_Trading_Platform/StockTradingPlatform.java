import java.util.*;
import java.io.*;

class Stock {
    String symbol;
    String companyName;
    double price;

    Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }

    void displayStock() {
        System.out.println(symbol + " - " + companyName + " : ₹" + price);
    }
}

class Transaction {
    String type;
    String stockSymbol;
    int quantity;
    double price;

    Transaction(String type, String stockSymbol, int quantity, double price) {
        this.type = type;
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
    }

    void displayTransaction() {
        System.out.println(type + " | " + stockSymbol +
                " | Qty: " + quantity +
                " | Price: ₹" + price);
    }
}

class Portfolio {

    HashMap<String, Integer> holdings = new HashMap<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    void buyStock(String symbol, int quantity, double price) {

        holdings.put(symbol,
                holdings.getOrDefault(symbol, 0) + quantity);

        transactions.add(new Transaction(
                "BUY", symbol, quantity, price));

        System.out.println("Stock Purchased Successfully!");
    }

    void sellStock(String symbol, int quantity, double price) {

        if (!holdings.containsKey(symbol)) {
            System.out.println("Stock not found in portfolio.");
            return;
        }

        int currentQty = holdings.get(symbol);

        if (quantity > currentQty) {
            System.out.println("Not enough shares to sell.");
            return;
        }

        holdings.put(symbol, currentQty - quantity);

        if (holdings.get(symbol) == 0) {
            holdings.remove(symbol);
        }

        transactions.add(new Transaction(
                "SELL", symbol, quantity, price));

        System.out.println("Stock Sold Successfully!");
    }

    void displayPortfolio() {

        System.out.println("\n===== PORTFOLIO =====");

        if (holdings.isEmpty()) {
            System.out.println("No stocks owned.");
            return;
        }

        for (String symbol : holdings.keySet()) {
            System.out.println(symbol +
                    " -> Shares: " + holdings.get(symbol));
        }
    }

    void displayTransactions() {

        System.out.println("\n===== TRANSACTIONS =====");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (Transaction t : transactions) {
            t.displayTransaction();
        }
    }

    void savePortfolio() {

        try {
            FileWriter writer = new FileWriter("portfolio.txt");

            for (String symbol : holdings.keySet()) {
                writer.write(symbol + "," +
                        holdings.get(symbol) + "\n");
            }

            writer.close();

            System.out.println("Portfolio saved successfully!");

        } catch (Exception e) {
            System.out.println("Error saving file.");
        }
    }
}

class User {

    String name;
    Portfolio portfolio;

    User(String name) {
        this.name = name;
        portfolio = new Portfolio();
    }
}

public class StockTradingPlatform {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Market Stocks
        ArrayList<Stock> market = new ArrayList<>();

        market.add(new Stock("TCS", "Tata Consultancy Services", 3500));
        market.add(new Stock("INFY", "Infosys", 1450));
        market.add(new Stock("RELIANCE", "Reliance Industries", 2800));
        market.add(new Stock("HDFC", "HDFC Bank", 1700));

        System.out.print("Enter User Name: ");
        String userName = sc.nextLine();

        User user = new User(userName);

        int choice;

        do {

            System.out.println("\n===== STOCK TRADING MENU =====");

            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("6. Save Portfolio");
            System.out.println("7. Exit");

            System.out.print("Enter Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("\n===== MARKET DATA =====");

                    for (Stock s : market) {
                        s.displayStock();
                    }

                    break;

                case 2:

                    System.out.print("Enter Stock Symbol: ");
                    String buySymbol = sc.next();

                    System.out.print("Enter Quantity: ");
                    int buyQty = sc.nextInt();

                    boolean foundBuy = false;

                    for (Stock s : market) {

                        if (s.symbol.equalsIgnoreCase(buySymbol)) {

                            user.portfolio.buyStock(
                                    s.symbol,
                                    buyQty,
                                    s.price);

                            foundBuy = true;
                        }
                    }

                    if (!foundBuy) {
                        System.out.println("Stock not found.");
                    }

                    break;

                case 3:

                    System.out.print("Enter Stock Symbol: ");
                    String sellSymbol = sc.next();

                    System.out.print("Enter Quantity: ");
                    int sellQty = sc.nextInt();

                    boolean foundSell = false;

                    for (Stock s : market) {

                        if (s.symbol.equalsIgnoreCase(sellSymbol)) {

                            user.portfolio.sellStock(
                                    s.symbol,
                                    sellQty,
                                    s.price);

                            foundSell = true;
                        }
                    }

                    if (!foundSell) {
                        System.out.println("Stock not found.");
                    }

                    break;

                case 4:

                    user.portfolio.displayPortfolio();

                    break;

                case 5:

                    user.portfolio.displayTransactions();

                    break;

                case 6:

                    user.portfolio.savePortfolio();

                    break;

                case 7:

                    System.out.println("Thank You!");

                    break;

                default:

                    System.out.println("Invalid Choice");
            }

        } while (choice != 7);

        sc.close();
    }
}
