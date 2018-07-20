package com.zimuka.peers.utils;

import com.zimuka.peers.dto.WechatTemplate;
import com.zimuka.peers.vo.AccessToken;
import com.zimuka.peers.vo.ApiTicket;
import com.zimuka.peers.vo.WechatOpenId;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

public class WxTemplateUtil {
	
	//通过code获取openId接口地址（GET）
	public final static String open_id_url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=APPSECRET&js_code=CODE&grant_type=authorization_code";
	
	// 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //发送 模板消息（Post）
    public final static String sen_templeta_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
    
    //订单消息推送（Post）
    public final static String exp_back_websocket = "https://expai.net/shopOrder/websocket?uid=UID&orderId=ORDERID";

    public final static String exp_take_out_websocket = "https://expai.net/elemeOrder/outOrderWebsocket?uid=UID&orderId=ORDERID";

    //通过access_token调用获取api_ticket接口
    public final static String api_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";
    
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {
            System.out.println("https request error:{}" + e);
        }
        return jsonObject;
    }
    
    /**
     * 获取openId
     * @param appid
     * @param appsecret
     * @param code
     * @return
     */
    public static WechatOpenId getOpenIdAndSessionKey(String appid, String appsecret, String code) {
    	WechatOpenId wechatOpenId = null;
    	String requestUrl = open_id_url.replace("APPID", appid).replace("APPSECRET", appsecret).replace("CODE", code);
    	
    	JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
    	if (null != jsonObject) {
    		try {
    			wechatOpenId = new WechatOpenId();
    			wechatOpenId.setOpenId(jsonObject.getString("openid"));
    			wechatOpenId.setSessionKey(jsonObject.getString("session_key"));
    		} catch(JSONException e) {
    			wechatOpenId = null;
    			System.out.println("获取openId失败");
    			//throw new PeerProjectException("获取openId失败");
    		}
    	}
    	return wechatOpenId;
    }
    
    /**
     * 获取access_token
     * @param appid
     * @param appsecret
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
        // 如果请求成功
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccessToken(jsonObject.getString("access_token"));
                accessToken.setExpiresin(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                System.out.println("获取token失败");
            }
        }
        return accessToken;
    }
    
    public static JSONObject sendTmplMsg(WechatTemplate wechatTemplate, String accessToken) {


        String url = sen_templeta_url.replace("ACCESS_TOKEN", accessToken);

        // 将模板数据对象转换成json字符串
        String weTemplate = JSONObject.fromObject(wechatTemplate).toString();

        //发送请求
        JSONObject result = httpRequest(url, "POST", weTemplate);

        if (result != null) {
            return result;
        } else
            return null;

    }
    
    public static void getBackWebSocket(Integer shopId, String orderId) {
    	String requestUrl = exp_back_websocket.replace("UID", shopId.toString()).replace("ORDERID", orderId);
    	httpRequest(requestUrl, "GET", null);
    }

    public static void getExpElemeOrderWebSocket(Integer shopId, String orderId) {
        String requestUrl = exp_take_out_websocket.replace("UID", shopId.toString()).replace("ORDERID", orderId);
        httpRequest(requestUrl, "GET", null);
    }
    
    public static ApiTicket getApiTicket(String accessToken) {
    	
    	ApiTicket apiTicket = null;
    	String requestUrl = api_ticket_url.replace("ACCESS_TOKEN", accessToken);
    	JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
    	
    	if (null != jsonObject) {
            try {
            	apiTicket = new ApiTicket();
            	apiTicket.setTicket(jsonObject.getString("ticket"));
            	apiTicket.setExpiresin(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                System.out.println("获取apiTicket失败");
            }
        }
    	return apiTicket;
    	
    }
}
