package cn.aaron911.admin.modules.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.aaron911.admin.common.annotation.SysLog;
import cn.aaron911.admin.modules.sys.entity.SysConfigEntity;
import cn.aaron911.admin.modules.sys.service.SysConfigService;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;

/**
 * 系统配置信息
 *
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {
	
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public Result list(@RequestParam Map<String, Object> params){
		IPage page = sysConfigService.queryPage(params);
		return Result.ok(page);
	}
	
	
	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	@ResponseBody
	public Result info(@PathVariable("id") Long id){
		SysConfigEntity config = sysConfigService.getById(id);
		
		return Result.ok(config);
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public Result save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.saveConfig(config);
		
		return Result.ok();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public Result update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return Result.ok();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public Result delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return Result.ok();
	}

}
