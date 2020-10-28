package cn.aaron911.admin.modules.oss.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.common.utils.Query;
import cn.aaron911.admin.modules.oss.dao.SysOssDao;
import cn.aaron911.admin.modules.oss.entity.SysOssEntity;


@Service("sysOssService")
public class SysOssService extends ServiceImpl<SysOssDao, SysOssEntity> {

	public IPage queryPage(Map<String, Object> params) {
		IPage<SysOssEntity> page = this.page(
			new Query<SysOssEntity>().getPage(params)
		);

		return page;
	}
	
}
