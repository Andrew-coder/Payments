package payments.model.entity.payment;

/**
 * This enum represents payment types, which are possible in this system
 */
public enum PaymentType {
    REFILL("refill"),
    TRANSFER_WITHIN_THIS_BANK("transfer within this bank"),
    TRANSFER_TO_ANOTHER_BANK_CARD("transfer to another bank's card"),
    TRANSFER_TO_ANOTHER_CARD_OF_ONE_USER("transfer to another card of one user");

    private String typeName;

    PaymentType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static PaymentType getType(String typeName){
        for(PaymentType type:values()){
            if(type.getTypeName().equals(typeName))
                return type;
        }
        return null;
    }
}