package Lecture4_interfaces_abstract_classes;

/**
 * Exception thrown when a bank account does not have enough funds
 * to complete a withdrawal transaction.
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
