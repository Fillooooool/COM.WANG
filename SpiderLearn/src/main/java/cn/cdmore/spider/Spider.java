package cn.cdmore.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import cn.cdmore.spider.parser.MeiShanParser;
import cn.cdmore.spider.parser.SpiderParser;
import cn.cdmore.spider.util.HttpUtil;
import cn.cdmore.spider.vo.SpiderRec;

public class Spider {
	private SpiderParser<SpiderRec> parser;

	private List<String> urls;

	private String encoding;

	private Integer sleepTime;

	private Integer sleepStartIime;

	public Spider(SpiderParser<SpiderRec> parser, List<String> urls, Integer sleepTime, Integer sleepStartIime) {
		this(parser, urls, sleepTime, sleepStartIime, "UTF-8");
	}

	public Spider(SpiderParser<SpiderRec> parser, List<String> urls, Integer sleepTime, Integer sleepStartIime,
			String encoding) {
		super();
		this.parser = parser;
		this.urls = urls;
		this.sleepTime = sleepTime;
		this.sleepStartIime = sleepStartIime;
		this.encoding = encoding;
	}

	private void rest() {
		// 随机等待时间，避免被发现是爬虫
		Random r = new Random();
		try {
			Thread.sleep(r.nextInt(sleepStartIime) + sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void show() {
		for (String url : urls) {
			String html = null;
			html = HttpUtil.getHtml(url, encoding);
			List<SpiderRec> parser2 = parser.parser(html);
			for (SpiderRec spiderRec : parser2) {
				System.out.println(spiderRec);
			}
			rest();
		}
	}

	public static void main(String[] args) {
		List<String> urls = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			urls.add(String.format("https://bbs.meishanren.com/forum-365-%d.html", i));
		}
		Spider spider = new Spider(new MeiShanParser("专筑装饰张雨欣"), urls, 200, 300, "GBK");
		spider.show();
	}
}
