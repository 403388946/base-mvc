package com.base.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientUtil工具类
 * @author YiMing
 *
 */
public class HttpClientUtil {
	// 创建默认的httpClient实例.
	public static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

	public static Object get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpEntity entity = null;
		String result = "";
		try {
			// 创建一个GET请求
			HttpGet get = new HttpGet(url);
			// 执行get请求
			CloseableHttpResponse response = httpclient.execute(get);
			// 获取响应的实体
			entity = response.getEntity();
			// 响应内容
			result = EntityUtils.toString(entity,"UTF-8");

			response.close();
			httpclient.close();
		} catch (Exception e) {
			LOGGER.error("http-client-get-error : {}", ErrorMessageUtil.format(e));
		}
		return result;
	}

	public static Object post(String url, Map<String, Object> map) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = "";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		for (String key : map.keySet()) {
			if (map.get(key) != null) {
				param.add(new BasicNameValuePair(key, map.get(key).toString()));
			}
		}
		UrlEncodedFormEntity formEntity = null;
		try {
			formEntity = new UrlEncodedFormEntity(param, "UTF-8");
			post.setEntity(formEntity);
			CloseableHttpResponse response = httpclient.execute(post);
			HttpEntity entity = response.getEntity();
			// 响应内容
			result = EntityUtils.toString(entity,"UTF-8");
			response.close();
			httpclient.close();
		} catch (Exception e) {
			LOGGER.error("http-client-get-error : {}", ErrorMessageUtil.format(e));
		}

		return result;
	}

	public static Object postJSON(String url, String paramJSONString) throws Exception {
		return postJSON(url, paramJSONString, "");
	}

	public static Object postJSON(String url, String paramJSONString, String authorization) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = "";
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type","application/json");
		post.addHeader("Accept","application/json");
		post.addHeader("Accept-Charset","UTF-8");
		post.addHeader("contentType","UTF-8");
		post.addHeader("Authorization",authorization);
		StringEntity formEntity = new StringEntity(paramJSONString, "UTF-8");
		post.setEntity(formEntity);
		CloseableHttpResponse response = httpclient.execute(post);
		HttpEntity entity = response.getEntity();
		// 响应内容
		result = EntityUtils.toString(entity,"UTF-8");
		response.close();
		httpclient.close();
		return result;
	}
}
