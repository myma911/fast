package cn.aaron911.admin.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.admin.modules.sys.entity.SysRoleDeptEntity;

/**
 * 角色与部门对应关系
 *
 */
@Mapper
public interface SysRoleDeptDao extends BaseMapper<SysRoleDeptEntity> {
	
	/**
	 * 根据角色ID，获取部门ID列表
	 */
	List<Long> queryDeptIdList(Long[] roleIds);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
