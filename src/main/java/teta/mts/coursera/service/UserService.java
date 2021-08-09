package teta.mts.coursera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teta.mts.coursera.dao.UserRepository;
import teta.mts.coursera.domain.User;
import teta.mts.coursera.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    UserService(UserRepository userRepository, RoleService roleService, RoleService roleService1, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService1;
        this.encoder = encoder;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), "", user.getCourses(), user.getRoles()))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new UserDto(user.getId(), user.getUsername(), "", user.getCourses(), user.getRoles()));
    }

    public List<UserDto> findByUsernameLike(String prefixUsername) {
        return userRepository.findUsersByUsernameLike(prefixUsername).stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), "", user.getCourses(), user.getRoles()))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(user -> new UserDto(user.getId(), user.getUsername(), "", user.getCourses(), user.getRoles()));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void save(UserDto userDto) {
        userRepository.save(new User(userDto.getId(), userDto.getUsername(), encoder.encode(userDto.getPassword()),
                userDto.getCourses(), userDto.getRoles()));
    }
}
