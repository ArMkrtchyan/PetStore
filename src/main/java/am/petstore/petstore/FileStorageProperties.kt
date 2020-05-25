package am.petstore.petstore

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "file")
class FileStorageProperties {
    var uploadDir: String? = null
    var uploadDirPet: String? = null
    var uploadDirCategory: String? = null
    var uploadDirProducts: String? = null

}