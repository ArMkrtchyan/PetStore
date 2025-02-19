package am.petstore.petstore.user.service

import am.petstore.petstore.FileStorageProperties
import am.petstore.petstore.user.exceptions.FileStorageException
import am.petstore.petstore.user.exceptions.MyFileNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class FileStorageService @Autowired constructor(fileStorageProperties: FileStorageProperties) {
    private val fileStorageLocation: Path = Paths.get(fileStorageProperties.uploadDir!!)
            .toAbsolutePath().normalize()

    private val fileStorageLocationCategory: Path = Paths.get(fileStorageProperties.uploadDirCategory!!)
            .toAbsolutePath().normalize()

    private val fileStorageLocationProducts: Path = Paths.get(fileStorageProperties.uploadDirProducts!!)
            .toAbsolutePath().normalize()

    private val fileStorageLocationPets: Path = Paths.get(fileStorageProperties.uploadDirPet!!)
            .toAbsolutePath().normalize()

    fun storeFile(file: MultipartFile): String { // Normalize file name
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try { // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            }
            // Copy file to the target location (Replacing existing file with the same name)
            val targetLocation = fileStorageLocation.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (ex: IOException) {
            throw FileStorageException("Could not store file $fileName. Please try again!", ex)
        }
    }

    fun storeCategoryPhoto(file: MultipartFile): String { // Normalize file name
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try { // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            }
            // Copy file to the target location (Replacing existing file with the same name)
            val targetLocation = fileStorageLocationCategory.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (ex: IOException) {
            throw FileStorageException("Could not store file $fileName. Please try again!", ex)
        }
    }

    fun storePetPhoto(file: MultipartFile): String { // Normalize file name
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try { // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            }
            // Copy file to the target location (Replacing existing file with the same name)
            val targetLocation = fileStorageLocationPets.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (ex: IOException) {
            throw FileStorageException("Could not store file $fileName. Please try again!", ex)
        }
    }

    fun storeProductPhoto(file: MultipartFile): String { // Normalize file name
        val fileName = StringUtils.cleanPath(file.originalFilename!!)
        return try { // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw FileStorageException("Sorry! Filename contains invalid path sequence $fileName")
            }
            // Copy file to the target location (Replacing existing file with the same name)
            val targetLocation = fileStorageLocationProducts.resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            fileName
        } catch (ex: IOException) {
            throw FileStorageException("Could not store file $fileName. Please try again!", ex)
        }
    }

    fun loadFileAsResource(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocation.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw MyFileNotFoundException("File not found $fileName")
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found $fileName", ex)
        }
    }

    fun loadCategoryPhoto(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocationCategory.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw MyFileNotFoundException("File not found $fileName")
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found $fileName", ex)
        }
    }

    fun loadProductPhoto(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocationProducts.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw MyFileNotFoundException("File not found $fileName")
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found $fileName", ex)
        }
    }

    fun loadPetPhoto(fileName: String): Resource {
        return try {
            val filePath = fileStorageLocationPets.resolve(fileName).normalize()
            val resource: Resource = UrlResource(filePath.toUri())
            if (resource.exists()) {
                resource
            } else {
                throw MyFileNotFoundException("File not found $fileName")
            }
        } catch (ex: MalformedURLException) {
            throw MyFileNotFoundException("File not found $fileName", ex)
        }
    }

    init {
        try {
            Files.createDirectories(fileStorageLocation)
            Files.createDirectories(fileStorageLocationCategory)
            Files.createDirectories(fileStorageLocationProducts)
            Files.createDirectories(fileStorageLocationPets)
        } catch (ex: Exception) {
            throw FileStorageException("Could not create the directory where the uploaded files will be stored.", ex)
        }
    }
}