package com.smh.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.smh.annotation.NXDateTimeExtension
import com.smh.annotation.NXDateToString
import com.smh.annotation.NXLongToDate
import com.smh.annotation.NXLongToString
import com.smh.annotation.NXStringToDate
import com.smh.annotation.NXStringToString
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.ksp.writeTo
import java.util.Date
import kotlin.reflect.KClass

private enum class AnnotationType {
    STRING, LONG, DATE
}

class NXDateTimeFormatterProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotatedClasses = resolver
            .getSymbolsWithAnnotation(NXDateTimeExtension::class.qualifiedName.toString())
            .filterIsInstance(KSClassDeclaration::class.java)
            .also { logger.warn("Generating for ${it.joinToString { it.simpleName.getShortName() }}") }

        annotatedClasses.forEach { annotatedClass ->
            FileSpec.builder(
                annotatedClass.packageName.asString(),
                fileName = annotatedClass.simpleName.asString() + "_NXDateExtension"
            )
                .apply {
                    annotatedClass.getDeclaredProperties()
                        .forEach { annotatedProperty ->
                            buildExtensionProperties(annotatedClass, annotatedProperty)
                        }
                }
                .build()
                .writeTo(codeGenerator = codeGenerator, dependencies = Dependencies(aggregating = false, annotatedClass.containingFile!!)
            )
        }

        return emptyList()
    }

    @OptIn(KspExperimental::class)
    private fun FileSpec.Builder.buildExtensionProperties(
        annotatedClass: KSClassDeclaration,
        annotatedProperty: KSPropertyDeclaration
    ) {
        val stringToStringAnnotations = annotatedProperty.getAnnotationsByType(NXStringToString::class)
        val longToStringAnnotations = annotatedProperty.getAnnotationsByType(NXLongToString::class)
        val dateToStringAnnotations = annotatedProperty.getAnnotationsByType(NXDateToString::class)
        val stringToDateAnnotations = annotatedProperty.getAnnotationsByType(NXStringToDate::class)
        val longToDateAnnotations = annotatedProperty.getAnnotationsByType(NXLongToDate::class)

        val propertyName = annotatedProperty.simpleName.asString()
        val propertyType = annotatedProperty.type.resolve()
        val propertyTypeName = propertyType.declaration.qualifiedName?.asString()

        when(propertyTypeName) {
            "kotlin.String" -> {
                checkAndGo(
                    stringToStringAnnotations = stringToStringAnnotations,
                    longToStringAnnotations = longToStringAnnotations,
                    dateToStringAnnotations = dateToStringAnnotations,
                    stringToDateAnnotations = stringToDateAnnotations,
                    longToDateAnnotations = longToDateAnnotations,
                    checkType = AnnotationType.STRING,
                    className = annotatedClass.simpleName.asString(),
                    propertyName = propertyName,
                    onGo = {
                        val isUsedMultipleAnnotations = stringToDateAnnotations.count() > 0 && stringToDateAnnotations.count() > 0
                        buildProperty(
                            annotations = stringToStringAnnotations,
                            annotatedClass = annotatedClass,
                            generatePropertyType = String::class,
                            generatePropertyName = { dateProperty ->
                                if (isUsedMultipleAnnotations) "${dateProperty.prefixName}string_$propertyName"
                                else "${dateProperty.prefixName}$propertyName"
                            },
                            generatePropertyReturn = { dateProperty ->
                                "return this.$propertyName.changeFormatDate(originPattern = \"${dateProperty.originPattern}\", targetPattern = \"${dateProperty.targetPattern}\")"
                            }
                        )

                        buildNullableProperty(
                            annotations = stringToDateAnnotations,
                            annotatedClass = annotatedClass,
                            generatePropertyType = Date::class,
                            generatePropertyName = { dateProperty ->
                                if (isUsedMultipleAnnotations) "${dateProperty.prefixName}date_$propertyName"
                                else "${dateProperty.prefixName}$propertyName"
                            },
                            generatePropertyReturn = { dateProperty ->
                                "return this.$propertyName.toDate(originPattern = \"${dateProperty.originPattern}\")"
                            }
                        )
                    }
                )
            }
            "kotlin.Long" -> {
                checkAndGo(
                    stringToStringAnnotations = stringToStringAnnotations,
                    longToStringAnnotations = longToStringAnnotations,
                    dateToStringAnnotations = dateToStringAnnotations,
                    stringToDateAnnotations = stringToDateAnnotations,
                    longToDateAnnotations = longToDateAnnotations,
                    checkType = AnnotationType.LONG,
                    className = annotatedClass.simpleName.asString(),
                    propertyName = propertyName,
                    onGo = {
                        val isUsedMultipleAnnotations = longToStringAnnotations.count() > 0 && longToDateAnnotations.count() > 0
                        buildProperty(
                            annotations = longToStringAnnotations,
                            annotatedClass = annotatedClass,
                            generatePropertyType = String::class,
                            generatePropertyName = { dateProperty ->
                                if (isUsedMultipleAnnotations) "${dateProperty.prefixName}string_$propertyName"
                                else "${dateProperty.prefixName}$propertyName"
                            },
                            generatePropertyReturn = { dateProperty ->
                                "return this.$propertyName.changeFormatDate(targetPattern = \"${dateProperty.targetPattern}\")"
                            }
                        )

                        buildNullableProperty(
                            annotations = longToDateAnnotations,
                            annotatedClass = annotatedClass,
                            generatePropertyType = Date::class,
                            generatePropertyName = { dateProperty ->
                                if (isUsedMultipleAnnotations) "${dateProperty.prefixName}date_$propertyName"
                                else "${dateProperty.prefixName}$propertyName"
                            },
                            generatePropertyReturn = { _ ->
                                "return this.$propertyName.toDate()"
                            }
                        )
                    }
                )
            }
            "java.util.Date" -> {
                checkAndGo(
                    stringToStringAnnotations = stringToStringAnnotations,
                    longToStringAnnotations = longToStringAnnotations,
                    dateToStringAnnotations = dateToStringAnnotations,
                    stringToDateAnnotations = stringToDateAnnotations,
                    longToDateAnnotations = longToDateAnnotations,
                    checkType = AnnotationType.DATE,
                    className = annotatedClass.simpleName.asString(),
                    propertyName = propertyName,
                    onGo = {
                        buildProperty(
                            annotations = dateToStringAnnotations,
                            annotatedClass = annotatedClass,
                            generatePropertyType = String::class,
                            generatePropertyName = { dateProperty ->
                                "${dateProperty.prefixName}$propertyName"
                            },
                            generatePropertyReturn = { dateProperty ->
                                "return this.$propertyName.changeFormatDate(targetPattern = \"${dateProperty.targetPattern}\")"
                            }
                        )
                    }
                )
            }
        }
    }

    private inline fun checkAndGo(
        stringToStringAnnotations: Sequence<NXStringToString>,
        longToStringAnnotations: Sequence<NXLongToString>,
        dateToStringAnnotations: Sequence<NXDateToString>,
        stringToDateAnnotations: Sequence<NXStringToDate>,
        longToDateAnnotations: Sequence<NXLongToDate>,
        checkType: AnnotationType,
        className: String,
        propertyName: String,
        crossinline onGo: () -> Unit
    ) {
        when(checkType) {
            AnnotationType.STRING -> {
                if(
                    (stringToStringAnnotations.count() > 0 || stringToDateAnnotations.count() > 0)
                    && longToStringAnnotations.count() == 0
                    && longToDateAnnotations.count() == 0
                    && dateToStringAnnotations.count() == 0
                ) {
                    onGo()
                } else {
                    if (longToStringAnnotations.any()) {
                        logger.error("NXLongToString annotation is not support for String property\nCheck $className.$propertyName")
                    }
                    if (longToDateAnnotations.any()) {
                        logger.error("NXLongToDate annotation is not support for String property\nCheck $className.$propertyName")
                    }
                    if (dateToStringAnnotations.any()) {
                        logger.error("NXDateToString annotation is not support for String property\nCheck $className.$propertyName")
                    }
                }
            }
            AnnotationType.LONG -> {
                if(
                    stringToStringAnnotations.count() == 0
                    && stringToDateAnnotations.count() == 0
                    && (longToStringAnnotations.count() > 0 || longToDateAnnotations.count() > 0)
                    && dateToStringAnnotations.count() == 0
                ) {
                    onGo()
                } else {
                    if (stringToStringAnnotations.any()) {
                        logger.error("NXStringToString annotation is not support for Long property\nCheck $className.$propertyName")
                    }
                    if (stringToDateAnnotations.any()) {
                        logger.error("NXStringToDate annotation is not support for Long property\nCheck $className.$propertyName")
                    }
                    if (dateToStringAnnotations.any()) {
                        logger.error("NXDateToString annotation is not support for Long property\nCheck $className.$propertyName")
                    }
                }
            }
            AnnotationType.DATE -> {
                if(
                    stringToStringAnnotations.count() == 0
                    && stringToDateAnnotations.count() == 0
                    && longToStringAnnotations.count() == 0
                    && longToDateAnnotations.count() == 0
                    && dateToStringAnnotations.count() > 0
                ) {
                    onGo()
                } else {
                    if (stringToStringAnnotations.any()) {
                        logger.error("NXStringToString annotation is not support for Date property\nCheck $className.$propertyName")
                    }
                    if (stringToDateAnnotations.any()) {
                        logger.error("NXStringToDate annotation is not support for Date property\nCheck $className.$propertyName")
                    }
                    if (longToStringAnnotations.any()) {
                        logger.error("NXLongToString annotation is not support for Date property\nCheck $className.$propertyName")
                    }
                    if (longToDateAnnotations.any()) {
                        logger.error("NXLongToDate annotation is not support for Date property\nCheck $className.$propertyName")
                    }
                }
            }
        }
    }

    private fun <T: Annotation> FileSpec.Builder.buildProperty(
        annotations: Sequence<T>,
        annotatedClass: KSClassDeclaration,
        generatePropertyType: KClass<*>,
        generatePropertyName: (T) -> String,
        generatePropertyReturn: (T) -> String
    ) {
        annotations.forEach { property ->
            addImport(
                packageName = "com.smh.annotation",
                "changeFormatDate"
            )
            addImport(
                packageName = "com.smh.annotation",
                "toDate"
            )

            val packageName = annotatedClass.packageName.asString()
            val className = annotatedClass.qualifiedName?.asString().toString()

            addProperty(
                PropertySpec.builder(generatePropertyName(property), generatePropertyType)
                    .receiver(ClassName(packageName, className))
                    .getter(
                        FunSpec.getterBuilder()
                            .addStatement(generatePropertyReturn(property))
                            .build()
                    )
                    .build()
            )
        }
    }

    private fun <T: Annotation> FileSpec.Builder.buildNullableProperty(
        annotations: Sequence<T>,
        annotatedClass: KSClassDeclaration,
        generatePropertyType: KClass<*>,
        generatePropertyName: (T) -> String,
        generatePropertyReturn: (T) -> String
    ) {
        annotations.forEach { property ->
            addImport(
                packageName = "com.smh.annotation",
                "changeFormatDate"
            )
            addImport(
                packageName = "com.smh.annotation",
                "toDate"
            )

            val packageName = annotatedClass.packageName.asString()
            val className = annotatedClass.qualifiedName?.asString().toString()

            addProperty(
                PropertySpec.builder(generatePropertyName(property), generatePropertyType.asTypeName().copy(nullable = true))
                    .receiver(ClassName(packageName, className))
                    .getter(
                        FunSpec.getterBuilder()
                            .addStatement(generatePropertyReturn(property))
                            .build()
                    )
                    .build()
            )
        }
    }
}