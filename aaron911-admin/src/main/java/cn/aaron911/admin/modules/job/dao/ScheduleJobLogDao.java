package cn.aaron911.admin.modules.job.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.admin.modules.job.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 *
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
	
}
