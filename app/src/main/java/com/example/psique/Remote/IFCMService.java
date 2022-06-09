package com.example.psique.Remote;

import com.example.psique.Models.FCMResponse;
import com.example.psique.Models.FCMSendData;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA0g_bR4I:APA91bHpYr4WCRbjrNPhymBekzZp3e8NP9extAU4WYnWuy1ea2Q2xwrAYxdfauMOzYRfk90fZ8wna3m_7xU8W0kYGis_r32VYvh9BG6liANIaznNf2kbg1E9WOfmyuFky2N3S-gp_R6W"
    })
    @POST("fcm/send")
    Observable<FCMResponse> sendNotification(@Body FCMSendData body);
}
