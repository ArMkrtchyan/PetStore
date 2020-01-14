package am.petstore.PetStore

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@EnableConfigurationProperties(
        FileStorageProperties::class
)
class PetStoreApplication

private val LOGGER = LoggerFactory.getLogger(PetStoreApplication::class.java)

fun main(args: Array<String>) {
    val ctx: ApplicationContext = SpringApplication.run(PetStoreApplication::class.java, *args)
    LOGGER.info("Simple log statement with inputs {}, {} and {}", 1, 2, 3)
}
