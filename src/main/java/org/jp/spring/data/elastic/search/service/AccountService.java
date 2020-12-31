package org.jp.spring.data.elastic.search.service;

import java.util.Map;
import java.util.Optional;

import org.jp.spring.data.elastic.search.model.Account;

public interface AccountService {

	Iterable<Account> findAllAccount();

	Optional<Account> findAccountById(String id);

	Iterable<Account> findAccountByState(String state);

	Account saveAccount(Account account);

	Iterable<Account> saveAllAccount(Iterable<Account> accountList);

	void deleteById(String id);

	void deleteAccount(Account account);

	long countTotalAccounts();

	Account indexAccount(Account account);

	Account indexAccountWithoutRefresh(Account account);

	Iterable<Account> matchAccountByState(String state);

	Iterable<Account> matchPhraseAccountByState(String state);

	Iterable<Account> matchPhrasePrefixAccountByState(String state);

	Iterable<Account> rangeAccountAge(long startIndex, long endIndex);

	Map<String, Double> averageStateAccountBalance(String metric);
	
	Map<String, Double> minAccountBalanceByState(final String metric);

}