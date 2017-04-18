package payments.model.entity.payment;

public enum PaymentType {
    REFILL,
    TRANSFER_WITHIN_THIS_BANK,
    TRANSFER_TO_ANOTHER_BANK_CARD,
    TRANSFER_TO_ANOTHER_CARD_OF_ONE_USER;
}