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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        if (!super.equals(o)) return false;

        BankAccount that = (BankAccount) o;

        return accountNumber != null ? accountNumber.equals(that.accountNumber) : that.accountNumber == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        return result;
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
