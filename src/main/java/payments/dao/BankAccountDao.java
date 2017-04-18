package payments.dao;

import payments.model.entity.BankAccount;

import java.util.Optional;

public interface BankAccountDao extends CommonDao<BankAccount> {
    Optional<BankAccount> findBankAccountByCard(int id);
}
