package payments.model.entity;

public enum PaymentType {
    ONE_BANK_PAYMENT("transaction within 1 bank", 0.01),
    DIFFERENT_BANKS_PAYMENT("transaction beetwen different banks", 0.03);

    private String paymentName;
    private double paymentRate;

    PaymentType(String paymentName, double paymentRate) {
        this.paymentName = paymentName;
        this.paymentRate = paymentRate;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public static PaymentType getPaymentType(String paymentName){
        for(PaymentType type:values()){
            if(type.getPaymentName().equals(paymentName)){
                return type;
            }
        }
        return null;
    }
}