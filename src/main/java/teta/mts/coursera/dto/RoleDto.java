package teta.mts.coursera.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teta.mts.coursera.domain.User;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;

    private String name;

    private Set<User> users;

    public RoleDto(String name) {
        this.name = name;
    }
}
