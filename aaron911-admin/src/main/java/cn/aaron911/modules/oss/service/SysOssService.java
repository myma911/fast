package cn.aaron911.modules.oss.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import cn.aaron911.common.utils.PageUtils;
import cn.aaron911.modules.oss.entity.SysOssEntity;

/**
 * 文件上传
 *
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
