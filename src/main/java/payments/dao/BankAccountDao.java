package payments.dao;

import payments.model.entity.BankAccount;

import java.util.Optional;

/**
 * DAO for bank account
 */
public interface BankAccountDao extends CommonDao<BankAccount> {
    /**
     * find bank account by it's card
     * @param id
     * @return
     */
    Optional<BankAccount> findBankAccountByCard(long id);

    /**
     * find bank account by number, which is unique
     * @param number
     * @return
     */
    Optional<BankAccount> findBankAccountByNumber(String number);
}
