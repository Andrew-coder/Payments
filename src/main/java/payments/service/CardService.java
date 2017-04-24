package payments.service;

import payments.model.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<Card> findById(long id);
    List<Card> findAll();
    void create(Card card);
    void block(long id);
    boolean isCardBlocked(long id);
}
