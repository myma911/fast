package cn.aaron911.admin.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.modules.sys.dao.SysUserRoleDao;
import cn.aaron911.admin.modules.sys.entity.SysUserRoleEntity;


/**
 * 用户与角色对应关系
 *
 */
@Service
public class SysUserRoleService extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> {

	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		for(Long roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			this.save(sysUserRoleEntity);
		}

	}

	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
