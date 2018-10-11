package com.redhelmet.alert2me.global;

import com.redhelmet.alert2me.data.remote.NetworkError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {
    private String message;
    private String url;
    private Response response;
    private Kind kind;
    private Throwable exception;
    private Retrofit retrofit;

    private NetworkError errorData;

    public RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        this.message = message;
        this.url = url;
        this.response = response;
        this.kind = kind;
        this.exception = exception;
        this.retrofit = retrofit;
    }

    public static RetrofitException httpError(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        RetrofitException error = new RetrofitException(message, url, response, Kind.HTTP, null, retrofit);
        error.deserializeServerError();
        return error;
    }

    public static RetrofitException httpErrorWithObject(String url, Response response, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        RetrofitException error = new RetrofitException(message, url, response, Kind.HTTP_422_WITH_DATA, null, retrofit);
        error.deserializeServerError();
        return error;
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    private void deserializeServerError() {
        if (response != null && response.errorBody() != null) {
            try {
                errorData = getErrorBodyAs(NetworkError.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * HTTP response body converted to specified `type`. `null` if there is no
     * response.
     *
     * @throws IOException if unable to convert the body to the specified `type`.
     */
    private <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (response == null || response.errorBody() == null || retrofit == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = retrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(response.errorBody());
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public NetworkError getErrorData() {
        return errorData;
    }

    public void setErrorData(NetworkError errorData) {
        this.errorData = errorData;
    }

    public enum Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        HTTP_422_WITH_DATA,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}
