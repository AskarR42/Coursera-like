package teta.mts.coursera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teta.mts.coursera.domain.Lesson;
import teta.mts.coursera.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Long id;

    @NotBlank(message = "Автор курса должен быть указан")
    private String author;

    @NotBlank(message = "Название курса должно быть указано")
    private String title;

    private List<Lesson> lessons;

    private Set<User> users;

}
