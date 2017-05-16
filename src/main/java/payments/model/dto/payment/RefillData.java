package payments.model.dto.payment;

/**
 * This class represents DTO to transfer refill data from controller layer to UI
 */
public class RefillData {
    private long idRefilledCard;
    private String cardNumber;
    private String pin;
    private String cvv;
    private String expireDate;
    private double sum;

    public long getIdRefilledCard() {
        return idRefilledCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public String getCvv() {
        return cvv;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public double getSum() {
        return sum;
    }

    public static class Builder{
        RefillData instance = new RefillData();

        public Builder setIdRefilledCard(long idRefilledCard){
            instance.idRefilledCard=idRefilledCard;
            return this;
        }

        public Builder setCardNumber(String cardNumber){
            instance.cardNumber=cardNumber;
            return this;
        }

        public Builder setPin(String pin) {
            instance.pin=pin;
            return this;
        }

        public Builder setCvv(String cvv) {
            instance.cvv=cvv;
            return this;
        }

        public Builder setExpireDate(String expireDate){
            instance.expireDate=expireDate;
            return this;
        }

        public Builder setSum(double sum){
            instance.sum=sum;
            return this;
        }

        public RefillData build(){
            return instance;
        }
    }
}
