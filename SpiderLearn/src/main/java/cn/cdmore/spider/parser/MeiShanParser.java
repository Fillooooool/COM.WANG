package cn.cdmore.spider.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.cdmore.spider.vo.SpiderRec;

public class MeiShanParser implements SpiderParser<SpiderRec> {
	private List<String> authors;

	public MeiShanParser(List<String> authors) {
		this.authors = authors;
	}

	public MeiShanParser(String... authors) {
		if (authors != null) {
			this.authors=new ArrayList<>();
		}
		for (String author : authors) {
			this.authors.add(author);
		}
	}

	private boolean isAuthor(String author) {
		for (String au : authors) {
			if (au.equals(author)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<SpiderRec> parser(String html) {
		List<SpiderRec> rst = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Elements trs = doc.select("#threadlisttableid tbody tr");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		trs.forEach(tr -> {
			if (tr == null) {
				return;
			}
			Element authorA = tr.selectFirst("td.by cite a");
			if (authorA != null) {
				String author = authorA.text();
				if (isAuthor(author)) {
					String publishTime = null;
					Element span = tr.selectFirst("td.by em span span");
					if (span != null) {
						publishTime = span.attr("title");
					} else {
						span = tr.selectFirst("td.by em span");
						if (span == null) {
							return;
						}
						publishTime = span.text();
					}
					Element a = tr.selectFirst("th a.s.xst");
					String url = a.attr("href");
					String title = a.text();
					SpiderRec rec = new SpiderRec();
					rec.setTitle(title);
					rec.setPublisher(author);
					Date date = null;
					try {
						date = sdf.parse(publishTime);
					} catch (Exception e) {
						e.printStackTrace();
						date = new Date();
					}
					rec.setPublishTime(date);
					rec.setUrl(url);
					rec.setCreateTime(new Date());
					rst.add(rec);
				}
			}
		});
		return rst;
	}
}
