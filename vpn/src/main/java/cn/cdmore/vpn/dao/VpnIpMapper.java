package cn.cdmore.vpn.dao;

import cn.cdmore.vpn.vo.VpnIp;

public interface VpnIpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VpnIp record);

    int insertSelective(VpnIp record);

    VpnIp selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VpnIp record);

    int updateByPrimaryKey(VpnIp record);
    
    /**
     * 
    * @Description:根据IP地址检索IP是否已存在
    * @param record
    * @return
    * @return VpnIp    返回类型
     */
    VpnIp checkIpExist(String ip);
}