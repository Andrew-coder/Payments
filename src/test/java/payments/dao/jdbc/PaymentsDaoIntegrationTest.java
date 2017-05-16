package payments.dao.jdbc;

import data.PaymentsData;
import net.jcip.annotations.NotThreadSafe;
import org.junit.*;
import payments.dao.ConnectionWrapper;
import payments.dao.DaoFactory;
import payments.dao.PaymentDao;
import payments.model.entity.payment.Payment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@NotThreadSafe
public class PaymentsDaoIntegrationTest {
    private DaoFactory daoFactory = TestDaoFactory.getInstance();
    private List<Payment> testPayments;
    private PaymentDao paymentDao;
    private ConnectionWrapper wrapper;

    {
        testPayments = new ArrayList<>();
        for(PaymentsData data : PaymentsData.values()){
            testPayments.add(data.payment);
        }
    }

    @Before
    public void init(){
        wrapper = daoFactory.getConnection();
        paymentDao = daoFactory.getPaymentDao(wrapper);
        wrapper.beginTransaction();
    }

    @After
    public void tearDown(){
        wrapper.rollbackTransaction();
        wrapper.close();
    }

    @Test
    public void testFindAll(){
        List<Payment> payments = paymentDao.findAll();
        assertEquals(payments, testPayments);
    }

    @Test
    public void testFindById(){
        Payment expectedPayment = testPayments.get(0);
        long id = expectedPayment.getId();
        Payment payment = paymentDao.findById(id).get();
        assertEquals(payment, expectedPayment);
    }

    @Test
    public void testFindAllByStartAndQuantity(){
        List<Payment> expectedPayments = testPayments.subList(0,5);
        int startFrom = 0;
        int quantity = 5;
        assertEquals(paymentDao.findAll(startFrom, quantity), expectedPayments);
    }

    @Test
    public void testCreatePayment(){
        Payment newPayment = testPayments.get(0);
        paymentDao.create(newPayment);
        assertEquals(testPayments.size()+1, paymentDao.getTotalCount());
    }
}