package `in`.company.contacts.repo.model

import androidx.annotation.Keep

@Keep
data class Either<out T>(val status: Status, val data: T?, val error: ApiError?) {

    companion object {
        fun <T> success(data: T?): Either<T> {
            return Either(Status.SUCCESS, data, null)
        }

        fun <T> error(error: ApiError, data: T?): Either<T> {
            return Either(Status.ERROR, data, error)
        }
    }
}