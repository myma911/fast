package cn.aaron911.modules.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.aaron911.modules.sys.entity.SysUserEntity;
import cn.aaron911.modules.sys.shiro.ShiroUtils;

/**
 * Controller公共组件
 *
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDeptId();
	}
}
