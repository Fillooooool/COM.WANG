package cn.cdmore.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

	private static OkHttpClient client = new OkHttpClient();

	public static String getHtml(String url, String encoding) {
		Request request = new Request.Builder().url(url).get().build();
		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new RuntimeException("请求失败，相应失败");
			}
			return new String(response.body().bytes(), encoding);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("请求失败,IO异常");
		}
	}
	
	public static String getHtml(String url) {
		return getHtml(url,"UTF-8");
	}
	
	public static void main(String []args) {
		String html = getHtml("http://118.24.125.253/ipcollector/query");
		System.out.println(html);
	}
}
