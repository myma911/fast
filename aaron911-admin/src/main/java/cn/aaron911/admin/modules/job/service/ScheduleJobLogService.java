package cn.aaron911.admin.modules.job.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.common.utils.Query;
import cn.aaron911.admin.modules.job.dao.ScheduleJobLogDao;
import cn.aaron911.admin.modules.job.entity.ScheduleJobLogEntity;

@Service
public class ScheduleJobLogService extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity>  {

	public IPage<ScheduleJobLogEntity> queryPage(Map<String, Object> params) {
		String jobId = (String)params.get("jobId");

		IPage<ScheduleJobLogEntity> page = this.page(
			new Query<ScheduleJobLogEntity>().getPage(params),
			new QueryWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
		);

		return page;
	}

}
