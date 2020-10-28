package cn.aaron911.admin.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.modules.sys.dao.SysDeptDao;
import cn.aaron911.admin.modules.sys.entity.SysDeptEntity;

/**
 * 部门管理
 *
 */
@Service
public class SysDeptService extends ServiceImpl<SysDeptDao, SysDeptEntity> {

	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return null;
	}

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	public List<Long> queryDetpIdList(Long parentId){
		return null;
	}

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	public List<Long> getSubDeptIdList(Long deptId){
		return null;
	}

}
