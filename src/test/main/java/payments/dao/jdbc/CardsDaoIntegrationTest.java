package payments.dao.jdbc;

import data.CardsData;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import payments.dao.CardDao;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.model.entity.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class CardsDaoIntegrationTest {
    private DaoFactory daoFactory = TestDaoFactory.getInstance();
    private List<Card> testCards;
    private CardDao cardDao;
    private ConnectionWrapper wrapper;

    {
        testCards = new ArrayList<>();
        for(CardsData data:CardsData.values()){
            testCards.add(data.card);
        }
    }

    @Before
    public void init(){
        wrapper = daoFactory.getConnection();
        cardDao = daoFactory.getCardDao(wrapper);
        wrapper.beginTransaction();
    }

    @After
    public void tearDown(){
        wrapper.rollbackTransaction();
        wrapper.close();
    }

    @Test
    public void testFindAll(){
        List<Card> cards = cardDao.findAll();
        assertEquals(cards, testCards);
    }

    @Test
    public void testFindById(){
        Card expectedCard = testCards.get(0);
        long id = expectedCard.getId();
        Card card = cardDao.findById(id).get();
        assertEquals(card, expectedCard);
    }

    @Test
    public void testFindByNumber(){
        Card expectedCard = testCards.get(0);
        String number = expectedCard.getCardNumber();
        Card card = cardDao.findCardByNumber(number).get();
        assertEquals(card, expectedCard);
    }

    @Test
    public void testFindCardsByUser(){
        Card expectedCard = testCards.get(0);
        long userId = expectedCard.getUser().getId();
        List<Card> cards = cardDao.findCardsByUser(userId);
        assertEquals(cards, Arrays.asList(expectedCard));
    }

    @Test
    public void testUpdateCard(){
        Card updatedCard = testCards.get(2);
        updatedCard.setPin("9876");
        updatedCard.setCvv("987");
        cardDao.update(updatedCard);
        long id = updatedCard.getId();
        Card foundCard = cardDao.findById(id).get();
        assertEquals(updatedCard, foundCard);
    }

    @Test
    public void testBlockCard(){
        Card blockedCard = testCards.get(1);
        cardDao.blockCard(blockedCard.getId());
        List<Card> blockedCards = cardDao.findAllBlockedCards();
        assertEquals(blockedCards, Arrays.asList(blockedCard));
    }

    @Test
    public void testUnblockCard(){
        Card blockedCard = testCards.get(0);
        cardDao.blockCard(blockedCard.getId());
        List<Card> blockedCards = cardDao.findAllBlockedCards();
        assertEquals(blockedCards, Arrays.asList(blockedCard));
        cardDao.unblockCard(blockedCard.getId());
        assertEquals(Arrays.asList(), cardDao.findAllBlockedCards());
    }

    @Test
    public void setFindAllBlockedCards(){
        Card card = testCards.get(2);
        cardDao.blockCard(card.getId());
        List<Card> expectedCards = Arrays.asList(card);
        assertEquals(expectedCards, cardDao.findAllBlockedCards());
    }
}