package payments.model.entity.payment;

import payments.model.entity.BaseEntity;

public class PaymentTariff extends BaseEntity {
    private PaymentType type;
    private double paymentRate;
    private long fixedRate;

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }

    public long getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(long fixedRate) {
        this.fixedRate = fixedRate;
    }

    public static class Builder{
        PaymentTariff instance = new PaymentTariff();

        public Builder setId(int id){
            instance.setId(id);
            return this;
        }

        public Builder setType(PaymentType type){
            instance.type=type;
            return this;
        }

        public Builder setPaymentRate(double paymentRate){
            instance.paymentRate=paymentRate;
            return this;
        }

        public Builder setFixedRate(long fixedRate){
            instance.fixedRate=fixedRate;
            return this;
        }

        public PaymentTariff build(){
            return instance;
        }
    }
}
