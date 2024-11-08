package cn.aaron911.admin.modules.sys.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.common.utils.Query;
import cn.aaron911.admin.modules.sys.dao.SysLogDao;
import cn.aaron911.admin.modules.sys.entity.SysLogEntity;


@Service("sysLogService")
public class SysLogService extends ServiceImpl<SysLogDao, SysLogEntity>  {

    public IPage<SysLogEntity> queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return page;
    }
}
