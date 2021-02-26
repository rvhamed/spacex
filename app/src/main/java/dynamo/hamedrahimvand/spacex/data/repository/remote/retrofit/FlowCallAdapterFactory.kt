package dynamo.hamedrahimvand.spacex.data.repository.remote.retrofit

import dynamo.hamedrahimvand.spacex.data.model.retrofit.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class FlowCallAdapterFactory @Inject constructor() : CallAdapter.Factory() {
    companion object {
        @JvmStatic @JvmName("create")
        operator fun invoke() = FlowCallAdapterFactory()
    }

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Flow::class.java != getRawType(returnType)) {
            return null
        }
        check(returnType is ParameterizedType) { "Flow return type must be parameterized as Flow<Foo> or Flow<out Foo>" }
        val responseType = getParameterUpperBound(0, returnType)

        val rawDeferredType = getRawType(responseType)
        return if (rawDeferredType == Response::class.java) {
            if (responseType !is ParameterizedType) {
                throw IllegalStateException(
                    "Response must be parameterized as Response<Foo> or Response<out Foo>")
            }
            ResponseCallAdapter<Any>(getParameterUpperBound(0, responseType))
        } else {
            BodyCallAdapter<Any>(responseType)
        }
    }

    private class BodyCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Flow<Resource<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Flow<Resource<T>> {
            val flow = MutableStateFlow<Resource<T>>(Resource.loading())

            if (call.isCanceled) {
                return flow
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (call.isCanceled) return
                    flow.tryEmit(Resource.error(t, null))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (call.isCanceled) return

                    if (response.isSuccessful) {
                        flow.tryEmit(Resource.success(response.body()))
                    } else {
                        flow.tryEmit(Resource.error(HttpException(response), null))
                    }
                }
            })

            return flow
        }
    }

    private class ResponseCallAdapter<T>(
        private val responseType: Type
    ) : CallAdapter<T, Flow<Resource<T>>> {

        override fun responseType() = responseType

        override fun adapt(call: Call<T>): Flow<Resource<T>> {
            val flow = MutableStateFlow<Resource<T>>(Resource.loading())

            if (call.isCanceled) {
                return flow
            }

            call.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    if (call.isCanceled) return
                    flow.tryEmit(Resource.error(t, null))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (call.isCanceled) return
                    flow.tryEmit(Resource.success(response.body()))
                }
            })

            return flow
        }
    }
}