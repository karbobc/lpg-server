package com.hlqz.lpg.lanyang.service;

import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.interceptor.Intercept;
import com.hlqz.lpg.lanyang.interceptor.LyMApiServiceInterceptor;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.util.Map;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 兰洋系统移动端接口
 */
@RetrofitClient(baseUrl = "${LY_M_BASE_URL}")
@Intercept(handler = LyMApiServiceInterceptor.class)
public interface LyMApiService {

    /**
     * @param paramMap 通过 {{@link com.hlqz.lpg.lanyang.model.request.LyDeliveryParam}} 转成 Map
     * @return {"state":"","data":[{"Spec":"","Code":"","NotExists":"","NotPass":"","BID":"","CustTel":"","CustAddress":"",}],"errorMsg":""}
     */
    @FormUrlEncoded
    @POST("SaveSend.aspx")
    String delivery(@FieldMap Map<String, Object> paramMap);
}
