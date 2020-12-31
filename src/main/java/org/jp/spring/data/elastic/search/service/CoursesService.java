package org.jp.spring.data.elastic.search.service;

import java.util.List;
import java.util.Map;

import org.jp.spring.data.elastic.search.model.Courses;
import org.springframework.data.util.CloseableIterator;

public interface CoursesService {

	List<Courses> findCourseByName(String name);

	Iterable<Courses> matchDocumentsWithField(String fieldName, String fieldValue);

	Iterable<Courses> matchDocumentsWithPhrasePrefix(String fieldName, String fieldValue);

	Iterable<Courses> rangeDocumentsWithCoursePublishDate(String fieldName, String param1, String param2);

	Iterable<Courses> filterDocumentsWithField(String fieldName, String fieldValue);

	Iterable<Courses> mustMatchDocumentsWithFields(String fieldName1, String fieldValue1, String fieldName2,
			String fieldValue2);

	Courses indexCourses(Courses courses);

	Courses findCoursesById(String id);

	List<Courses> findCoursesByDepartment(String departmentName);

	CloseableIterator<Courses> findCoursesByDepartmentAndFaculty(String departmentName, String faculty);

	Map<String, Map<String, Long>> findFacultyPerDepartment(String departmentName, String faculty);

}