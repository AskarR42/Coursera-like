package teta.mts.coursera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teta.mts.coursera.domain.Course;
import teta.mts.coursera.domain.Lesson;
import teta.mts.coursera.dto.LessonDto;
import teta.mts.coursera.exceptions.NotFoundException;
import teta.mts.coursera.service.CourseService;
import teta.mts.coursera.service.LessonService;
import teta.mts.coursera.service.StatisticsCounter;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final StatisticsCounter statisticsCounter;

    @Autowired
    LessonController(LessonService lessonService, CourseService courseService, StatisticsCounter statisticsCounter) {
        this.lessonService = lessonService;
        this.courseService = courseService;
        this.statisticsCounter = statisticsCounter;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String lessonForm(Model model, @RequestParam("course_id") long courseId) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("lesson", new LessonDto(courseId));
        return "lesson_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String editLesson(Model model, @PathVariable("id") Long id) {
        model.addAttribute("lesson", lessonService.findById(id).orElseThrow(NotFoundException::new));
        return "lesson_form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public String createLesson(@Valid @ModelAttribute("lesson") LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }
        lessonService.save(lessonDto);
        return "redirect:/course/" + lessonDto.getCourseId();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable("id") Long id) {
        Long courseId = lessonService.findById(id).orElseThrow(NotFoundException::new).getCourseId();
        lessonService.deleteById(id);
        return "redirect:/course/" + courseId;
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
