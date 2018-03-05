package com.xsl.photos.util;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import org.json.JSONException;

import java.io.InputStream;

/**
 * Created by msi- on 2018/3/3.
 */
public class FileUtils {
    private static final String ACCESS_KEY = "ZMgW_Dd3U52Msqm_W1EuGsvDnPcklc-VBhVC79Yb";
    private static final String SECRET_KEY = "HFYWkWCdotJRd-wEvll56o8DYyNis3UAiv7parCm";
    private static final String BUCKET_NAME = "photos";

    public static void upload(InputStream reader, String fileName) {
        Mac mac = new Mac(ACCESS_KEY,SECRET_KEY);
        PutPolicy putPolicy = new PutPolicy(BUCKET_NAME);
        try {
            String uptoken = putPolicy.token(mac);
            IoApi.Put(uptoken, fileName,reader,null);
        } catch (AuthException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String key) {
        Mac mac = new Mac(ACCESS_KEY,SECRET_KEY);
        RSClient client = new RSClient(mac);
        client.delete(BUCKET_NAME, key);
    }
}
