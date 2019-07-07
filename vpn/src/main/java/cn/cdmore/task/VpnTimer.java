package cn.cdmore.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.cdmore.settings.VpnUrlSetting;
import cn.cdmore.utils.HttpUtil;
import cn.cdmore.vpn.service.VpnIpService;
import cn.cdmore.vpn.vo.VpnIp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @Description: 定时获取公司最新的公网IP
 * @author wby
 * @date 2019年6月30日 下午8:47:32
 *
 */

@Component
public class VpnTimer {

	private static final Logger logger = LoggerFactory.getLogger(VpnTimer.class);
	private File file = new File("C:\\Windows\\System32\\drivers\\etc\\hosts");
	@Autowired
	private VpnUrlSetting vpnUrlSetting;
	@Autowired
	private VpnIpService vpnIpService;

	@Scheduled(cron = "${task.vpnCheck}")
	@Async
	public void getVpnIp() {
		String html = HttpUtil.getHtml(vpnUrlSetting.getUrl());
		logger.info("获取到最新IP={}", html);
		VpnIp ip = vpnIpService.getIp(1);

		if (ip != null && ip.getVpnIp().equals(html)) {
			logger.debug("目前已更新到最新的IP地址IP={}，无需更新", html);
		} else {
			ip.setVpnIp(html);
			ip.setCreateTime(new Date());
			vpnIpService.updateIp(ip);
			logger.info("更新到最新IP={}", html);

			String s;
			StringBuffer sb = new StringBuffer();
			try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr);) {
				while ((s = br.readLine()) != null) {
					if (s.indexOf("cdmore") == -1) {
						sb.append(s).append("\n");
					}
				}
				sb.append(html + " cdmore\r\n");
				file.delete();
			} catch (IOException e) {
				logger.info("读取host文件出现问题，请检查相关权限");
				e.printStackTrace();
			}
			try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw);) {
				fw.write(sb.toString());
			} catch (IOException e) {
				logger.info("写入host文件出现问题");
				e.printStackTrace();
			}
		}
	}
}
