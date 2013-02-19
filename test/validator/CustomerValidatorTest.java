package validator;

import domain.Customer;
import exceptions.CustomerBalanceInvalid;
import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CustomerValidatorTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenTryToSetCustomerNicknameWithInvalidSpecialCharacters() throws NicknameInvalidException {
        expectedException.expect(NicknameInvalidException.class);

        String nickname = "a-b-c";
        Customer customer = new Customer();
        customer.setNickname(nickname);
    }

    @Test
    public void shouldThrowExceptionWhenTryToSetCustomerNicknameWithInvalidCharactersOtherThanLowercaseLettersOrNumbers() throws NicknameInvalidException {
        expectedException.expect(NicknameInvalidException.class);

        String nickname = "1abcD";
        Customer customer = new Customer();
        customer.setNickname(nickname);
    }

    @Test
    public void shouldSetCustomerNicknameSuccessfullyWithValidCharacters() throws NicknameInvalidException {
        String nickname = "raul";
        Customer customer = new Customer();
        customer.setNickname(nickname);
    }

    @Test
    public void shouldThrowExceptionWhenTryToSetCustomerProperNameWithInvalidSpecialCharacters() throws ProperNameInvalidException {
        expectedException.expect(ProperNameInvalidException.class);
        String properName = "a-b-c";
        Customer customer = new Customer();
        customer.setProperName(properName);
    }

    @Test
    public void shouldThrowExceptionWhenTryToSetCustomerProperNameWithInvalidCharactersOtherThanLettersOrSpace() throws ProperNameInvalidException {
        expectedException.expect(ProperNameInvalidException.class);
        String properName = "123";
        Customer customer = new Customer();
        customer.setProperName(properName);
    }

    @Test
    public void shouldAddProperNameSuccessfully() throws ProperNameInvalidException {
        String properName = "Van Persie";
        Customer customer = new Customer();
        customer.setProperName(properName);
    }

    @Test
    public void shouldSetBalanceSuccessfully() throws ProperNameInvalidException, CustomerBalanceInvalid {
        double money = 100;
        Customer customer = new Customer();
        customer.setBalance(money);
    }

    @Test
    public void shouldNotSetBalanceSuccessfully() throws ProperNameInvalidException, CustomerBalanceInvalid {
        expectedException.expect(CustomerBalanceInvalid.class);
        double money = -100;
        Customer customer = new Customer();
        customer.setBalance(money);
    }

    @Test
    public void shouldThrowExceptionWhenTryToWithdrawWithInvalidAmountOfMoney() throws CustomerBalanceInvalid {
        expectedException.expect(CustomerBalanceInvalid.class);

        double money = -100;
        Customer customer = new Customer();
        customer.withdraw(money);
    }
}
