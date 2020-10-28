package cn.aaron911.admin.modules.sys.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.common.utils.Query;
import cn.aaron911.admin.modules.sys.dao.SysDictDao;
import cn.aaron911.admin.modules.sys.entity.SysDictEntity;


@Service
public class SysDictService extends ServiceImpl<SysDictDao, SysDictEntity> {

    public IPage<SysDictEntity> queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<SysDictEntity> page = this.page(
            new Query<SysDictEntity>().getPage(params),
            new QueryWrapper<SysDictEntity>()
                .like(StringUtils.isNotBlank(name),"name", name)
        );

        return page;
    }

}
