package am.petstore.PetStore.user.annotations

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

//@Constraint(validatedBy = [])
//@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.VALUE_PARAMETER)
//@Retention(RetentionPolicy.RUNTIME)
//annotation class CheckEmail(val message: String = "Wrong email format.",
//                            val groups: Array<KClass<*>> = [],
//                            val payload: Array<KClass<out Payload?>> = [])