package payments.service.impl;

import org.apache.log4j.Logger;
import payments.dao.CardDao;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.model.entity.Card;
import payments.service.CardService;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService{
    private static final Logger logger = Logger.getLogger(CardServiceImpl.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private CardServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class InstanceHolder {
        private static final CardService instance = new CardServiceImpl(DaoFactory.getInstance());
    }

    public static CardService getInstance(){
        return InstanceHolder.instance;
    }

    @Override
    public Optional<Card> findById(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            return cardDao.findById(id);
        }
    }

    @Override
    public List<Card> findAll() {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            return cardDao.findAll();
        }
    }

    @Override
    public void create(Card card) {

    }

    @Override
    public void block(long id) {
        try(ConnectionWrapper wrapper = daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            if(!isCardBlocked(id)){
                cardDao.blockCard(id);
            }
        }
    }

    @Override
    public boolean isCardBlocked(long id) {
        try(ConnectionWrapper wrapper= daoFactory.getConnection()){
            CardDao cardDao = daoFactory.getCardDao(wrapper);
            boolean result = cardDao.findAllBlockedCards()
                    .stream()
                    .mapToLong(Card::getId)
                    .anyMatch(cardId -> cardId==id);
            return result;
        }
    }
}