package payments.model.entity;

public class BancAccount extends BaseEntity {
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

    public BancAccount() {
    }

    public static class Builder{
        BancAccount instance = new BancAccount();

        public Builder setId(int id){
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

        public BancAccount build(){
            return instance;
        }
    }
}
