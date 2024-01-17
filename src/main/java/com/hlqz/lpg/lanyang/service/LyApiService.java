package com.hlqz.lpg.lanyang.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.hlqz.lpg.lanyang.interceptor.LyApiServiceInterceptor;
import com.hlqz.lpg.lanyang.model.common.LyCustomer;
import com.hlqz.lpg.lanyang.model.common.LyCylinder;
import com.hlqz.lpg.lanyang.model.request.LyFetchByPageParam;
import com.hlqz.lpg.lanyang.model.response.LyPageResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 兰洋系统 PC 端接口
 */
@RetrofitClient(baseUrl = "${LY_BASE_URL}")
@Intercept(handler = LyApiServiceInterceptor.class)
public interface LyApiService {

    /**
     * 分页查询客户信息
     */
    @POST("BaseCustomer/LoadDataByPage")
    LyPageResponse<LyCustomer> fetchCustomer(@Body LyFetchByPageParam param);

    /**
     * 分页查询钢瓶信息
     */
    @POST("DocVase/LoadDocVases")
    LyPageResponse<LyCylinder> fetchCylinder(@Body LyFetchByPageParam param);
}
