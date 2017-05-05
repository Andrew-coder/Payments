package payments.model.dto.payment;

public class AccountTransferData {
    private String accountNumber;
    private String senderCard;
    private String usreou;
    private String mfo;
    private double sum;
    private String paymentPurpose;

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getSenderCard() {
        return senderCard;
    }

    public String getUsreou() {
        return usreou;
    }

    public String getMfo() {
        return mfo;
    }

    public double getSum() {
        return sum;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public static class Builder {
        AccountTransferData instance = new AccountTransferData();

        public Builder setAccountNumber(String accountNumber){
            instance.accountNumber=accountNumber;
            return this;
        }

        public Builder setSenderCard(String senderCard){
            instance.senderCard=senderCard;
            return this;
        }

        public Builder setUsreou(String usreou){
            instance.usreou=usreou;
            return this;
        }

        public Builder setMfo(String mfo){
            instance.mfo=mfo;
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

        public AccountTransferData build(){
            return instance;
        }
    }
}
