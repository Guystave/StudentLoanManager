package com.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Class to represent a student loan
class StudentLoan {
    private String studentName;
    private double loanAmount;
    private double interestRate;
    private int weeksToRepay;

    public StudentLoan(String studentName, double loanAmount, double interestRate, int weeksToRepay) {
        this.studentName = studentName;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.weeksToRepay = weeksToRepay;
    }

    public double calculateTotalPayment() {
        double totalInterest = 0;
        double currentInterestRate = interestRate;
        for (int i = 0; i < weeksToRepay; i++) {
            totalInterest += loanAmount * (currentInterestRate / 100);
            if (i < 3) {
                currentInterestRate += 5; // Interest increases by 5% each week for the first 3 weeks
            } else {
                currentInterestRate = 10; // Reset interest rate to 10% after the 4th week
            }
        }
        return loanAmount + totalInterest;
    }

    // Getters and setters
    public String getStudentName() {
        return studentName;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getWeeksToRepay() {
        return weeksToRepay;
    }
}

// Class to manage student loans
class LoanManager {
    private ArrayList<StudentLoan> loans = new ArrayList<>();

    public void addLoan(StudentLoan loan) {
        loans.add(loan);
    }

    public void displayLoans() {
        for (StudentLoan loan : loans) {
            System.out.println("Student Name: " + loan.getStudentName());
            System.out.println("Loan Amount: K" + String.format("%.2f", loan.getLoanAmount())); // Formatting loan amount
            System.out.println("Interest Rate: " + loan.getInterestRate() + "% per week");
            System.out.println("Weeks to Repay: " + loan.getWeeksToRepay());
            System.out.println("Total Payment: K" + String.format("%.2f", loan.calculateTotalPayment())); // Formatting total payment
            System.out.println();
        }
    }
}

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoanManager loanManager = new LoanManager();

        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Welcome " + name + " to Julia Student Loan Manager. Do you want to proceed? (yes/no)");
        String proceed = scanner.nextLine();

        if (proceed.equalsIgnoreCase("no")) {
            System.out.println("Terminating...");
            return;
        } else if (!proceed.equalsIgnoreCase("yes")) {
            System.out.println("Invalid response. Terminating...");
            return;
        }

        System.out.println("Do you understand the interest rates? (yes/no)");
        String understand = scanner.nextLine();
        if (!understand.equalsIgnoreCase("yes")) {
            System.out.println("Terminating...");
            return;
        }

        System.out.println("Interest rates are as follows:");
        System.out.println("Week 1: 10% interest");
        System.out.println("Week 2: 15% interest");
        System.out.println("Week 3: 20% interest");
        System.out.println("Week 4: 25% interest");
        System.out.println("If it exceeds 4 weeks, interest returns to 10%.");

        double loanAmount;
        while (true) {
            try {
                System.out.println("How much do you want to borrow starting from K200 to K1000 max?");
                loanAmount = scanner.nextDouble();
                if (loanAmount < 200 || loanAmount > 1000) {
                    throw new IllegalArgumentException("Invalid loan amount.");
                }
                break; // Break the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int weeksToRepay;
        while (true) {
            try {
                System.out.println("Paying back after how many weeks?");
                weeksToRepay = scanner.nextInt();
                if (weeksToRepay <= 0) {
                    throw new IllegalArgumentException("Invalid number of weeks.");
                }
                break; // Break the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        StudentLoan loan = new StudentLoan(name, loanAmount, 10, weeksToRepay);
        loanManager.addLoan(loan);

        System.out.println("Calculating total payment...");
        System.out.println("Total payment: K" + String.format("%.2f", loan.calculateTotalPayment())); // Formatting total payment
    }
}
