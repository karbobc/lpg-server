package com.hlqz.lpg.lanyang.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.hlqz.lpg.lanyang.interceptor.LyServiceInterceptor;
import com.hlqz.lpg.lanyang.model.request.LyFetchCustomerParam;
import com.hlqz.lpg.lanyang.model.request.LyFetchCylinderParam;
import com.hlqz.lpg.lanyang.model.response.LyFetchCustomerResponse;
import com.hlqz.lpg.lanyang.model.response.LyFetchCylinderResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 兰洋系统 PC 端接口
 */
@RetrofitClient(baseUrl = "${LY_BASE_URL}")
@Intercept(handler = LyServiceInterceptor.class)
public interface LyApiService {

    /**
     * 分页查询客户信息
     */
    @POST("BaseCustomer/LoadDataByPage")
    LyFetchCustomerResponse fetchCustomer(@Body LyFetchCustomerParam param);

    /**
     * 分页查询钢瓶信息
     */
    @POST("DocVase/LoadDocVases")
    LyFetchCylinderResponse fetchCylinder(@Body LyFetchCylinderParam param);
}
