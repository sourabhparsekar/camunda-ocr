package com.example.workflow.utils

import org.slf4j.Logger

class WorkflowLogger private constructor() {

    companion object {

        private const val INFO_LOGGER = "Timestamp:{}:workflow-service-info:{}:{}"
        private const val ERROR_LOGGER = "Timestamp:{}:workflow-service-error:{}:{}"

        fun info(logger: Logger, method: String?, message: String?) {
            logger.info(INFO_LOGGER, System.currentTimeMillis(), method, message)
        }

        fun error(logger: Logger, method: String?, message: String?, exception: Exception?) {
            logger.error(ERROR_LOGGER, System.currentTimeMillis(), method, message, exception)
        }

        fun error(logger: Logger, method: String?, message: String?) {
            logger.error(ERROR_LOGGER, System.currentTimeMillis(), method, message)
        }

    }

    init {
        throw IllegalStateException("Logger Utility Class. Cannot be instantiated")
    }
}