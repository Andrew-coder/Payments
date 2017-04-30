package payments.dao;

import payments.model.entity.BankAccount;
import payments.model.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardDao extends CommonDao<Card> {
    List<Card> findCardsByUser(long id);
    Optional<Card> findCardByNumber(String number);
    List<Card> findAllBlockedCards();
    void blockCard(long id);
    void unblockCard(long id);
}