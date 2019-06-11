package cn.cdmore.settings;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spider", ignoreNestedProperties = false)
public class SpiderSettings {
	public Integer getSleepStartTime() {
		return sleepStartTime;
	}

	public void setSleepStartTime(Integer sleepStartTime) {
		this.sleepStartTime = sleepStartTime;
	}

	public Integer getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(Integer sleepTime) {
		this.sleepTime = sleepTime;
	}

	public Integer getPerListNum() {
		return perListNum;
	}

	public void setPerListNum(Integer perListNum) {
		this.perListNum = perListNum;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	private Integer sleepStartTime;
	private Integer sleepTime;
	private Integer perListNum;
	private List<String> authors;
	private List<String> urls;
}
