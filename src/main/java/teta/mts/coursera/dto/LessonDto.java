package teta.mts.coursera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

    private Long id;

    @NotBlank(message = "Название должно быть заполнено")
    private String title;

    @NotBlank(message = "Содержание должно быть заполнено")
    private String text;

    private Long courseId;

    public LessonDto(Long courseId) {
        this.courseId = courseId;
    }
}
