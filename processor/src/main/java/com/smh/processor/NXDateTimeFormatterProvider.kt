package com.smh.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class NXDateTimeFormatterProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return NXDateTimeFormatterProcessor(
            codeGenerator = environment.codeGenerator,
            logger = environment.logger,
        )
    }
}
