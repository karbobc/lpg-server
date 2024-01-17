package com.hlqz.lpg.ntfy.interceptor;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.hlqz.lpg.util.ConfigUtils;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * NTFY 接口拦截器, 补充鉴权 Header
 */
@Component
public class NtfyApiServiceInterceptor extends BasePathMatchInterceptor {

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        final Request request = chain.request();
        final String username = ConfigUtils.getNtfyUsername();
        final String password = ConfigUtils.getNtfyPassword();
        final Request newRequest = request.newBuilder()
            .url(request.url())
            .addHeader(HttpHeaders.AUTHORIZATION, Credentials.basic(username, password))
            .build();
        return chain.proceed(newRequest);
    }
}
