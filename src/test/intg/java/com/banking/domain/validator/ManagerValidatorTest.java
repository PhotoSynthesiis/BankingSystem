package com.banking.domain.validator;

import com.banking.domain.entity.Customer;
import com.banking.exceptions.CustomerBalanceInvalid;
import com.banking.exceptions.NicknameInvalidException;
import com.banking.exceptions.ProperNameInvalidException;
import org.junit.Test;

import java.sql.Date;

public class ManagerValidatorTest {
    @Test
    public void shouldNotGiveBonusToCustomerWhoHaveNoMoneyInAccount() throws NicknameInvalidException, ProperNameInvalidException, CustomerBalanceInvalid {
        String nickname = "zhangmos";
        Date dateOfBirth = new Date(Date.valueOf("1989-12-25").getTime());
        String properName = "Zhang Linpeng";
        double balance = 100.00;

        Customer customer = new Customer();
        customer.setNickname(nickname);
        customer.setDateOfBirth(dateOfBirth);
        customer.setProperName(properName);
        customer.setBalance(balance);
    }
}
