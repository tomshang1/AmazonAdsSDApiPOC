package org.example;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class FixContentTypeInterceptor implements Interceptor {
    @NotNull
    @Override public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();

        final String originalContextType = originalRequest.headers().get("Content-Type");
        if (originalContextType == null) {
            return chain.proceed(originalRequest);
        }
        final String newContextType = originalContextType.replace("; charset=utf-8", "");

        System.out.println("Original ContextType that was incorrectly changed by OkHttpClient: " + originalContextType
                + ", is now: " + newContextType);

        Request fixedRequest = originalRequest.newBuilder()
                .header("Content-Type", newContextType)
                .build();

        return chain.proceed(fixedRequest);
    }

}