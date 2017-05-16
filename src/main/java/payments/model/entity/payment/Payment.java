package payments.model.entity.payment;

import payments.model.entity.BankAccount;
import payments.model.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This class represents payment, which will be saved in payments history
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Payment payment = (Payment) o;

        if (sender != null ? !sender.equals(payment.sender) : payment.sender != null) return false;
        if (recipient != null ? !recipient.equals(payment.recipient) : payment.recipient != null) return false;
        if (date != null ? !date.equals(payment.date) : payment.date != null) return false;
        if (mfo != null ? !mfo.equals(payment.mfo) : payment.mfo != null) return false;
        if (usreou != null ? !usreou.equals(payment.usreou) : payment.usreou != null) return false;
        if (tariff != null ? !tariff.equals(payment.tariff) : payment.tariff != null) return false;
        return paymentPurpose != null ? paymentPurpose.equals(payment.paymentPurpose) : payment.paymentPurpose == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (mfo != null ? mfo.hashCode() : 0);
        result = 31 * result + (usreou != null ? usreou.hashCode() : 0);
        result = 31 * result + (tariff != null ? tariff.hashCode() : 0);
        result = 31 * result + (paymentPurpose != null ? paymentPurpose.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", sum=" + sum +
                ", date=" + date +
                ", mfo='" + mfo + '\'' +
                ", usreou='" + usreou + '\'' +
                ", tariff=" + tariff +
                ", paymentPurpose='" + paymentPurpose + '\'' +
                '}';
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
