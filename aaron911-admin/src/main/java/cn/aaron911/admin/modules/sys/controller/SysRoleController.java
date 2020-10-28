package cn.aaron911.admin.modules.sys.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.aaron911.admin.common.annotation.SysLog;
import cn.aaron911.admin.modules.sys.entity.SysRoleEntity;
import cn.aaron911.admin.modules.sys.service.SysRoleDeptService;
import cn.aaron911.admin.modules.sys.service.SysRoleMenuService;
import cn.aaron911.admin.modules.sys.service.SysRoleService;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;

/**
 * 角色管理
 *
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public Result list(@RequestParam Map<String, Object> params){
		IPage page = sysRoleService.queryPage(params);

		return Result.ok(page);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public Result select(){
		List<SysRoleEntity> list = sysRoleService.list();
		
		return Result.ok(list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public Result info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);
		
		return Result.ok(role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.saveRole(role);
		
		return Result.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return Result.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return Result.ok();
	}
}
