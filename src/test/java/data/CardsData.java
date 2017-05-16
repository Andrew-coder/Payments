package data;

import payments.model.entity.BankAccount;
import payments.model.entity.Card;
import payments.model.entity.user.User;
import payments.utils.extractors.RequestParamExtractor;

import java.util.Date;

public enum CardsData {
    A(1,"4102471200041788","1111","312","2018-08-05",BankAccountsData.A.account, "0631016341", UsersData.B.user),
    B(2,"5213582311152700","2222","423","2020-04-20",BankAccountsData.B.account, "0958026341",  UsersData.C.user),
    C(3,"9657926755596144","3333","534","2019-02-13",BankAccountsData.C.account, "0958085183", UsersData.D.user);

    public Card card;

    CardsData(long id, String cardNumber, String pin, String cvv, String date, BankAccount account, String cellphone, User user){
        RequestParamExtractor extractor = new RequestParamExtractor();
        Date expireDate= new java.sql.Date(extractor.extractDate(date).getTime());
        card = new Card.Builder()
                .setId(id)
                .setCardNumber(cardNumber)
                .setPin(pin)
                .setCvv(cvv)
                .setExpireDate(expireDate)
                .setAccount(account)
                .setUser(user)
                .build();
    }
}