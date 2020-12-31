package org.jp.spring.data.elastic.search.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.jp.spring.data.elastic.search.constant.ApplicationConstant;
import org.jp.spring.data.elastic.search.model.Courses;
import org.jp.spring.data.elastic.search.repo.CoursesRepository;
import org.jp.spring.data.elastic.search.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.util.CloseableIterator;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CoursesServiceImpl implements CoursesService {

	@Autowired
	private CoursesRepository coursesRepository;
	@Autowired
	private ElasticsearchRestTemplate elasticsearchTemplate;

	@Override
	public List<Courses> findCourseByName(final String name) {
		log.info("enter into findCourseByName method with parameters : {}", name);
		final NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery(ApplicationConstant.NAME, name)).build();
		final List<Courses> coursesList = elasticsearchTemplate.queryForList(nativeSearchQuery, Courses.class);
		log.info("exit from findCourseByName method with output : {}", coursesList);
		return coursesList;
	}

	@Override
	public Iterable<Courses> matchDocumentsWithField(final String fieldName, final String fieldValue) {
		log.info("enter into matchDocumentsWithField method with parameters : {},{}", fieldName, fieldValue);
		final Iterable<Courses> courses = coursesRepository.matchDocumentsWithField(fieldName, fieldValue);
		log.info("exit from matchDocumentsWithField method with output : {}", courses);
		return courses;
	}

	@Override
	public Iterable<Courses> matchDocumentsWithPhrasePrefix(final String fieldName, final String fieldValue) {
		log.info("enter into matchDocumentsWithPhrasePrefix method with parameters : {},{}", fieldName, fieldValue);
		final Iterable<Courses> iterableCourses = coursesRepository.matchDocumentsWithPhrasePrefix(fieldName,
				fieldValue);
		log.info("exit from matchDocumentsWithPhrasePrefix method with output : {}", iterableCourses);
		return iterableCourses;
	}

	@Override
	public Iterable<Courses> rangeDocumentsWithCoursePublishDate(final String fieldName, final String param1,
			final String param2) {
		log.info("enter into rangeDocumentsWithCoursePublishDate method with parameters : {},{},{}", fieldName, param1,
				param2);
		final Iterable<Courses> iterableCourses = coursesRepository.rangeDocumentsWithCoursePublishDate(fieldName,
				param1, param2);
		log.info("exit from rangeDocumentsWithCoursePublishDate method with output : {}", iterableCourses);
		return iterableCourses;
	}

	@Override
	public Iterable<Courses> filterDocumentsWithField(final String fieldName, final String fieldValue) {
		log.info("enter into filterDocumentsWithField method with parameters : {},{}", fieldName, fieldValue);
		final Iterable<Courses> iterableCourses = coursesRepository.filterDocumentsWithField(fieldName, fieldValue);
		log.info("exit from filterDocumentsWithField method with output : {},{}", iterableCourses);
		return iterableCourses;
	}

	@Override
	public Iterable<Courses> mustMatchDocumentsWithFields(final String fieldName1, final String fieldValue1,
			final String fieldName2, final String fieldValue2) {
		log.info("enter into mustMatchDocumentsWithFields method with parameters : {},{},{},{}", fieldName1,
				fieldValue1, fieldName2, fieldValue2);
		final Iterable<Courses> iterableCourses = coursesRepository.mustMatchDocumentsWithFields(fieldName1,
				fieldValue1, fieldName2, fieldValue2);
		log.info("exit from mustMatchDocumentsWithFields method with output : {},{}", iterableCourses);
		return iterableCourses;
	}

	@Override
	public Courses indexCourses(Courses courses) {
		log.info("enter into indexCourses with parameters : {}", courses);
		final IndexQuery indexQuery = new IndexQueryBuilder().withId(courses.getId())
				.withIndexName(ApplicationConstant.COURSES).withType(ApplicationConstant.CLASSROOM).withObject(courses)
				.build();
		final String documentId = elasticsearchTemplate.index(indexQuery);
		final GetQuery getQuery = new GetQuery();
		getQuery.setId(documentId);
		courses = elasticsearchTemplate.queryForObject(getQuery, Courses.class);
		log.info("exit from indexCourses with output : {}", courses);
		return courses;
	}

	@Override
	public Courses findCoursesById(final String id) {
		log.info("enter into findCoursesById with parameters : {}", id);
		final GetQuery getQuery = new GetQuery();
		getQuery.setId(id);
		final Courses courses = elasticsearchTemplate.queryForObject(getQuery, Courses.class);
		log.info("exit from findCoursesById with output : {}", courses);
		return courses;
	}

	@Override
	public List<Courses> findCoursesByDepartment(final String departmentName) {
		log.info("enter into findCoursesByDepartment with parameters : {}", departmentName);
		final MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(ApplicationConstant.PROFESSOR_DEPARTMENT,
				departmentName);
		final NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(matchQueryBuilder).build();
		final List<Courses> coursesList = elasticsearchTemplate.queryForList(nativeSearchQuery, Courses.class);
		log.info("exit from findCoursesByDepartment with output : {}", coursesList);
		return coursesList;
	}

	@Override
	public CloseableIterator<Courses> findCoursesByDepartmentAndFaculty(final String departmentName,
			final String faculty) {
		log.info("enter into findCoursesByDepartmentAndFaculty with parameters : {},{}", departmentName, faculty);
		final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		final List<QueryBuilder> queryBuilderList = boolQueryBuilder.must();
		queryBuilderList.add(QueryBuilders.matchQuery(ApplicationConstant.PROFESSOR_DEPARTMENT, departmentName));
		queryBuilderList.add(QueryBuilders.matchQuery(ApplicationConstant.PROFESSOR_FACULTY_TYPE, faculty));
		final NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).build();
		final CloseableIterator<Courses> closeableIterator = elasticsearchTemplate.stream(nativeSearchQuery,
				Courses.class);
		log.info("exit from findCoursesByDepartmentAndFaculty with output : {}", closeableIterator);
		return closeableIterator;
	}

	@Override
	public Map<String, Map<String, Long>> findFacultyPerDepartment(final String departmentName, final String faculty) {
		log.info("enter into findFacultyPerDepartment with parameters : {}", departmentName);
		final Map<String, Map<String, Long>> map = null;

		final TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders
				.terms(ApplicationConstant.DEPARTMENT_GROUP).field(ApplicationConstant.PROFESSOR_DEPARTMENT)
				.subAggregation(AggregationBuilders.terms(ApplicationConstant.FACULTY_GROUP)
						.field(ApplicationConstant.PROFESSOR_FACULTY_TYPE));
		final NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
				.addAggregation(termsAggregationBuilder).withIndices("courses").withTypes("classroom").build();
		final ResultsExtractor<Aggregations> resultsExtractor = (searchResponse) -> searchResponse.getAggregations();

		final Aggregations aggregations = elasticsearchTemplate.query(nativeSearchQuery, resultsExtractor);
		final ParsedStringTerms parsedStringTerms = (ParsedStringTerms) aggregations.asMap()
				.get(ApplicationConstant.DEPARTMENT_GROUP);
		final List<? extends Bucket> bucketList = parsedStringTerms.getBuckets();
		/*
		 * bucketList.parallelStream().collect(Collectors.toMap(bucket ->
		 * bucket.getKey(), bucket ->
		 * ((ParsedStringTerms)bucket.getAggregations().asList().get(0)).getBuckets().
		 * parallelStream().collect(Collectors.toMap(b-> b.getKey(),
		 * b.getDocCount()))));
		 */
		log.info("exit from findFacultyPerDepartment with output : {}", map);
		return map;
	}
}
