package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Tag("UserMapper unit tests")
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapperUnderTest;

    User user;
    UserDto userDto;

    @BeforeEach
    public void setup() {

        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        userDto = new UserDto(1L, "yassine@gmail.com", "yassine", "yassine", true,"password", currentLocalDateTime,currentLocalDateTime);
        user = User.builder()
                .id(1L)
                .email("yassine@gmail.com")
                .admin(true)
                .firstName("yassine")
                .lastName("yassine")
                .createdAt(currentLocalDateTime)
                .updatedAt(currentLocalDateTime)
                .password("password")
                .build();
    }

    @Tag("shouldMapDtoToEntity valid unit test")
    @Test
    public void shouldMapDtoToEntity(){
        User result = userMapperUnderTest.toEntity(userDto);

        assert (result.equals(result));
        assert (result.getId().equals(user.getId()));
        assert (result.getEmail().equals(user.getEmail()));
        assert (result.getFirstName().equals(user.getFirstName()));
        assert (result.getLastName().equals(user.getLastName()));
        assert (result.getCreatedAt().equals(user.getCreatedAt()));
        assert (result.getUpdatedAt().equals(user.getUpdatedAt()));

    }

    @Tag("shouldMapListDtoToListEntity valid unit test")
    @Test
    public void shouldMapListDtoToListEntity() {
        List<User> result = userMapperUnderTest.toEntity(Collections.singletonList(userDto));

        assert(result.equals(Collections.singletonList(user)));
    }

    @Tag("shouldMapEntityToDto valid unit test")
    @Test
    public void shouldMapEntityToDto() {
        UserDto result = userMapperUnderTest.toDto(user);

        assert(result.equals(userDto));
        assert(result.getId().equals(userDto.getId()));
        assert(result.getEmail().equals(userDto.getEmail()));
        assert(result.getFirstName().equals(userDto.getFirstName()));
        assert(result.getLastName().equals(userDto.getLastName()));
        assert(result.getPassword().equals(userDto.getPassword()));
        assert(result.isAdmin() == userDto.isAdmin());
        assert(result.getCreatedAt().equals(userDto.getCreatedAt()));
        assert(result.getUpdatedAt().equals(userDto.getUpdatedAt()));
    }

    @Tag("shouldMapListEntityToListDto valid unit test")
    @Test
    public void shouldMapListEntityToListDto() {
        List<UserDto> result = userMapperUnderTest.toDto(Collections.singletonList(user));

        assert(result.equals(Collections.singletonList(userDto)));
    }

}
