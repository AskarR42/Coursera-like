package teta.mts.coursera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teta.mts.coursera.dao.CourseRepository;
import teta.mts.coursera.dao.LessonRepository;
import teta.mts.coursera.domain.Course;
import teta.mts.coursera.domain.Lesson;
import teta.mts.coursera.dto.LessonDto;
import teta.mts.coursera.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Autowired
    LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public List<LessonDto> findAll() {
        return lessonRepository.findAll().stream()
                .map(lesson -> new LessonDto(lesson.getId(), lesson.getTitle(), lesson.getText(), lesson.getCourse().getId()))
                .collect(Collectors.toList());
    }

    public Optional<LessonDto> findById(long id) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        return optionalLesson
                .map(lesson -> new LessonDto(lesson.getId(),lesson.getTitle(), lesson.getText(), lesson.getCourse().getId()));
    }

    public List<LessonDto> findByTitleLike(String title) {
        return lessonRepository.findByTitleLike(title).stream()
                .map(lesson -> new LessonDto(lesson.getId(), lesson.getTitle(), lesson.getText(), lesson.getCourse().getId()))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(NotFoundException::new);
        Course course = lesson.getCourse();

        course.getLessons().remove(lesson);
        courseRepository.save(course);
    }

    public void save(LessonDto lessonDto) {
        lessonRepository.save(new Lesson(lessonDto.getId(), lessonDto.getTitle(), lessonDto.getText(), courseRepository.findById(lessonDto.getCourseId()).orElseThrow(NotFoundException::new)));
    }


}
