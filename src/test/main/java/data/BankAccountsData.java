package data;

import payments.model.entity.BankAccount;

import java.math.BigDecimal;

public enum BankAccountsData {
    A(1,"31257289113853", BigDecimal.valueOf(34231.00)),
    B(2,"40348198204762", BigDecimal.valueOf(41560.50)),
    C(3,"68520916486580", BigDecimal.valueOf(89683.00));

    public BankAccount account;

    BankAccountsData(long id, String number, BigDecimal balance) {
        account = new BankAccount.Builder()
                .setId(id)
                .setAccountNumber(number)
                .setBalance(balance)
                .build();
    }
}