package professional.wellness.health.com.employeeapp.Rest;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Navit on 20-03-2017.
 */

public class RESTInteraction {
    private static RESTInteraction _instance;
    private DefaultHttpClient httpclient;
    private HttpPost postRequest;
    private HttpResponse httpResponse;
    private HttpEntity httpEntity;
    Context mContext;
    private ArrayList<NameValuePair> nameValuePairs;
    private HttpGet getRequest;
    private EncryptionClass encryptionClass;

    public RESTInteraction(Context ctx) {
        mContext = ctx;
    }

    public static RESTInteraction getInstance(Context ctx) {
        if (_instance == null) {
            _instance = new RESTInteraction(ctx);
        }
        return _instance;
    }

    private void initializeHttpClient() {
        encryptionClass = EncryptionClass.getInstance(mContext);
        httpclient = new DefaultHttpClient();
        nameValuePairs = new ArrayList<NameValuePair>(2);
    }

    private void setConnectionTimeout(int timeout) {
        HttpParams httpParams = httpclient.getParams();
        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
    }

    private String sendHttpRequest(String url) {
        String jSonStr = null;
        StringBuilder builder = new StringBuilder();
        try {
             httpclient = new DefaultHttpClient();
             postRequest = new HttpPost(url);
            setConnectionTimeout(Constant.WEB_CONNECTION_TIME_OUT);
            postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
            httpResponse = httpclient.execute(postRequest);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));

                while ((jSonStr = reader.readLine()) != null) {
                    builder.append(jSonStr);
                }

            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    private String sendHttpGetRequest(String url) {
        String jSonStr = null;
        try {
            httpclient = new DefaultHttpClient();
            getRequest = new HttpGet(url);
            httpResponse = httpclient.execute(getRequest);
            httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                jSonStr = EntityUtils.toString(httpEntity);
                return jSonStr;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jSonStr;
    }


    public String sendLoginRequest(String url, String request) {
         initializeHttpClient();
         Log.d("Tag sendLoginRequest: ", request);
         try {
             nameValuePairs.add(new BasicNameValuePair("request", request));
             // return String.valueOf(request);
            String encryptedData = (sendHttpRequest(url));
            Log.d("Tag encryptedData: ", encryptedData);
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(encryptedData, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(encryptedData)));
            }
             Log.d("Tag clean: ", clean);
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

         } catch (Exception e) {
             e.getMessage();
         }
         return "Fail";
    }

    public String senddeleteCertificateRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendForget(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);

            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }



    public String getProfileRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendUpoladCertificateRequest(String url, String request,String image) {
        httpclient = new DefaultHttpClient();
        postRequest = new HttpPost(url);

        try {

            MultipartEntity reqEntity = new MultipartEntity();
            File file = new File(image);
            if (file.exists()) {
                FileBody fileBody = new FileBody(file);
                reqEntity.addPart("file", fileBody);
            }
            Charset chars = Charset.forName("UTF-8");
            reqEntity.addPart("request", new StringBody(request, chars));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(postRequest);
            HttpEntity resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);

            String decrypted = new String(encryptionClass.decrypt(response_str));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "fail";
       /* try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
*/
    }


    public String sendUpdateProfileRequest(String url, String request,String image) {
        httpclient = new DefaultHttpClient();
        postRequest = new HttpPost(url);

        try {

            MultipartEntity reqEntity = new MultipartEntity();
            File file = new File(image);
            if (file.exists()) {
                FileBody fileBody = new FileBody(file);
                reqEntity.addPart("image", fileBody);
            }
            Charset chars = Charset.forName("UTF-8");
            reqEntity.addPart("request", new StringBody(request, chars));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(postRequest);
            HttpEntity resEntity = response.getEntity();
            final String response_str = EntityUtils.toString(resEntity);

            String decrypted = new String(encryptionClass.decrypt(response_str));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "fail";
       /* try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
*/
    }


    public String getCertificateRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }
    public String getallannouncement(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }



    public String getMyClinicMonth(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String getMyClinicUpcoming(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String getMyClinicDay(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }



    public String getMyClinicWeek(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String getMyClinicOfflineData(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }





    public String sendUpdateClinicMilage(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }



    public String getHomeFeed(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String sendSyncDataOfflineFeed(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }



    public String getallLatestannouncement(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }

    public String sendDeactiveRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }

    public String sendDeactiveUpcomingRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String sendUpdateProfileRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }

    public String sendUpdateTimeZoneRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }

    public String sendUpdateTimeFormatRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendUpdateSystemCalanderRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendUpdateGroupByNotificationRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }



    public String sendChangePasswordRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendUserEmailNotifcationRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendSecurityPasswordRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendUserPushNotifcationSettingRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendUpadteSystemCalanderStatusRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendChangePrepTimeRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendDisableEmailConfirmationRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendClockinUpdateRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendClockOutUpdateRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }

    public String sendClockInReminderRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


    public String sendClockOutReminderRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }



    public String sendSoicalSecuirtyNumberRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }

    public String sendUpdateLeaveLocationRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }



    public String sendDefaultPrepTimeRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }





    public String sendTimeSheetRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }



    public String sendChangeEmailRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }

    public String sendChangePhoneNumberRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }



    public String getAvailableRequest(String url, String request) {
        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String getAvailableUpcomingRequest(String url, String request) {

        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String sendAcceptRejectClinicRequest(String url, String request) {

        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String sendSwipeOthertClinicRequest(String url, String request) {

        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }



    public String sendUpdateClockinRequest(String url, String request) {

        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";
    }


    public String sendUpdatelocationAsync(String url, String request) {

        initializeHttpClient();
        try {
            nameValuePairs.add(new BasicNameValuePair("request", request));
            String encryptedData = (sendHttpRequest(url));
            String decrypted = new String(encryptionClass.decrypt(encryptedData));
            String clean = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                clean = String.valueOf(Html.fromHtml(decrypted, Html.FROM_HTML_MODE_COMPACT));
            } else {
                clean = String.valueOf((Html.fromHtml(decrypted)));
            }
            JSONObject jsonObject = new JSONObject(clean);
            if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                return String.valueOf(jsonObject);
            }
            if (jsonObject.has("status") && jsonObject.getString("status").equals("error")) {
                return String.valueOf(jsonObject);
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return "Fail";

    }


}
