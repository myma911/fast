package cn.aaron911.admin.modules.sys.controller;

import java.util.Map;

import cn.aaron911.admin.modules.sys.entity.SysLogEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.aaron911.admin.modules.sys.service.SysLogService;
import cn.aaron911.common.result.Result;


/**
 * 系统日志
 *
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public Result<IPage<SysLogEntity>> list(@RequestParam Map<String, Object> params){
		IPage<SysLogEntity> page = sysLogService.queryPage(params);
		return Result.ok(page);
	}
	
}
