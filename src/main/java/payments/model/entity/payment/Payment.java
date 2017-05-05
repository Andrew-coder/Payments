package payments.model.entity.payment;

import payments.model.entity.BankAccount;
import payments.model.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Payment extends BaseEntity {
    private BankAccount sender;
    private BankAccount recipient;
    private BigDecimal sum;
    private Date date;
    private String mfo;
    private String usreou;
    private PaymentTariff tariff;
    private String paymentPurpose;

    public BankAccount getSender() {
        return sender;
    }

    public void setSender(BankAccount sender) {
        this.sender = sender;
    }

    public BankAccount getRecipient() {
        return recipient;
    }

    public void setRecipient(BankAccount recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMfo() {
        return mfo;
    }

    public void setMfo(String mfo) {
        this.mfo = mfo;
    }

    public String getUsreou() {
        return usreou;
    }

    public void setUsreou(String usreou) {
        this.usreou = usreou;
    }

    public PaymentTariff getTariff() {
        return tariff;
    }

    public void setTariff(PaymentTariff tariff) {
        this.tariff = tariff;
    }

    public String getPaymentPurpose() {
        return paymentPurpose;
    }

    public void setPaymentPurpose(String paymentPurpose) {
        this.paymentPurpose = paymentPurpose;
    }

    public static class Builder{
        Payment instance = new Payment();

        public Builder setId(long id){
            instance.setId(id);
            return this;
        }

        public Builder setSender(BankAccount sender){
            instance.sender=sender;
            return this;
        }

        public Builder setRecipient(BankAccount recipient){
            instance.recipient=recipient;
            return this;
        }

        public Builder setSum(BigDecimal sum){
            instance.sum=sum;
            return this;
        }

        public Builder setDate(Date date){
            instance.date=date;
            return this;
        }

        public Builder setMfo(String mfo){
            instance.mfo=mfo;
            return this;
        }

        public Builder setUsreou(String usreou){
            instance.usreou=usreou;
            return this;
        }

        public Builder setTariff(PaymentTariff tariff){
            instance.tariff=tariff;
            return this;
        }

        public Builder setCurrentDate(){
            instance.date = new Date();
            return this;
        }

        public Builder setPaymentPurpose(String paymentPurpose){
            instance.paymentPurpose=paymentPurpose;
            return this;
        }

        public Payment build(){
            return instance;
        }
    }
}
