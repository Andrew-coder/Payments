package payments.model.entity;

import java.util.Date;

public class Payment extends BaseEntity {
    private BankAccount sender;
    private BankAccount recipient;
    private long sum;
    private Date date;
    private String mfo;
    private String usreou;
    private PaymentType type;

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

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
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

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public static class Builder{
        Payment instance = new Payment();

        public Builder setId(int id){
            instance.setId(id);
            return this;
        }

        public Builder setSeder(BankAccount sender){
            instance.sender=sender;
            return this;
        }

        public Builder setRecipient(BankAccount recipient){
            instance.recipient=recipient;
            return this;
        }

        public Builder setSum(long sum){
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

        public Builder setType(PaymentType type){
            instance.type=type;
            return this;
        }

        public Payment build(){
            return instance;
        }
    }
}
