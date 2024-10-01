package com.hlqz.lpg.lanyang.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * @author Karbob
 * @date 2024-10-01
 *
 * 兰洋系统客户端接口
 */
@RetrofitClient(baseUrl = "${LY_C_BASE_URL}")
public interface LyCApiService {

    /**
     * 气瓶溯源轨迹
     * @param paramMap 通过 {{@link com.hlqz.lpg.lanyang.model.request.LyTraceParam}} 转成 Map
     * @return html
     */
    @GET("gj.aspx")
    String trace(@QueryMap(encoded = true) Map<String, Object> paramMap);
}
