package org.jp.spring.data.elastic.search.rest;

import java.util.Map;
import java.util.Optional;

import org.jp.spring.data.elastic.search.model.Account;
import org.jp.spring.data.elastic.search.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("elk")
public class AccountController {

	@Autowired
	private AccountService accountServiceImpl;

	@GetMapping("accounts")
	public Iterable<Account> findAllAccount() {
		log.info("enter into findAllAccount method");
		final Iterable<Account> accountIterable = accountServiceImpl.findAllAccount();
		log.info("exit from findAllAccount method with output : {}.", accountIterable);
		return accountIterable;
	}

	@GetMapping("accounts/{id}")
	public Optional<Account> findAccountById(@PathVariable("id") final String id) {
		log.info("enter into findAccountById method with parameters : {}", id);
		final Optional<Account> optionalAccount = accountServiceImpl.findAccountById(id);
		log.info("exit from findAccountById method with output : {}", optionalAccount);
		return optionalAccount;
	}

	@GetMapping("accounts/state/{stateName}")
	public Iterable<Account> findAccountByState(@PathVariable("stateName") final String state) {
		log.info("enter into findAccountByState method with parameters : {}", state);
		final Iterable<Account> accountIterable = accountServiceImpl.findAccountByState(state);
		log.info("exit from findAccountByState method with output : {}", accountIterable);
		return accountIterable;
	}

	@PostMapping("account")
	public Account saveAccount(@RequestBody Account account) {
		log.info("enter into saveAccount method with parameters : {}", account);
		account = accountServiceImpl.saveAccount(account);
		log.info("exit from saveAccount method with output : {}", account);
		return account;
	}

	@PostMapping("accounts")
	public Iterable<Account> saveAllAccount(@RequestBody final Iterable<Account> accountList) {
		log.info("enter into saveAllAccount method with parameters : {}", accountList);
		final Iterable<Account> accountIterable = accountServiceImpl.saveAllAccount(accountList);
		log.info("exit from saveAllAccount method with output : {}", accountIterable);
		return accountIterable;
	}

	@DeleteMapping("accounts/{id}")
	public void deleteById(@PathVariable("id") final String id) {
		log.info("enter into deleteById method with parameters : {}", id);
		accountServiceImpl.deleteById(id);
		log.info("exit from deleteById method");
	}

	@DeleteMapping("account")
	public void deleteAccount(@RequestBody final Account account) {
		log.info("enter into deleteAccount method with parameters : {}", account);
		accountServiceImpl.deleteAccount(account);
		log.info("exit from deleteAccount method");
	}

	@GetMapping("accounts/totalCount")
	public long countTotalAccounts() {
		log.info("enter into countTotalAccounts method");
		final long totalAccounts = accountServiceImpl.countTotalAccounts();
		log.info("exit from countTotalAccounts method with output : {}", totalAccounts);
		return totalAccounts;
	}

	@PostMapping("account/index")
	public Account indexAccount(@RequestBody Account account) {
		log.info("enter into indexAccount method with parameters : {}", account);
		account = accountServiceImpl.indexAccount(account);
		log.info("exit from indexAccount method with output : {}", account);
		return account;
	}

	@PostMapping("account/indexWithoutRefresh")
	public Account indexAccountWithoutRefresh(@RequestBody Account account) {
		log.info("enter into indexAccountWithoutRefresh method with parameters : {}", account);
		account = accountServiceImpl.indexAccountWithoutRefresh(account);
		log.info("exit from indexAccountWithoutRefresh method with output : {}", account);
		return account;
	}

	@GetMapping("accounts/match/{state}")
	public Iterable<Account> matchAccountByState(@PathVariable("state") final String state) {
		log.info("enter into matchAccountByState method with parameters : {}", state);
		final Iterable<Account> iterableAccount = accountServiceImpl.matchAccountByState(state);
		log.info("exit from matchAccountByState method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@GetMapping("accounts/matchPhrase/{state}")
	public Iterable<Account> matchPhraseAccountByState(@PathVariable("state") final String state) {
		log.info("enter into matchPhraseAccountByState method with parameters : {}", state);
		final Iterable<Account> iterableAccount = accountServiceImpl.matchPhraseAccountByState(state);
		log.info("exit from matchPhraseAccountByState method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@GetMapping("accounts/matchPhrasePrefix/{state}")
	public Iterable<Account> matchPhrasePrefixAccountByState(@PathVariable("state") final String state) {
		log.info("enter into matchPhrasePrefixAccountByState method with parameters : {}", state);
		final Iterable<Account> iterableAccount = accountServiceImpl.matchPhrasePrefixAccountByState(state);
		log.info("exit from matchPhrasePrefixAccountByState method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@GetMapping("accounts/range")
	public Iterable<Account> rangeAccountAge(@RequestParam("startIndex") final long startIndex,
			@RequestParam("endIndex") final long endIndex) {
		log.info("enter into multiMatchAccountByStateAndEmployer method with parameters : {},{}", startIndex, endIndex);
		final Iterable<Account> iterableAccount = accountServiceImpl.rangeAccountAge(startIndex, endIndex);
		log.info("exit from multiMatchAccountByStateAndEmployer method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@GetMapping("accounts/metricAverageBalance/{metric}")
	public Map<String, Double> metricMinBalance(@PathVariable("metric") final String metric) {
		log.info("enter into averageStateAccountBalance method with parameters : {}", metric);
		final Map<String, Double> aggregations = accountServiceImpl.averageStateAccountBalance(metric);
		log.info("exit from averageStateAccountBalance method with output : {}", aggregations);
		return aggregations;
	}

	@GetMapping("accounts/metricMinBalance/{metric}")
	public Map<String, Double> metricAverageBalance(@PathVariable("metric") final String metric) {
		log.info("enter into averageStateAccountBalance method with parameters : {}", metric);
		final Map<String, Double> aggregations = accountServiceImpl.minAccountBalanceByState(metric);
		log.info("exit from averageStateAccountBalance method with output : {}", aggregations);
		return aggregations;
	}

}
