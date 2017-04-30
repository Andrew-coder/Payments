package payments.service;

import payments.model.dto.payment.CardTransferData;
import payments.model.dto.payment.RefillData;
import payments.model.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<Card> findById(long id);
    List<Card> findAll();
    void create(Card card);
    void block(long id);
    void refillCard(RefillData data);
    void transferBetweenCards(CardTransferData ransferData);
    boolean isCardBlocked(long id);
}
