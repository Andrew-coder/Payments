package payments.model.entity.payment;

import payments.model.entity.BaseEntity;

import java.math.BigDecimal;

public class PaymentTariff extends BaseEntity {
    private PaymentType type;
    private double paymentRate;
    private BigDecimal fixedRate;

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

    public BigDecimal getFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(BigDecimal fixedRate) {
        this.fixedRate = fixedRate;
    }

    public static class Builder{
        PaymentTariff instance = new PaymentTariff();

        public Builder setId(long id){
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

        public Builder setFixedRate(BigDecimal fixedRate){
            instance.fixedRate=fixedRate;
            return this;
        }

        public PaymentTariff build(){
            return instance;
        }
    }
}