package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

/**
 * Concrete class representing a Deposit Transaction
 */
public class DepositTransaction extends BaseTransaction {
    public DepositTransaction(double amount, @NotNull Calendar date){
        super(amount, date);
    }

    private boolean checkDepositAmount(double amt){
        return amt >= 0;
    }

    // Method to print a transaction receipt or details
    @Override
    public void printTransactionDetails(){
        System.out.println("Deposit Transaction - ID: " + getTransactionID() + ", Amount: " + getAmount() + ", Date: " + getDate().getTime());
    }

    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        if (!checkDepositAmount(getAmount())) {
            throw new IllegalArgumentException("Deposit amount cannot be negative.");
        }
        double curr_balance = ba.getBalance();
        double new_balance = curr_balance + getAmount();
        ba.setBalance(new_balance);
        System.out.println("Deposit transaction applied. Amount: " + getAmount() + ", New Balance: " + new_balance);
    }
}
