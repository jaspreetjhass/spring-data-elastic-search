package org.jp.spring.data.elastic.search.rest;

import java.util.List;
import java.util.Map;

import org.jp.spring.data.elastic.search.model.Courses;
import org.jp.spring.data.elastic.search.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.CloseableIterator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("elk")
public class CoursesController {

	@Autowired
	private CoursesService coursesServiceImpl;

	@GetMapping("courses/{courseName}")
	public List<Courses> findCourseByName(@PathVariable("courseName") final String name) {
		log.info("enter into findCourseByName method with parameters : {}", name);
		final List<Courses> coursesList = coursesServiceImpl.findCourseByName(name);
		log.info("exit from findCourseByName method with output : {}", coursesList);
		return coursesList;
	}

	@GetMapping("courses/match/{fieldName}/{fieldValue}")
	public Iterable<Courses> matchDocumentsWithField(@PathVariable("fieldName") final String fieldName,
			@PathVariable("fieldValue") final String fieldValue) {
		log.info("enter into matchDocumentsWithField method with parameters : {},{}", fieldName, fieldValue);
		final Iterable<Courses> courses = coursesServiceImpl.matchDocumentsWithField(fieldName, fieldValue);
		log.info("exit from matchDocumentsWithField method with output : {}", courses);
		return courses;
	}

	@GetMapping("courses/matchPhrasePrefix/{fieldName}/{fieldValue}")
	Iterable<Courses> matchDocumentsWithPhrasePrefix(@PathVariable("fieldName") final String fieldName,
			@PathVariable("fieldValue") final String fieldValue) {
		log.info("enter into matchDocumentsWithPhrasePrefix method with parameters : {},{}", fieldName, fieldValue);
		final Iterable<Courses> iterableCourses = coursesServiceImpl.matchDocumentsWithPhrasePrefix(fieldName,
				fieldValue);
		log.info("exit from matchDocumentsWithPhrasePrefix method with output : {}", iterableCourses);
		return iterableCourses;
	}

	@GetMapping("courses/range/{fieldName}/{param1}/{param2}")
	Iterable<Courses> rangeDocumentsWithCoursePublishDate(@PathVariable("fieldName") final String fieldName,
			@PathVariable("param1") final String param1, @PathVariable("param2") final String param2) {
		log.info("enter into rangeDocumentsWithCoursePublishDate method with parameters : {},{},{}", fieldName, param1,
				param2);
		final Iterable<Courses> iterableCourses = coursesServiceImpl.rangeDocumentsWithCoursePublishDate(fieldName,
				param1, param2);
		log.info("exit from rangeDocumentsWithCoursePublishDate method with output : {}", iterableCourses);
		return iterableCourses;
	}

	@GetMapping("courses/filter/{fieldName}/{fieldValue}")
	Iterable<Courses> filterDocumentsWithField(@PathVariable("fieldName") final String fieldName,
			@PathVariable("fieldValue") final String fieldValue) {
		log.info("enter into filterDocumentsWithField method with parameters : {},{}", fieldName, fieldValue);
		final Iterable<Courses> iterableCourses = coursesServiceImpl.filterDocumentsWithField(fieldName, fieldValue);
		log.info("exit from filterDocumentsWithField method with output : {},{}", iterableCourses);
		return iterableCourses;
	}

	@GetMapping("courses/mustMatch/{fieldName1}/{fieldValue1}/{fieldName2}/{fieldValue2}")
	Iterable<Courses> mustMatchDocumentsWithFields(@PathVariable("fieldName1") final String fieldName1,
			@PathVariable("fieldValue1") final String fieldValue1, @PathVariable("fieldName2") final String fieldName2,
			@PathVariable("fieldValue2") final String fieldValue2) {
		log.info("enter into mustMatchDocumentsWithFields method with parameters : {},{},{},{}", fieldName1,
				fieldValue1, fieldName2, fieldValue2);
		final Iterable<Courses> iterableCourses = coursesServiceImpl.mustMatchDocumentsWithFields(fieldName1,
				fieldValue1, fieldName2, fieldValue2);
		log.info("exit from mustMatchDocumentsWithFields method with output : {},{}", iterableCourses);
		return iterableCourses;
	}

	@PostMapping("courses")
	public Courses indexCourses(@RequestBody Courses courses) {
		log.info("enter into indexCourses with parameters : {}", courses);
		courses = coursesServiceImpl.indexCourses(courses);
		log.info("exit from indexCourses with output : {}", courses);
		return courses;
	}

	@GetMapping("courses/id/{id}")
	public Courses findCoursesById(@PathVariable("id") final String id) {
		log.info("enter into findCoursesById with parameters : {}", id);
		final Courses courses = coursesServiceImpl.findCoursesById(id);
		log.info("exit from findCoursesById with output : {}", courses);
		return courses;
	}

	@GetMapping("courses/department/{departmentName}")
	public List<Courses> findCoursesByDepartment(@PathVariable("departmentName") final String departmentName) {
		log.info("enter into findCoursesByDepartment with parameters : {}", departmentName);
		final List<Courses> coursesList = coursesServiceImpl.findCoursesByDepartment(departmentName);
		log.info("exit from findCoursesByDepartment with output : {}", coursesList);
		return coursesList;
	}

	@GetMapping("courses/department/{departmentName}/faculty/{faculty}")
	public CloseableIterator<Courses> findCoursesByDepartmentAndFaculty(
			@PathVariable("departmentName") final String departmentName,
			@PathVariable("faculty") final String faculty) {
		log.info("enter into findCoursesByDepartmentAndFaculty with parameters : {},{}", departmentName, faculty);
		final CloseableIterator<Courses> closeableIterator = coursesServiceImpl
				.findCoursesByDepartmentAndFaculty(departmentName, faculty);
		log.info("exit from findCoursesByDepartmentAndFaculty with output : {}", closeableIterator);
		return closeableIterator;
	}

	@GetMapping("courses/try")
	public Map<String, Map<String, Long>> findFacultyPerDepartment(final String departmentName, final String faculty) {
		log.info("enter into findFacultyPerDepartment with parameters : {}", departmentName);
		final Map<String, Map<String, Long>> map = coursesServiceImpl.findFacultyPerDepartment(departmentName, faculty);
		log.info("exit from findFacultyPerDepartment with output : {}", map);
		return map;
	}

}
