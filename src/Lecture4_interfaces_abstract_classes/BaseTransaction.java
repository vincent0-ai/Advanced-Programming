package Lecture4_interfaces_abstract_classes;

import org.jetbrains.annotations.NotNull;
import java.util.Calendar;

/**
 * Concrete base class implementing TransactionInterface
 */
public class BaseTransaction implements TransactionInterface {
    private final double amount;
    private final Calendar date;
    private final String transactionID;

    /**
     * Constructor for BaseTransaction
     * @param amount the transaction amount
     * @param date the transaction date, not null
     */
    public BaseTransaction(double amount, @NotNull Calendar date)  {
        this.amount = amount;
        this.date = (Calendar) date.clone();
        int uniq = (int) (Math.random() * 10000);
        this.transactionID = date.toString() + uniq;
    }

    /**
     * getAmount()
     * @return double transaction amount
     */
    @Override
    public double getAmount() {
        return amount;
    }

    /**
     * getDate()
     * @return Calendar object representing the transaction date
     */
    @Override
    public Calendar getDate() {
        return (Calendar) date.clone(); // Defensive copying
    }

    /**
     * getTransactionID()
     * @return unique identifier for the transaction
     */
    @Override
    public String getTransactionID(){
        return transactionID;
    }

    /**
     * printTransactionDetails()
     * A method to print out details of the transaction.
     */
    public void printTransactionDetails() {
        System.out.println("Base Transaction - ID: " + transactionID + ", Amount: " + amount + ", Date: " + date.getTime());
    }

    /**
     * apply()
     * A method that applies this transaction on a BankAccount object.
     * The implementation in BaseTransaction logs the operation without changing the balance,
     * differing substantially from subclasses.
     */
    public void apply(BankAccount ba) throws InsufficientFundsException {
        System.out.println("LOG: BaseTransaction apply() invoked for account balance: " + ba.getBalance());
    }
}
