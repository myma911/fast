package cn.aaron911.admin.modules.sys.dao;


import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.admin.modules.sys.entity.SysLogEntity;

/**
 * 系统日志
 *
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
