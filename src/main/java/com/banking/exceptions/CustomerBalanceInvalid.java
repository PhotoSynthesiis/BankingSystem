package com.banking.exceptions;

public class CustomerBalanceInvalid extends Throwable {
    public CustomerBalanceInvalid(String message) {
        super(message);
    }
}
