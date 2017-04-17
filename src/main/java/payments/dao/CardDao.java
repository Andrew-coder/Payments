package payments.dao;

import payments.model.entity.BankAccount;
import payments.model.entity.Card;

import java.util.List;

public interface CardDao extends CommonDao<Card> {
    List<Card> findCardsByUser(int id);
}