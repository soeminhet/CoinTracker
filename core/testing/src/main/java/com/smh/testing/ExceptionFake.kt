package com.smh.testing

import com.smh.network.DataException
import com.smh.network.ERROR_MESSAGE_GENERAL
import com.smh.network.ERROR_TITLE_GENERAL

val apiException = DataException.Api(message = ERROR_MESSAGE_GENERAL, errorCode = 404, title = ERROR_TITLE_GENERAL)