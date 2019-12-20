package am.store.PeetStore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
class PeetStoreApplication

fun main(args: Array<String>) {
    runApplication<PeetStoreApplication>(*args)
}


