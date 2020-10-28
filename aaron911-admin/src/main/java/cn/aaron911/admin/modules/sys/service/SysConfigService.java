package cn.aaron911.admin.modules.sys.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.modules.sys.dao.SysConfigDao;
import cn.aaron911.admin.modules.sys.entity.SysConfigEntity;

/**
 * 系统配置信息
 *
 */
@Service
public class SysConfigService extends ServiceImpl<SysConfigDao ,SysConfigEntity> {

	public IPage queryPage(Map<String, Object> params) {
		return null;
	}
	
	/**
	 * 保存配置信息
	 */
	public void saveConfig(SysConfigEntity config) {
	}
	
	
	/**
	 * 更新配置信息
	 */
	public void update(SysConfigEntity config) {
	}
	
	
	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value) {
	}
	
	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids) {
	}
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 */
	public String getValue(String key) {
		return null;
	}
	
	
	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz) {
		return null;
	}
	
}
