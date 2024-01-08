package com.hlqz.lpg.lanyang.interceptor;

import com.github.lianjiatech.retrofit.spring.boot.interceptor.BasePathMatchInterceptor;
import com.hlqz.lpg.util.ConfigUtils;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 兰洋系统 PC 端接口拦截器, 补充鉴权 Cookie
 */
@Component
public class LyMServiceInterceptor extends BasePathMatchInterceptor {

    @Override
    protected Response doIntercept(Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url();
        HttpUrl newUrl = url.newBuilder()
            .addQueryParameter("optId", ConfigUtils.getLyOptId())
            .addQueryParameter("sectionId", ConfigUtils.getLySectionId())
            .build();
        Request newRequest = request.newBuilder()
            .url(newUrl)
            .build();
        return chain.proceed(newRequest);
    }
}
