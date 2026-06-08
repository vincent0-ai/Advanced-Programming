package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

/**
 * Concrete class representing a Withdrawal Transaction
 */
public class WithdrawalTransaction extends BaseTransaction {
    private BankAccount appliedAccount;
    private double actualWithdrawnAmount = 0.0;
    private double amountNotWithdrawn = 0.0;
    private boolean reversed = false;
    private boolean succeeded = false;

    public WithdrawalTransaction(double amount, @NotNull Calendar date) {
        super(amount, date);
    }

    private boolean checkWithdrawalAmount(double amt) {
        return amt >= 0;
    }

    /**
     * Method to reverse the transaction.
     * Restores the balance in the BankAccount object that the transaction was initially applied to
     * to its original amount.
     * @return true if reversal was successful, false otherwise
     */
    public boolean reverse() {
        if (!succeeded) {
            System.out.println("Reversal failed: Transaction was not successfully applied.");
            return false;
        }
        if (reversed) {
            System.out.println("Reversal failed: Transaction has already been reversed.");
            return false;
        }
        if (appliedAccount == null) {
            System.out.println("Reversal failed: No bank account associated with this transaction.");
            return false;
        }

        double curr_balance = appliedAccount.getBalance();
        appliedAccount.setBalance(curr_balance + actualWithdrawnAmount);
        reversed = true;
        System.out.println("Reversal successful. Restored " + actualWithdrawnAmount + " to the account. New balance: " + appliedAccount.getBalance());
        return true;
    }

    @Override
    public void printTransactionDetails() {
        System.out.println("Withdrawal Transaction - ID: " + getTransactionID() + 
                           ", Target Amount: " + getAmount() + 
                           ", Date: " + getDate().getTime() + 
                           ", Actual Withdrawn: " + actualWithdrawnAmount + 
                           ", Amount Not Withdrawn: " + amountNotWithdrawn + 
                           ", Succeeded: " + succeeded + 
                           ", Reversed: " + reversed);
    }

    @Override
    public void apply(BankAccount ba) throws InsufficientFundsException {
        if (!checkWithdrawalAmount(getAmount())) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative.");
        }
        double curr_balance = ba.getBalance();
        if (curr_balance < getAmount()) {
            throw new InsufficientFundsException("Insufficient funds: balance is " + curr_balance + ", withdrawal amount is " + getAmount());
        }
        double new_balance = curr_balance - getAmount();
        ba.setBalance(new_balance);
        this.appliedAccount = ba;
        this.actualWithdrawnAmount = getAmount();
        this.amountNotWithdrawn = 0.0;
        this.succeeded = true;
        System.out.println("Withdrawal transaction applied. Amount: " + getAmount() + ", New Balance: " + new_balance);
    }

    /**
     * Overloaded apply method that checks if the balance covers the withdrawal amount,
     * checks if the balance is greater than 0, and if 0 < balance < withdrawal amount,
     * withdraws all the balance, keeping a record of the amount not withdrawn.
     * Uses try-catch-finally to handle InsufficientFundsException.
     */
    public void apply(BankAccount ba, boolean allowPartial) {
        try {
            if (!checkWithdrawalAmount(getAmount())) {
                throw new IllegalArgumentException("Withdrawal amount cannot be negative.");
            }
            double curr_balance = ba.getBalance();
            if (curr_balance <= 0) {
                throw new InsufficientFundsException("Insufficient funds: balance is " + curr_balance + " (cannot perform partial/full withdrawal).");
            }

            if (curr_balance < getAmount()) {
                // Case when 0 < balance < withdrawal amount
                double withdrawn = curr_balance;
                double remaining = getAmount() - withdrawn;
                ba.setBalance(0.0);
                this.appliedAccount = ba;
                this.actualWithdrawnAmount = withdrawn;
                this.amountNotWithdrawn = remaining;
                this.succeeded = true;
                System.out.println("Partial withdrawal applied. Withdrew: " + withdrawn + ", Remaining (not withdrawn): " + remaining);
            } else {
                // Normal full withdrawal
                apply(ba);
            }
        } catch (InsufficientFundsException e) {
            System.err.println("Exception caught during partial withdrawal: " + e.getMessage());
        } finally {
            System.out.println("Partial withdrawal attempt completed. Current account balance: " + ba.getBalance());
        }
    }
}
