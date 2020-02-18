package am.petstore.PetStore.user.annotations

import javax.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [])
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CheckEmail(val message: String = "Wrong email format.",
                            val groups: Array<KClass<out Any>> = [],
                            val payload: Array<KClass<out Any>> = [])