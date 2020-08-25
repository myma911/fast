package cn.aaron911.modules.oss.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.common.utils.PageUtils;
import cn.aaron911.common.utils.Query;
import cn.aaron911.modules.oss.dao.SysOssDao;
import cn.aaron911.modules.oss.entity.SysOssEntity;
import cn.aaron911.modules.oss.service.SysOssService;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SysOssEntity> page = this.page(
			new Query<SysOssEntity>().getPage(params)
		);

		return new PageUtils(page);
	}
	
}
