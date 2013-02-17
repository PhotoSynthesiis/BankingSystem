package domain;

import exceptions.NicknameInvalidException;
import exceptions.ProperNameInvalidException;
import validator.CustomerValidator;

import java.sql.Date;

public class Customer {
    private String nickname;
    private Date dateOfBirth;
    private String properName;
    private Date joiningDate;

    public void setNickname(String nickname) throws NicknameInvalidException {
        if (CustomerValidator.isNicknameValid(nickname)) {
            this.nickname = nickname;
        } else {
            throw new NicknameInvalidException("NICKNAME SHOULD ONLY CONTAIN LOWERCASE LETTERS AND/OR WORDS");
        }
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setProperName(String properName) throws ProperNameInvalidException {
        if (CustomerValidator.isProperNameValid(properName)) {
            this.properName = properName;
        } else {
            throw new ProperNameInvalidException("PROPERNAME SHOULD ONLY CONTAIN LETTERS AND/OR SPACES");
        }
    }

    public String getProperName() {
        return properName;
    }

    @Override
    public boolean equals(Object customer) {
        if (!(customer instanceof Customer))
            return false;

        Customer customer1 = (Customer) customer;
        return (this.getNickname().equals(customer1.getNickname()) &&
                this.getDateOfBirth().equals(customer1.getDateOfBirth()) &&
                this.getProperName().equals(customer1.getProperName()) &&
                this.getJoiningDate().toString().equals(customer1.getJoiningDate().toString()));
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }
}
