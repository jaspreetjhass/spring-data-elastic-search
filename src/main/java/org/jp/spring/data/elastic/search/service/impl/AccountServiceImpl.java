package org.jp.spring.data.elastic.search.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.avg.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.min.ParsedMin;
import org.jp.spring.data.elastic.search.constant.ApplicationConstant;
import org.jp.spring.data.elastic.search.model.Account;
import org.jp.spring.data.elastic.search.repo.AccountRepository;
import org.jp.spring.data.elastic.search.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ElasticsearchRestTemplate elasticsearchTemplate;

	@Override
	public Iterable<Account> findAllAccount() {
		log.info("enter into findAllAccount method");
		final Iterable<Account> accountIterable = accountRepository.findAll();
		log.info("exit from findAllAccount method with output : {}.", accountIterable);
		return accountIterable;
	}

	@Override
	public Optional<Account> findAccountById(final String id) {
		log.info("enter into findAccountById method with parameters : {}", id);
		final Optional<Account> optionalAccount = accountRepository.findById(id);
		log.info("exit from findAccountById method with output : {}", optionalAccount);
		return optionalAccount;
	}

	@Override
	public Iterable<Account> findAccountByState(final String state) {
		log.info("enter into findAccountByState method with parameters : {}", state);
		final Iterable<Account> accountIterable = accountRepository.findByState(state);
		log.info("exit from findAccountByState method with output : {}", accountIterable);
		return accountIterable;
	}

	@Override
	public Account saveAccount(Account account) {
		log.info("enter into saveAccount method with parameters : {}", account);
		account = accountRepository.save(account);
		log.info("exit from saveAccount method with output : {}", account);
		return account;
	}

	@Override
	public Iterable<Account> saveAllAccount(final Iterable<Account> accountList) {
		log.info("enter into saveAllAccount method with parameters : {}", accountList);
		final Iterable<Account> accountIterable = accountRepository.saveAll(accountList);
		log.info("exit from saveAllAccount method with output : {}", accountIterable);
		return accountIterable;
	}

	@Override
	public void deleteById(final String id) {
		log.info("enter into deleteById method with parameters : {}", id);
		accountRepository.deleteById(id);
		log.info("exit from deleteById method");
	}

	@Override
	public void deleteAccount(final Account account) {
		log.info("enter into deleteAccount method with parameters : {}", account);
		accountRepository.delete(account);
		log.info("exit from deleteAccount method");
	}

	@Override
	public long countTotalAccounts() {
		log.info("enter into countTotalAccounts method");
		final long totalAccounts = accountRepository.count();
		log.info("exit from countTotalAccounts method with output : {}", totalAccounts);
		return totalAccounts;
	}

	@Override
	public Account indexAccount(Account account) {
		log.info("enter into indexAccount method with parameters : {}", account);
		account = accountRepository.index(account);
		log.info("exit from indexAccount method with output : {}", account);
		return account;
	}

	@Override
	public Account indexAccountWithoutRefresh(Account account) {
		log.info("enter into indexAccountWithoutRefresh method with parameters : {}", account);
		account = accountRepository.indexWithoutRefresh(account);
		log.info("exit from indexAccountWithoutRefresh method with output : {}", account);
		return account;
	}

	@Override
	public Iterable<Account> matchAccountByState(final String state) {
		log.info("enter into matchAccountByState method with parameters : {}", state);
		final Iterable<Account> iterableAccount = accountRepository
				.search(QueryBuilders.matchQuery(ApplicationConstant.STATE, state));
		log.info("exit from matchAccountByState method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@Override
	public Iterable<Account> matchPhraseAccountByState(final String state) {
		log.info("enter into matchPhraseAccountByState method with parameters : {}", state);
		final Iterable<Account> iterableAccount = accountRepository
				.search(QueryBuilders.matchPhraseQuery(ApplicationConstant.STATE, state));
		log.info("exit from matchPhraseAccountByState method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@Override
	public Iterable<Account> matchPhrasePrefixAccountByState(final String state) {
		log.info("enter into matchPhrasePrefixAccountByState method with parameters : {}", state);
		final Iterable<Account> iterableAccount = accountRepository
				.search(QueryBuilders.matchPhrasePrefixQuery(ApplicationConstant.STATE, state));
		log.info("exit from matchPhrasePrefixAccountByState method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@Override
	public Iterable<Account> rangeAccountAge(final long startIndex, final long endIndex) {
		log.info("enter into multiMatchAccountByStateAndEmployer method with parameters : {},{}", startIndex, endIndex);
		final Iterable<Account> iterableAccount = accountRepository
				.search(QueryBuilders.rangeQuery("age").gte(startIndex).lte(endIndex));
		log.info("exit from multiMatchAccountByStateAndEmployer method with output : {}", iterableAccount);
		return iterableAccount;
	}

	@Override
	public Map<String, Double> averageStateAccountBalance(final String metric) {
		log.info("enter into averageStateAccountBalance method with parameters : {}", metric);
		final NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().build();
		nativeSearchQuery.addAggregation(AggregationBuilders.terms(ApplicationConstant.METRIC_GROUP).field(metric)
				.subAggregation(AggregationBuilders.avg(ApplicationConstant.AVERAGE_BALANCE)
						.field(ApplicationConstant.BALANCE)));
		final ResultsExtractor<Aggregations> resultsExtractor = (searchResponse) -> searchResponse.getAggregations();
		final Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, resultsExtractor);
		final ParsedStringTerms parsedStringTerms = (ParsedStringTerms) aggregations
				.get(ApplicationConstant.METRIC_GROUP);
		final List<? extends Bucket> bucketList = parsedStringTerms.getBuckets();
		final Map<String, Double> metricAvgBalance = bucketList.parallelStream().collect(Collectors.toMap(
				bucket -> String.valueOf(bucket.getKey()),
				bucket -> ((ParsedAvg) bucket.getAggregations().asMap().get(ApplicationConstant.AVERAGE_BALANCE))
						.getValue()));
		log.info("exit from averageStateAccountBalance method with output : {}", aggregations);
		return metricAvgBalance;
	}

	@Override
	public Map<String, Double> minAccountBalanceByState(final String metric) {
		final NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().build();
		nativeSearchQuery
				.addAggregation(AggregationBuilders.terms(ApplicationConstant.METRIC_GROUP).field(metric).subAggregation(
						AggregationBuilders.min(ApplicationConstant.MIN_BALANCE).field(ApplicationConstant.BALANCE)));
		final ResultsExtractor<Aggregations> resultsExtractor = (searchResponse) -> searchResponse.getAggregations();
		final Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, resultsExtractor);
		final ParsedStringTerms metricAggregation = aggregations.get(ApplicationConstant.METRIC_GROUP);
		final List<? extends Bucket> bucketList = metricAggregation.getBuckets();
		final Map<String, Double> metricMinBalance = bucketList.parallelStream()
				.collect(Collectors.toMap(bucket -> String.valueOf(bucket.getKey()),
						bucket -> ((ParsedMin) bucket.getAggregations().asMap().get(ApplicationConstant.MIN_BALANCE))
								.getValue()));
		return metricMinBalance;
	}

}
