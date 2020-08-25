package cn.aaron911.modules.sys.service;


import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.aaron911.common.utils.PageUtils;
import cn.aaron911.modules.sys.entity.SysRoleEntity;


/**
 * 角色
 *
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
