package br.com.ridesforme.ridesforme;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.net.URL;

/**
 * Created by Felipe on 27/08/2015.
 */
public class LoginControllerTask extends AsyncTask<String, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM) //this is what I say in my POSTman (Chrome plugin)
                    .addFormDataPart("login",params[0])
                    .addFormDataPart("senha",params[1])
                    .build();
            Request request = new Request.Builder()
                    .url("http://187.59.115.154:8080/rpg/usuario/login")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                String responseString = response.body().string();
                response.body().close();
                Log.v("a", responseString);
                return Boolean.parseBoolean(responseString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
