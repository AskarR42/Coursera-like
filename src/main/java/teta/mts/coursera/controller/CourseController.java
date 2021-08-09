package teta.mts.coursera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teta.mts.coursera.domain.Course;
import teta.mts.coursera.domain.User;
import teta.mts.coursera.dto.CourseDto;
import teta.mts.coursera.dto.UserDto;
import teta.mts.coursera.exceptions.NotFoundException;
import teta.mts.coursera.service.CourseService;
import teta.mts.coursera.service.RoleService;
import teta.mts.coursera.service.StatisticsCounter;
import org.springframework.beans.factory.annotation.Autowired;
import teta.mts.coursera.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;
    private final RoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    public CourseController(CourseService courseService, UserService userService, StatisticsCounter statisticsCounter, RoleService roleService) {
        this.courseService = courseService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false, defaultValue = "") String titlePrefix, Principal principal, HttpSession httpSession) {
        if (principal != null) {
            logger.info("Request from user '{}'", principal.getName());
        }

        model.addAttribute("courses", courseService.findByTitleLike(titlePrefix + "%"));

        model.addAttribute("activePage", "courses");

        return "course_table";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String editCourse(Model model, @PathVariable("id") Long id) {
        model.addAttribute("course", courseService.findById(id).orElseThrow(NotFoundException::new));
        return "course_form";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/assign")
    public String assignUser(Model model, HttpServletRequest request,  @PathVariable("id") Long id) {
        model.addAttribute("course", courseService.findById(id).orElseThrow(NotFoundException::new));
        if (request.isUserInRole("ROLE_ADMIN")) {
            model.addAttribute("users", userService.findAll());
        } else {
            UserDto userDto = userService.findUserByUsername(request.getRemoteUser()).orElseThrow(NotFoundException::new);
            model.addAttribute("users", Collections.singletonList(userDto));
        }

        return "assign_user";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String courseForm(Model model) {
        model.addAttribute("course", new CourseDto());
        return "course_form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public String createCourse(@Valid @ModelAttribute("course") CourseDto courseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }

        if (courseDto.getId() != null) {
            CourseDto existingCourseDto = courseService.findById(courseDto.getId()).orElseThrow(NotFoundException::new);
            if (courseDto.getLessons() == null) {
                courseDto.setLessons(existingCourseDto.getLessons());
            }
            if (courseDto.getUsers() == null) {
                courseDto.setUsers(existingCourseDto.getUsers());
            }
        }
        courseService.save(courseDto);

        return "redirect:/course";
    }

    @PostMapping("/{courseId}/assign")
    public String assignUserFrom(@PathVariable("courseId") Long courseId, @RequestParam("userId") Long userId) {
        CourseDto courseDto = courseService.findById(courseId).orElseThrow(NotFoundException::new);
        Course course = new Course(courseDto.getId(), courseDto.getAuthor(), courseDto.getTitle(), courseDto.getLessons(), courseDto.getUsers());

        UserDto userDto = userService.findById(userId).orElseThrow(NotFoundException::new);
        User user = new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getCourses(), userDto.getRoles());

        course.getUsers().add(user);
        user.getCourses().add(course);
        courseService.save(courseDto);

        return "redirect:/course";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteById(id);
        return "redirect:/course";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{courseId}/unassign")
    public String unassignUser(@PathVariable("courseId") Long courseId, @RequestParam("userId") Long userId) {
        CourseDto courseDto = courseService.findById(courseId).orElseThrow(NotFoundException::new);
        Course course = new Course(courseDto.getId(), courseDto.getAuthor(), courseDto.getTitle(), courseDto.getLessons(), courseDto.getUsers());
        UserDto userDto = userService.findById(userId).orElseThrow(NotFoundException::new);
        User user = new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getCourses(), userDto.getRoles());

        course.getUsers().remove(user);
        courseService.save(courseDto);

        return "redirect:/course/" + courseId;
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
