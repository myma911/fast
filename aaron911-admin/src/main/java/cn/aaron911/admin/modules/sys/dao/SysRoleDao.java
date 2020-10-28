package cn.aaron911.admin.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.admin.modules.sys.entity.SysRoleEntity;

/**
 * 角色管理
 *
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	

}
