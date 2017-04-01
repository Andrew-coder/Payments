package payments.model.entity;

import java.util.Date;

public class Card extends BaseEntity {
    private String cardNumber;
    private Date expireDate;
    private User user;
    private BancAccount account;


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BancAccount getAccount() {
        return account;
    }

    public void setAccount(BancAccount account) {
        this.account = account;
    }

    public Card() {
    }

    public static class Builder{
        Card instance = new Card();

        public Builder setId(int id){
            instance.setId(id);
            return this;
        }

        public Builder setCardNumber(String cardNumber){
            instance.cardNumber = cardNumber;
            return this;
        }

        public Builder setExpireDate(Date expireDate){
            instance.expireDate = expireDate;
            return this;
        }

        public Builder setUser(User user){
            instance.user = user;
            return this;
        }

        public Builder setAccount(BancAccount account){
            instance.account=account;
            return this;
        }

        public Card build(){
            return instance;
        }
    }
}
