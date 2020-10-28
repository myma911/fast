package cn.aaron911.admin.modules.sys.service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.common.annotation.DataFilter;
import cn.aaron911.admin.common.utils.Constant;
import cn.aaron911.admin.common.utils.Query;
import cn.aaron911.admin.modules.sys.dao.SysRoleDao;
import cn.aaron911.admin.modules.sys.entity.SysDeptEntity;
import cn.aaron911.admin.modules.sys.entity.SysRoleEntity;


/**
 * 角色
 *
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleDao, SysRoleEntity>  {
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysDeptService sysDeptService;

	@DataFilter(subDept = true, user = false)
	public IPage queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");

		IPage<SysRoleEntity> page = this.page(
			new Query<SysRoleEntity>().getPage(params),
			new QueryWrapper<SysRoleEntity>()
				.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
				.apply(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
		);

		for(SysRoleEntity sysRoleEntity : page.getRecords()){
			SysDeptEntity sysDeptEntity = sysDeptService.getById(sysRoleEntity.getDeptId());
			if(sysDeptEntity != null){
				sysRoleEntity.setDeptName(sysDeptEntity.getName());
			}
		}

		return page;
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveRole(SysRoleEntity role) {
		role.setCreateTime(new Date());
		this.save(role);

		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(SysRoleEntity role) {
		this.updateById(role);

		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] roleIds) {
		//删除角色
		this.removeByIds(Arrays.asList(roleIds));

		//删除角色与菜单关联
		sysRoleMenuService.deleteBatch(roleIds);

		//删除角色与部门关联
		sysRoleDeptService.deleteBatch(roleIds);

		//删除角色与用户关联
		sysUserRoleService.deleteBatch(roleIds);
	}


}
