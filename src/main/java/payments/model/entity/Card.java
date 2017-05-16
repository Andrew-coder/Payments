package payments.model.entity;

import payments.model.entity.user.User;

import java.util.Date;

/**
 * this class represents credit card entity
 */
public class Card extends BaseEntity {
    private String cardNumber;
    private Date expireDate;
    private User user;
    private String pin;
    private String cvv;
    private BankAccount account;

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

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Card card = (Card) o;

        if (cardNumber != null ? !cardNumber.equals(card.cardNumber) : card.cardNumber != null) return false;
        if (expireDate != null ? !expireDate.equals(card.expireDate) : card.expireDate != null) return false;
        if (pin != null ? !pin.equals(card.pin) : card.pin != null) return false;
        return cvv != null ? cvv.equals(card.cvv) : card.cvv == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (expireDate != null ? expireDate.hashCode() : 0);
        result = 31 * result + (pin != null ? pin.hashCode() : 0);
        result = 31 * result + (cvv != null ? cvv.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", expireDate=" + expireDate +
                ", user=" + user +
                ", pin='" + pin + '\'' +
                ", cvv='" + cvv + '\'' +
                ", account=" + account +
                '}';
    }

    public static class Builder{
        Card instance = new Card();

        public Builder setId(long id){
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

        public Builder setPin(String pin){
            instance.pin=pin;
            return this;
        }

        public Builder setCvv(String cvv){
            instance.cvv=cvv;
            return this;
        }

        public Builder setAccount(BankAccount account){
            instance.account=account;
            return this;
        }

        public Card build(){
            return instance;
        }
    }
}