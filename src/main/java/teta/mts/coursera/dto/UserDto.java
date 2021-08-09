package teta.mts.coursera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teta.mts.coursera.domain.Course;
import teta.mts.coursera.domain.Role;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Имя должно быть указано")
    private String username;

    @NotBlank(message = "Пароль должен быть указан")
    private String password;

    private Set<Course> courses;

    private Set<Role> roles;
}
