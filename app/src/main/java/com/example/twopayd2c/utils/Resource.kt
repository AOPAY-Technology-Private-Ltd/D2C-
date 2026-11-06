package com.example.twopayd2c.utils



data class Resource<T>(
    val apiStatus: ApiStatus,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
//    class success<T>(data: T) : Resource<T>(data)
//    class error<T>(message: String, data: T? = null) : Resource<T>(data, message)
//    class loading<T> : Resource<T>()

        fun <T> success(data: T): Resource<T> =
            Resource(apiStatus = ApiStatus.SUCCESS, data = data, message = null)

        fun <T> error( message: String?): Resource<T> =
            Resource(apiStatus = ApiStatus.ERROR, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(apiStatus = ApiStatus.LOADING, data = data, message = null)
    }

}
