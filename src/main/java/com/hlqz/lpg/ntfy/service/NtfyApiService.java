package com.hlqz.lpg.ntfy.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.hlqz.lpg.ntfy.interceptor.NtfyApiServiceInterceptor;
import com.hlqz.lpg.ntfy.model.request.NtfySendParam;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * @author Karbob
 * @date 2024-01-17
 */
@RetrofitClient(baseUrl = "${NTFY_BASE_URL}")
@Intercept(handler = NtfyApiServiceInterceptor.class)
public interface NtfyApiService {

    @PUT("/")
    String send(@Body NtfySendParam param);
}
