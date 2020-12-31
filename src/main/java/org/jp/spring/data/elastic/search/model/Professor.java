package org.jp.spring.data.elastic.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Professor {

	@JsonProperty("name")
	private String name;
	@JsonProperty("department")
	private String department;
	@JsonProperty("faculty_type")
	private String facultyType;
	@JsonProperty("email")
	private String email;

}
