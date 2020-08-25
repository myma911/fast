package cn.aaron911.modules.job.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.aaron911.common.utils.PageUtils;
import cn.aaron911.modules.job.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 *
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
