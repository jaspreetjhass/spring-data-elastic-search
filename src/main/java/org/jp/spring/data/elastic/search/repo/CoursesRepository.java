package org.jp.spring.data.elastic.search.repo;

import org.jp.spring.data.elastic.search.model.Courses;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CoursesRepository extends ElasticsearchRepository<Courses, String> {

	@Query(value = "{\"match\":{\"?0\":{\"query\":\"?1\"}}}")
	Iterable<Courses> matchDocumentsWithField(String fieldName, String fieldValue);

	@Query(value = "{\"match_phrase_prefix\":{\"?0\":{\"query\":\"?1\"}}}")
	Iterable<Courses> matchDocumentsWithPhrasePrefix(String fieldName, String fieldValue);

	@Query(value = "{ \"range\":{ \"?0\":{ \"gte\":\"?1\", \"lte\":\"?2\" } } } ")
	Iterable<Courses> rangeDocumentsWithCoursePublishDate(String fieldName, String param1, String param2);

	@Query(value = "{\"bool\":{\"filter\":{\"match\":{\"?0\":{\"query\":\"?1\"}}}}}")
	Iterable<Courses> filterDocumentsWithField(String fieldName, String fieldValue);

	@Query(value = "{\"bool\":{\"must\":[{\"match\":{\"?0\":{\"query\":\"?1\"}}},{\"match\":{\"?2\":{\"query\":\"?3\"}}}]}}")
	Iterable<Courses> mustMatchDocumentsWithFields(String fieldName1, String fieldValue1, String fieldName2,
			String fieldValue2);

	// department wise faculty type

}