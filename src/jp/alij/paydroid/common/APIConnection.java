package jp.alij.paydroid.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import jp.alij.paydroid.data.TransactionRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

/**
 * アナザーレーン（株）
 * http://www.alij.ne.jp
 * @author canu johann
 * 
 * API(gateway)に接続
 *
 */
public class APIConnection {
	
	public final String TAG = this.getClass().getSimpleName(); 
	
	public final static String SITE_ID_PARAM = "SiteId";
	public final static String SITE_PASS_PARAM = "SitePass";
	public final static String AMOUNT_PARAM = "Amount";
	public final static String CARD_NAME_PARAM = "cardName";
	public final static String CARD_NO_PARAM = "cardNo";
	public final static String CARD_MONTH_PARAM = "cardMonth";
	public final static String CARD_YEAR_PARAM = "cardYear";
	
	public final static String CUST_ID_PARAM = "CustomerId";
	public final static String CUST_PASS_PARAM = "CustomerId";
	public final static String ZIP_PARAM = "CustomerId";
	public final static String CAPITAL_PARAM = "capital";
	public final static String ADR1_PARAM = "adr1";
	public final static String ADR2_PARAM = "adr2";
	public final static String NAME_PARAM = "name";
	public final static String TEL_PARAM = "tel";
	public final static String MAIL_PARAM = "mail";
	public final static String NOTE_PARAM = "note";
	public final static String TRANS_ID_PARAM = "TransactionId";
	public final static String LANGUAGE_PARAM = "country";
	public final static String OTHER_PARAM = "other";
	
	
	
	/*
	 * 決済実行
	 */
	public String doSettlement(HashMap<String, String> mapConditions){
		String url = Consts.API_SETTLEMENT_URI;		
		return queryAPIByPost(url,mapConditions);
	}
	
	
	/*
	 * APIに接続（POSTのみ）
	 */
	private String queryAPIByPost(String action, HashMap<String, String> mapConditions) {
				
		// HTTPリクエスト
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httppost = new HttpPost(action);
		httppost.setHeader("User-Agent", "another-lane-android");
		
		//パラメータ設定
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> it = mapConditions.entrySet().iterator();		
		while (it.hasNext()) {
			Entry<String,String> pairs = (Entry<String,String>) it.next();
			
			//値があれば、追加
			if(pairs.getValue()!=null && pairs.getValue()!= "")
			params.add(new BasicNameValuePair(pairs.getKey().toString(), pairs.getValue().toString()));
		}
		
		
		try {
			
			//リクエスト送信
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			urlEncodedFormEntity.setContentEncoding(HTTP.UTF_8);
			httppost.setEntity(urlEncodedFormEntity);
				
			HttpResponse response = httpclient.execute(httppost);	
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), HTTP.UTF_8));
			return reader.readLine();
		
		} catch (ClientProtocolException e) {
			Log.e(TAG, "Client protocol exception occurred when calling API.");
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			Log.e(TAG, "API is unreachable. Please check it.");
			e.printStackTrace();
			return null;
		}
				
	

	}
	
}
