package com.example.bai2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://flow-fbmj.onrender.com/auth/local";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private String jwtToken;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy thông tin từ người dùng
        String username = "John";
        String password = "123";

        // Tạo request body với thông tin từ người dùng
        JSONObject requestBodyJson = new JSONObject();
        try {
            requestBodyJson.put("username", username);
            requestBodyJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, requestBodyJson.toString());

        // Tạo request
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .build();

        // Thực hiện request
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseJson = response.body().string();
                JSONObject responseObject = new JSONObject(responseJson);
                jwtToken = responseObject.getString("jwt");
                // Lưu accessToken vào SharedPreferences hoặc variable để sử dụng về sau
            } else {
                // Xử lý response không thành công
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
