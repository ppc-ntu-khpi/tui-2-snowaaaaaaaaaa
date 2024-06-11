package com.mybank.tui;

import com.mybank.domain.Bank;
import com.mybank.domain.CheckingAccount;
import com.mybank.domain.Customer;
import com.mybank.domain.SavingsAccount;
import java.util.LinkedList;
import java.util.List;

import org.jline.reader.*;
import org.jline.reader.impl.completer.*;
import org.jline.utils.*;
import org.fusesource.jansi.*;

/**
 * Console client for 'Banking' example
 * 
 * Author: Alexander 'Taurus' Babich
 */
public class CLIdemo {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private String[] commandsList;

    public void init() {
        commandsList = new String[]{"help", "customers", "customer", "report", "exit"};
    }

    public void run() {
        AnsiConsole.systemInstall(); // needed to support ansi on Windows cmd
        printWelcomeMessage();
        LineReaderBuilder readerBuilder = LineReaderBuilder.builder();
        List<Completer> completors = new LinkedList<>();

        completors.add(new StringsCompleter(commandsList));
        readerBuilder.completer(new ArgumentCompleter(completors));

        LineReader reader = readerBuilder.build();

        String line;

        while ((line = readLine(reader, "")) != null) {
            if ("help".equals(line)) {
                printHelp();
            } else if ("customers".equals(line)) {
                printCustomers();
            } else if (line.startsWith("customer")) {
                printCustomerDetails(line);
            } else if ("report".equals(line)) {
                printCustomerReport();
            } else if ("exit".equals(line)) {
                System.out.println("Exiting application");
                return;
            } else {
                System.out.println(ANSI_RED + "Invalid command, For assistance press TAB or type \"help\" then hit ENTER." + ANSI_RESET);
            }
        }

        AnsiConsole.systemUninstall();
    }

    private void printWelcomeMessage() {
        System.out.println("\nWelcome to " + ANSI_GREEN + " MyBank Console Client App" + ANSI_RESET + "! \nFor assistance press TAB or type \"help\" then hit ENTER.");
    }

    private void printHelp() {
        System.out.println("help\t\t\t- Show help");
        System.out.println("customers\t\t- Show list of customers");
        System.out.println("customer 'index'\t- Show customer details");
        System.out.println("report\t\t\t- Show customer report");
        System.out.println("exit\t\t\t- Exit the app");
    }

    private String readLine(LineReader reader, String promtMessage) {
        try {
            String line = reader.readLine(promtMessage + ANSI_YELLOW + "\nbank> " + ANSI_RESET);
            return line.trim();
        } catch (UserInterruptException | EndOfFileException e) {
            return null;
        }
    }

    private void printCustomers() {
        AttributedStringBuilder a = new AttributedStringBuilder()
                .append("\nThis is all of your ")
                .append("customers", AttributedStyle.BOLD.foreground(AttributedStyle.RED))
                .append(":");

        System.out.println(a.toAnsi());
        if (Bank.getNumberOfCustomers() > 0) {
            System.out.println("\nLast name\tFirst Name\tBalance");
            System.out.println("---------------------------------------");
            for (int i = 0; i < Bank.getNumberOfCustomers(); i++) {
                Customer customer = Bank.getCustomer(i);
                System.out.println(customer.getLastName() + "\t\t" + customer.getFirstName() + "\t\t$" + customer.getAccount(0).getBalance());
            }
        } else {
            System.out.println(ANSI_RED + "Your bank has no customers!" + ANSI_RESET);
        }
    }

    private void printCustomerDetails(String line) {
        try {
            String[] parts = line.split(" ");
            if (parts.length > 1) {
                int custNo = Integer.parseInt(parts[1]);
                Customer cust = Bank.getCustomer(custNo);
                String accType = cust.getAccount(0) instanceof CheckingAccount ? "Checking" : "Savings";

                AttributedStringBuilder a = new AttributedStringBuilder()
                        .append("\nThis is detailed information about customer #")
                        .append(Integer.toString(custNo), AttributedStyle.BOLD.foreground(AttributedStyle.RED))
                        .append("!");

                System.out.println(a.toAnsi());

                System.out.println("\nLast name\tFirst Name\tAccount Type\tBalance");
                System.out.println("-------------------------------------------------------");
                System.out.println(cust.getLastName() + "\t\t" + cust.getFirstName() + "\t\t" + accType + "\t$" + cust.getAccount(0).getBalance());
            } else {
                System.out.println(ANSI_RED + "ERROR! Customer number is missing!" + ANSI_RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED + "ERROR! Wrong customer number!" + ANSI_RESET);
        }
    }

    private void printCustomerReport() {
          System.out.println("Customer Report:");
        for (int i = 0; i < Bank.getNumberOfCustomers(); i++) {
            Customer cust = Bank.getCustomer(i);
            String accType = cust.getAccount(0) instanceof CheckingAccount ? "Checking" : "Savings";
            System.out.println("\nCustomer: " + cust.getLastName() + ", " + cust.getFirstName());
            System.out.println("Account Type: " + accType);
            System.out.println("Balance: $" + cust.getAccount(0).getBalance());
        }
    }

    public static void main(String[] args) {
        Bank.addCustomer("John", "Doe");
        Bank.addCustomer("Fox", "Mulder");
        Bank.getCustomer(0).addAccount(new CheckingAccount(2000));
        Bank.getCustomer(1).addAccount(new SavingsAccount(1000, 3));

        CLIdemo shell = new CLIdemo();
        shell.init();
        shell.run();
    }
}