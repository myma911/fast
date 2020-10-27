package cn.aaron911.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.modules.sys.entity.SysDictEntity;

/**
 * 数据字典
 *
 */
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {
	
}
