package com.example.jeonghyeongkim.dong_geo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.User;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KakaoSignupActivity extends Activity {
    private static UserProfile buffer;
    long id;

     @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    protected void requestMe() { //유저의 정보를 받아오는 함수
        Log.e("login", "user request called");
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("login", "signedup error");
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환되며, 사용자 정보 저장되어있으면 자동으로 넘어감!
                Log.e("login", "success");
                Logger.d("UserProfile : " + userProfile);
<<<<<<< HEAD
                buffer=userProfile;

                id = userProfile.getId();

=======
                  buffer = userProfile;
>>>>>>> 635962487ed61f1cea8d3de4b726ad4bdd1cb568
                Intent intent = new Intent(KakaoSignupActivity.this, Main2Activity.class);
                intent.putExtra("id", userProfile.getId()); //세션 id
                intent.putExtra("nickname", userProfile.getNickname());
                intent.putExtra("kakaoimage", userProfile.getThumbnailImagePath());

                JSONObject jsonObject = MakeJson(userProfile.getId(),userProfile.getNickname());
                PostData postData =new PostData(null,jsonObject); // 유저 정보 데이터베이스 등록
                startActivity(intent);
                finish();
            }
        });
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


    private JSONObject MakeJson(long id, String nickname){
        JSONObject jsonObject = new JSONObject(); //파라미터 데이터

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String getTime = sdf.format(date); // 현재 날짜 가져오기

        try {
            jsonObject.put("kakao_id", id);
            jsonObject.put("user_nickname", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    public static long get_kakao_id() {
        return buffer.getId();
    }

    public static String get_kakao_nickname(){
//        Log.e("kakao_buffer", buffer.getNickname());
        if(buffer != null)
            return buffer.getNickname();
        else
            return "";
    }
}


