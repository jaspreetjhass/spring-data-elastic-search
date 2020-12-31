package org.jp.spring.data.elastic.search.repo;

import org.jp.spring.data.elastic.search.model.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AccountRepository extends ElasticsearchRepository<Account, String> {

	Iterable<Account> findByState(String state);

}
