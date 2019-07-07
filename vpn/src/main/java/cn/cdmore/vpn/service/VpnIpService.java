package cn.cdmore.vpn.service;

import cn.cdmore.vpn.vo.VpnIp;

public interface VpnIpService {
	
	int addIP(VpnIp vpnIp);

	VpnIp getIp(Integer id);
	
	VpnIp checkIp(String vpnIp);
	
	int updateIp(VpnIp vpnIp);
}
