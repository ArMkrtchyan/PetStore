package am.petstore.PetStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class PetStoreApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PetStoreApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(PetStoreApplication.class, args);
        LOGGER.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3);
    }

}
