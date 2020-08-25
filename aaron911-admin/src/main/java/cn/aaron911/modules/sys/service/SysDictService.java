package cn.aaron911.modules.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.aaron911.common.utils.PageUtils;
import cn.aaron911.modules.sys.entity.SysDictEntity;

/**
 * 数据字典
 *
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

