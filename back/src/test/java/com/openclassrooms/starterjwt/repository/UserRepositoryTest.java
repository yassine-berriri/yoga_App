package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Tag("UserRepository Unit test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepositoryUnderTest;

    @Autowired
    private TestEntityManager testEntityManager;
    User userMock;

    @BeforeEach
    void setup() {
        userMock = User.builder()
                .email("yassine@gmail.com")
                .admin(true)
                .firstName("yassine")
                .lastName("yassine")
                .password("password")
                .build();

        testEntityManager.persist(userMock);
        testEntityManager.flush();
    }

    @DisplayName("findByEmail valid unit test ")
    @Test
    public void findByEmail_ShouldReturnUser_GivenEmail(){
        Optional<User> result = userRepositoryUnderTest.findByEmail("yassine@gmail.com");

        if(result != null){
            assertEquals(result.get().getId(), userMock.getId());
            assertEquals(result.get().getEmail(), userMock.getEmail());
            assertEquals(result.get().getFirstName(), userMock.getFirstName());
        }
        else {
            fail("user is null");
        }
    }

    @DisplayName("findByEmail invalid Unit test")
    @Test
    public void findByEmail_ShouldReturnNull_GivenNotExistedEmail(){
        Optional<User> result = userRepositoryUnderTest.findByEmail("inValid@gmail.com");

        assertEquals(result, Optional.empty());
    }

    @DisplayName("existByEmail valid unit test ")
    @Test
    public void existByEmail_ShouldReturnUser_GivenEmail(){
        Boolean result = userRepositoryUnderTest.existsByEmail("yassine@gmail.com");

       assertEquals(result, true);
    }




}
