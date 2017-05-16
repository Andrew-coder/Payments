package payments.dao;

import payments.model.entity.Card;

import java.util.List;
import java.util.Optional;

/**
 * DAO for credit card
 */
public interface CardDao extends CommonDao<Card> {
    /**
     * find all user's cards
     * @param id
     * @return
     */
    List<Card> findCardsByUser(long id);

    /**
     * find card by number, which is unique
     * @param number
     * @return
     */
    Optional<Card> findCardByNumber(String number);

    /**
     * find all cards which are currently blocked
     * @return
     */
    List<Card> findAllBlockedCards();

    /**
     * block card with specified id
     * @param id
     */
    void blockCard(long id);

    /**
     * unblock card with specified id
     * @param id
     */
    void unblockCard(long id);
}