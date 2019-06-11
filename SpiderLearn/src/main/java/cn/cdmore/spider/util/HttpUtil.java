package cn.cdmore.spider.util;


import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
	
	private static OkHttpClient client=new OkHttpClient();
	
	public static String getHtml(String url, String encoding) {
		Request request = new Request.Builder().url(url).get().build();
		try(Response response=client.newCall(request).execute()){
			if (!response.isSuccessful()) {
				throw new RuntimeException("请求失败，响应失败");
			}
			return new String(response.body().bytes(), encoding);
		}catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("请求失败,Io异常");
		}
	}
}
