package payments.model.dto.payment;

public class CardTransferData {
    private String senderCard;
    private String recipientCard;
    private double sum;
    private String paymentPurpose;

    public String getSenderCard() {
        return senderCard;
    }

    public String getRecipientCard() {
        return recipientCard;
    }

    public double getSum() {
        return sum;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public static class Builder {
        CardTransferData instance = new CardTransferData();

        public Builder setSenderCard(String senderCard){
            instance.senderCard=senderCard;
            return this;
        }

        public Builder setRecipientCard(String recipientCard) {
            instance.recipientCard=recipientCard;
            return this;
        }

        public Builder setSum(double sum){
            instance.sum=sum;
            return this;
        }

        public Builder setPaymentPurpose(String paymentPurpose){
            instance.paymentPurpose=paymentPurpose;
            return this;
        }

        public CardTransferData build(){
            return instance;
        }
    }
}
