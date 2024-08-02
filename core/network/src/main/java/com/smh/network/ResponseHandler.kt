package com.smh.network

import android.accounts.NetworkErrorException
import arrow.core.Either
import arrow.core.right
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.net.UnknownHostException

const val ERROR_MESSAGE_GENERAL = "Something went wrong. Please try again."
const val ERROR_JSON_CONVERSION = "Error json conversion. Please try again."
const val ERROR_RESPONSE_UNSUCCESSFUL = "Response is unsuccessful"
const val ERROR_BODY_NULL = "Response Body is null"
const val ERROR_TITLE_GENERAL = "Error"

suspend fun <T, R> handleCall(
    apiCall: suspend () -> Response<T>,
    mapper: suspend (T) -> R,
): Either<DataException, R> = try {
    val response = apiCall()
    val code = response.code()
    val body = response.body()
    val errorBody = response.errorBody()

    if (response.isSuccessful) {
        if (body == null) {
            Either.Left(
                DataException.Api(
                    errorCode = code,
                    title = ERROR_TITLE_GENERAL,
                    message = ERROR_BODY_NULL,
                )
            )
        } else {
            Either.Right(mapper(body))
        }
    } else {
        val error = errorBody?.errorConvertor()
        Either.Left(
            DataException.Api(
                title = ERROR_TITLE_GENERAL,
                errorCode = error?.first ?: code,
                message = error?.second ?: ERROR_RESPONSE_UNSUCCESSFUL,
            )
        )
    }
} catch (e: Exception) {
    e.printStackTrace()
    e.convertEither()
}

private fun Exception.convertEither(): Either<DataException, Nothing> = when (this) {
    is NetworkErrorException, is UnknownHostException -> Either.Left(DataException.Network)
    is SerializationException -> Either.Left(
        DataException.Api(
            message = this.message ?: ERROR_JSON_CONVERSION,
            title = ERROR_TITLE_GENERAL,
            errorCode = -1
        )
    )

    else -> Either.Left(
        DataException.Api(
            message = this.message ?: ERROR_MESSAGE_GENERAL,
            title = ERROR_TITLE_GENERAL,
            errorCode = -1
        )
    )
}

private fun ResponseBody?.errorConvertor(): Pair<Int, String>? {
    if (this == null) return Pair(0, "Null")
    return try {
        val jsonObject = JSONObject(this.string())
        val status = jsonObject.getJSONObject("status")
        Pair(
            status.getInt("error_code"),
            status.getString("error_message")
        )
    } catch (e: Exception) {
        Pair(0, e.message.toString())
    }
}