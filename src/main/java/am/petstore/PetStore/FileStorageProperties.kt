package am.petstore.PetStore

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "file")
class FileStorageProperties {
    var uploadDir: String? = null

}