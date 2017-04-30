package payments.model.entity;

import java.math.BigDecimal;

public class BankAccount extends BaseEntity {
    private String accountNumber;
    private BigDecimal balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static class Builder{
        BankAccount instance = new BankAccount();

        public Builder setId(long id){
            instance.setId(id);
            return this;
        }

        public Builder setAccountNumber(String accountNumber){
            instance.accountNumber = accountNumber;
            return this;
        }

        public Builder setBalance(BigDecimal balance){
            instance.balance = balance;
            return this;
        }

        public BankAccount build(){
            return instance;
        }
    }
}
