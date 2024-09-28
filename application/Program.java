package application;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import services.ContractService;
import services.PaypalTax;

public class Program {

    public static void main(String[] args) throws ParseException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Contract> contracts = new ArrayList<>();
        ContractService contractService = new ContractService(new PaypalTax());

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Insert new contract");
            System.out.println("2. Consult payment history of a contract");
            System.out.println("3. Delete contract");
            System.out.println("0. Exit");
            int option = sc.nextInt();

            if (option == 1) {
                System.out.println("Enter the contract details:");
                System.out.print("Number: ");
                int number = sc.nextInt();
                System.out.print("Date (dd/MM/yyyy): ");
                LocalDate date = LocalDate.parse(sc.next(), fmt);
                System.out.print("Contract value: ");
                double totalValue = sc.nextDouble();
                System.out.print("Enter the number of installments: ");
                int n = sc.nextInt();

                Contract obj = new Contract(number, date, totalValue);
                contractService.processContract(obj, n);
                contracts.add(obj); // Add the new contract to the list

                System.out.println("New contract added successfully.");
            } else if (option == 2) {
                if (contracts.isEmpty()) {
                    System.out.println("No contracts found.");
                    continue;
                }

                System.out.println("Available contract numbers:");
                for (Contract c : contracts) {
                    System.out.println(c.getNumber());
                }
                System.out.print("Enter the contract number to check payment history: ");
                int contractNumberToCheck = sc.nextInt();

                Contract foundContract = null;
                for (Contract c : contracts) {
                    if (c.getNumber() == contractNumberToCheck) {
                        foundContract = c;
                        break;
                    }
                }

                if (foundContract != null) {
                    System.out.println("Payment history for contract number " + foundContract.getNumber() + ":");
                    List<Installment> installments = foundContract.getInstallments();
                    for (Installment installment : installments) {
                        System.out.println(installment);
                    }
                } else {
                    System.out.println("Contract not found.");
                }
            } else if (option == 3) {
                System.out.print("Enter the contract number to delete: ");
                int contractNumberToDelete = sc.nextInt();
                
                Contract contractToDelete = null;
                for (Contract c : contracts) {
                    if (c.getNumber() == contractNumberToDelete) {
                        contractToDelete = c;
                        break;
                    }
                }

                if (contractToDelete != null) {
                    contracts.remove(contractToDelete);
                    System.out.println("Contract deleted successfully.");
                } else {
                    System.out.println("Contract not found.");
                }
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}
