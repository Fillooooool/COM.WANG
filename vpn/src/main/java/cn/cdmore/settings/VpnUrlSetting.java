package cn.cdmore.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vpnUrl", ignoreNestedProperties = false)
public class VpnUrlSetting {

	@Override
	public String toString() {
		return "VpnUrl [url=" + url + "]";
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
