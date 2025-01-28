package com.hlqz.lpg.lanyang.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.hlqz.lpg.lanyang.interceptor.LyNewApiServiceInterceptor;
import com.hlqz.lpg.lanyang.model.common.LyBoxDocking;
import com.hlqz.lpg.lanyang.model.response.LyNewBaseResponse;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * @author Karbob
 * @date 2025-01-28
 *
 * 兰洋新系统 PC 端接口
 */
@RetrofitClient(baseUrl = "${LY_NEW_BASE_URL}")
@Intercept(handler = LyNewApiServiceInterceptor.class)
public interface LyNewApiService {

    /**
     * 钢瓶上传记录
     * @param paramMap 通过 {@link com.hlqz.lpg.lanyang.model.request.LyBoxDockingParam} 转成 Map
     */
    @GET("GasCall/ProductionManage/BotDocking.ashx")
    LyNewBaseResponse<List<LyBoxDocking>> botDocking(@QueryMap(encoded = true) Map<String, Object> paramMap);
}
