package teta.mts.coursera.service;

import org.springframework.stereotype.Service;
import teta.mts.coursera.dao.CourseRepository;
import teta.mts.coursera.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import teta.mts.coursera.dto.CourseDto;
import teta.mts.coursera.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDto> findAll() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseDto(course.getId(), course.getAuthor(), course.getTitle(), course.getLessons(), course.getUsers()))
                .collect(Collectors.toList());
    }

    public Optional<CourseDto> findById(long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.map(course ->
                new CourseDto(course.getId(), course.getAuthor(), course.getTitle(), course.getLessons(), course.getUsers())
        );
    }

    public List<CourseDto> findByTitleLike(String title) {
        return courseRepository.findByTitleLike(title).stream()
                .map(course -> new CourseDto(course.getId(), course.getAuthor(), course.getTitle(), course.getLessons(), course.getUsers()))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public void save(CourseDto courseDto) {
        courseRepository.save(new Course(courseDto.getId(), courseDto.getAuthor(), courseDto.getTitle(), courseDto.getLessons(), courseDto.getUsers()));
    }
}
