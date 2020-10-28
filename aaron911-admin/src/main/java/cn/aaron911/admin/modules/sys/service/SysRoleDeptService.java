package cn.aaron911.admin.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.modules.sys.dao.SysRoleDeptDao;
import cn.aaron911.admin.modules.sys.entity.SysRoleDeptEntity;


/**
 * 角色与部门对应关系
 *
 */
@Service("sysRoleDeptService")
public class SysRoleDeptService extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity>  {

	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
		//先删除角色与部门关系
		deleteBatch(new Long[]{roleId});

		if(deptIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		for(Long deptId : deptIdList){
			SysRoleDeptEntity sysRoleDeptEntity = new SysRoleDeptEntity();
			sysRoleDeptEntity.setDeptId(deptId);
			sysRoleDeptEntity.setRoleId(roleId);

			this.save(sysRoleDeptEntity);
		}
	}

	public List<Long> queryDeptIdList(Long[] roleIds) {
		return baseMapper.queryDeptIdList(roleIds);
	}

	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
