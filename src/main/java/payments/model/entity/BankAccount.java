package payments.model.entity;

public class BankAccount extends BaseEntity {
    private String accountNumber;
    private long balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
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

        public Builder setBalance(long balance){
            instance.balance = balance;
            return this;
        }

        public BankAccount build(){
            return instance;
        }
    }
}
