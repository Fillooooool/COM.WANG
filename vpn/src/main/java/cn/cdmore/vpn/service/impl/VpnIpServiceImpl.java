package cn.cdmore.vpn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cdmore.vpn.dao.VpnIpMapper;
import cn.cdmore.vpn.service.VpnIpService;
import cn.cdmore.vpn.vo.VpnIp;

@Service
public class VpnIpServiceImpl implements VpnIpService {

	@Autowired
	VpnIpMapper vpnIpMapper;
	
	@Override
	public int addIP(VpnIp vpnId) {
		int insertSelective = vpnIpMapper.insertSelective(vpnId);
		return insertSelective;
	}

	@Override
	public VpnIp checkIp(String vpnIp) {
		VpnIp checkIpExist = vpnIpMapper.checkIpExist(vpnIp);
		return checkIpExist;
	}

	@Override
	public int updateIp(VpnIp vpnIp) {
		int updateByPrimaryKeySelective = vpnIpMapper.updateByPrimaryKeySelective(vpnIp);
		return updateByPrimaryKeySelective;
	}

	@Override
	public VpnIp getIp(Integer id) {
		VpnIp selectByPrimaryKey = vpnIpMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}
	
}
