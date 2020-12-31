package org.jp.spring.data.elastic.search.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(indexName = "courses", type = "classroom")
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Courses {

	@Id
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("room")
	private String room;
	private Professor professor;
	@JsonProperty("students_enrolled")
	private long studentsEnrolled;
	@JsonProperty("course_publish_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date coursePublishDate;
	@JsonProperty("course_description")
	private String courseDescription;

}
