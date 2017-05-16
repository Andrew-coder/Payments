package payments.model.entity.payment;

import payments.model.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * this class represents payment tariff, which depends on payment type
 */
public class PaymentTariff extends BaseEntity {
    private PaymentType type;

    /**
     * percentage of commission that will be taken from the user
     */
    private double paymentRate;

    /**
     * the absolute value of the commission, which will be taken from the user
     */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PaymentTariff tariff = (PaymentTariff) o;

        if (Double.compare(tariff.paymentRate, paymentRate) != 0) return false;
        return type == tariff.type;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(paymentRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PaymentTariff{" +
                "type=" + type +
                ", paymentRate=" + paymentRate +
                ", fixedRate=" + fixedRate +
                '}';
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