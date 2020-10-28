package cn.aaron911.admin.modules.oss.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.admin.modules.oss.entity.SysOssEntity;

/**
 * 文件上传
 *
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
