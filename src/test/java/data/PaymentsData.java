package data;

import payments.model.entity.BankAccount;
import payments.model.entity.payment.Payment;
import payments.model.entity.payment.PaymentTariff;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum PaymentsData {
    A(12,BankAccountsData.C.account,BankAccountsData.B.account,BigDecimal.valueOf(300.00),"2017-05-09 15:18:44",TariffsData.B.tariff,null, null,"purpose"),
    B(11,BankAccountsData.B.account,BankAccountsData.A.account,BigDecimal.valueOf(810.00),"2017-05-07 16:31:18",TariffsData.B.tariff,"820172","02070921","simple transfer"),
    C(10,BankAccountsData.B.account,BankAccountsData.A.account,BigDecimal.valueOf(1000.00),"2017-05-07 16:30:34",TariffsData.B.tariff,null, null,"purpose"),
    D(9,BankAccountsData.A.account,BankAccountsData.B.account,BigDecimal.valueOf(720.00),"2017-05-07 16:28:56",TariffsData.B.tariff,null, null,"purpose"),
    E(8,BankAccountsData.A.account,BankAccountsData.B.account,BigDecimal.valueOf(3000.00),"2017-05-07 16:28:12",TariffsData.B.tariff,"820172","02070921","payment for hostel"),
    F(7,new BankAccount(),BankAccountsData.B.account,BigDecimal.valueOf(1000.00),"2017-05-07 16:25:20",TariffsData.A.tariff,null, null,null),
    G(6,new BankAccount(),BankAccountsData.A.account,BigDecimal.valueOf(430.00),"2017-05-07 16:23:54",TariffsData.A.tariff,null, null,null),
    H(5,new BankAccount(),BankAccountsData.B.account,BigDecimal.valueOf(2301.00),"2017-05-07 16:00:16",TariffsData.A.tariff,null, null,null),
    K(4,new BankAccount(),BankAccountsData.B.account,BigDecimal.valueOf(300.00),"2017-05-07 15:56:11",TariffsData.A.tariff,null, null,null),
    L(3,BankAccountsData.B.account, BankAccountsData.A.account,BigDecimal.valueOf(2000.00),"2017-05-07 15:42:10", TariffsData.B.tariff,null, null,"purpose"),
    M(2,BankAccountsData.B.account, BankAccountsData.A.account,BigDecimal.valueOf(1500.00),"2017-05-07 15:38:25", TariffsData.B.tariff,null, null,"purpose"),
    N(1, BankAccountsData.B.account, BankAccountsData.A.account, BigDecimal.valueOf(1000.00),"2017-05-07 14:27:02", TariffsData.B.tariff,null,null,"purpose");

    public Payment payment;

    PaymentsData(long id, BankAccount sender, BankAccount recipient, BigDecimal sum, String dateTime, PaymentTariff tariff, String mfo, String usreou, String purpose){
        Date paymentDate =extractDateTimeFromString(dateTime);
        payment = new Payment.Builder()
                .setId(id)
                .setSender(sender)
                .setRecipient(recipient)
                .setSum(sum)
                .setDate(paymentDate)
                .setTariff(tariff)
                .setMfo(mfo)
                .setUsreou(usreou)
                .setPaymentPurpose(purpose)
                .build();
    }

    private Date extractDateTimeFromString(String date){
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return new java.sql.Timestamp(format.parse(date).getTime());
        }
        catch (ParseException ex){}
        return null;
    }
}