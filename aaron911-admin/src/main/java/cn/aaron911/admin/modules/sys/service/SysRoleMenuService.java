package cn.aaron911.admin.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.modules.sys.dao.SysRoleMenuDao;
import cn.aaron911.admin.modules.sys.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 *
 */
@Service
public class SysRoleMenuService extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> {

	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(Long menuId : menuIdList){
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);

			this.save(sysRoleMenuEntity);
		}
	}

	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
