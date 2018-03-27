package com.hiroshi.cimoc.core;

import java.util.List;
import org.json.JSONObject;
import org.json.;import okhttp3.Request;

/**
 * Created by FEILONG on 2018/3/27.
 */

public class mlab {
    String MangoApiUrl = "https://api.mlab.com/api/1";
    String MangoApiKey = "MlL4htTfzxCvICCTvq4eUmsHv0f7EtUp";//todo: change later

    public JSONObject GetDatabases(){
        Request request = new Request.Builder().url(MangoApiUrl).build();

        JSONObject result = new JSONObject();
        return result;
    }
}
