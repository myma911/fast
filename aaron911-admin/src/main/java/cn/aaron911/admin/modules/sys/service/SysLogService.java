package cn.aaron911.modules.sys.service;


import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.aaron911.admin.common.utils.PageUtils;
import cn.aaron911.modules.sys.entity.SysLogEntity;


/**
 * 系统日志
 *
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
