package integration;

import com.juniorsfredo.xtreme_management_api.XtremeManagementApiApplication;
import com.juniorsfredo.xtreme_management_api.api.dto.references.RoleReferenceDTO;
import com.juniorsfredo.xtreme_management_api.api.dto.user.UserDetailsResponseDTO;
import com.juniorsfredo.xtreme_management_api.domain.exceptions.UserNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.enums.RoleName;
import com.juniorsfredo.xtreme_management_api.domain.services.UserService;
import com.juniorsfredo.xtreme_management_api.infrastructure.config.dotenv.DotenvApplicationContextInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = XtremeManagementApiApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(initializers = DotenvApplicationContextInitializer.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestDataLoader.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    private Long existingUserId;

    private Long nonExistingUserId;

    @Autowired
    private TestDataLoader testDataLoader;

    @BeforeEach
    void setup() {
        existingUserId = 3L;
        nonExistingUserId = 1000L;
        testDataLoader.resetAndPopulateDatabase();
    }

    @Test
    @DisplayName(value = "Given existing user, when get user by id, then return valid user details")
    void givenExistingUser_whenGetUserId_ThenReturnUserId() {
        UserDetailsResponseDTO dto = userService.getUserById(existingUserId);

        assertNotNull(dto);
        assertEquals(existingUserId, dto.getId());
        assertEquals("Ana", dto.getName());
        assertEquals("member@example.com", dto.getEmail());
        assertEquals(RoleName.ROLE_MEMBER, dto.getRoles().stream().map(RoleReferenceDTO::getName).findFirst().orElse(null));
    }

    @Test
    @DisplayName(value = "Given non exist user id, when get user by id, then return user not found exception")
    void givenNonExistingUser_whenGetUserId_ThenReturnUserNotFoundException() {

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(nonExistingUserId)
        );

        assertEquals("User not found with id: " + nonExistingUserId, exception.getMessage());
    }

}
