package cn.cdmore.spider.parser;

import java.util.List;

public interface SpiderParser <T>{
	List<T> parser(String html);
}
