import com.juniorsfredo.xtreme_management_api.XtremeManagementApiApplication;
import com.juniorsfredo.xtreme_management_api.infrastructure.config.dotenv.DotenvApplicationContextInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = XtremeManagementApiApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(initializers = DotenvApplicationContextInitializer.class)
public class XtremeManagementApiApplicationTests {

    @Test
    void contextLoads() {
        // Testa se o contexto do Spring Boot carrega corretamente
    }
}
