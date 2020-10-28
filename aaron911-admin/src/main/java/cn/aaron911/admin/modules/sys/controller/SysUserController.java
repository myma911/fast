package cn.aaron911.admin.modules.sys.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.aaron911.admin.common.annotation.SysLog;
import cn.aaron911.admin.modules.sys.entity.SysUserEntity;
import cn.aaron911.admin.modules.sys.service.SysUserRoleService;
import cn.aaron911.admin.modules.sys.service.SysUserService;
import cn.aaron911.admin.modules.sys.shiro.ShiroUtils;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;
import cn.aaron911.common.validator.group.AddGroup;
import cn.aaron911.common.validator.group.UpdateGroup;
import cn.hutool.core.lang.Assert;

/**
 * 系统用户
 *
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public Result list(@RequestParam Map<String, Object> params){
		IPage page = sysUserService.queryPage(params);

		return Result.ok(page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public Result info(){
		return Result.ok(getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public Result password(String password, String newPassword){
		Assert.notBlank(newPassword, "新密码不为能空");
		//原密码
		password = ShiroUtils.sha256(password, getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
				
		//更新密码
		boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(!flag){
			return Result.failed("原密码不正确");
		}
		
		return Result.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public Result info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return Result.ok(user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public Result save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		
		sysUserService.saveUser(user);
		
		return Result.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public Result update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		sysUserService.update(user);
		
		return Result.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public Result delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return Result.failed("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return Result.failed("当前用户不能删除");
		}

		sysUserService.removeByIds(Arrays.asList(userIds));
		
		return Result.ok();
	}
}
