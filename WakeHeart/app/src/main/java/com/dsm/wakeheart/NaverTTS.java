package com.dsm.wakeheart;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by parktaeim on 2017. 11. 4..
 */

public class NaverTTS {

    public static void main(String[] args) {
        final String clientId = "oPDj1y9Xk6B_IakJkQ0J";//애플리케이션 클라이언트 아이디값";
        final String clientSecret = "yJaFJ2L0Da";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(args[0], "UTF-8"); // 13자
            Log.d("tts args[0]", args[0]);
            String apiURL = "https://openapi.naver.com/v1/voice/tts.bin";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "speaker=mijin&speed=0&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            Log.d("tts res code =========", String.valueOf(responseCode));

            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];

                File dir = new File(Environment.getExternalStorageDirectory()+"/", "Naver");
                if(!dir.exists()){
                    dir.mkdirs();
                }

                // 랜덤한 이름으로 mp3 파일 생성
                String tempname = "ttsFile";
                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "Naver/"+tempname + ".mp3");
                f.createNewFile();

                OutputStream outputStream = new FileOutputStream(f);
                while ((read = is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();

                String filePath = Environment.getExternalStorageDirectory() + File.separator + "Naver/" + tempname + ".mp3";
                Log.d("filePath=========", filePath.toString());
                MediaPlayer ttsPlayer = new MediaPlayer();
                ttsPlayer.setDataSource(filePath);
                ttsPlayer.prepare();
                ttsPlayer.start();

                Log.d("ttsPlayer!!!!", "!!!!!");


            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());

            }
        } catch (
                Exception e)

        {
            System.out.println(e);
        }
    }
}
