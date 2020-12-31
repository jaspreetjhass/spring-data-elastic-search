package org.jp.spring.data.elastic.search.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "bank", type = "account")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Account {

	@Id
	private String id;
	@JsonProperty("account_number")
	private String accountNumber;
	@JsonProperty("firstname")
	private String firstName;
	@JsonProperty("lastname")
	private String lastName;
	@JsonProperty("balance")
	private long balance;
	@JsonProperty("age")
	private long age;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("address")
	private String address;
	@JsonProperty("employer")
	private String employer;
	@JsonProperty("email")
	private String email;
	@JsonProperty("city")
	private String city;
	@JsonProperty("state")
	private String state;

}
