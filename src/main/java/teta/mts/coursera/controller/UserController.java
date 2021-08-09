package teta.mts.coursera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teta.mts.coursera.domain.Role;
import teta.mts.coursera.dto.UserDto;
import teta.mts.coursera.exceptions.NotFoundException;
import teta.mts.coursera.service.RoleService;
import teta.mts.coursera.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public String userTable(Model model, @RequestParam(name = "namePrefix", required = false, defaultValue = "") String namePrefix) {
        model.addAttribute("users", userService.findByUsernameLike(namePrefix + "%"));
        model.addAttribute("active_page", "users");
        return "user_table";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/new")
    public String userForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @GetMapping("/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findById(id).orElseThrow(NotFoundException::new));
        return  "user_form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }
        userService.save(userDto);
        return "redirect:/admin/user";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/user";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }
}
