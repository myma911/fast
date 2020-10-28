package cn.aaron911.admin.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.admin.common.utils.Constant;
import cn.aaron911.admin.modules.sys.dao.SysMenuDao;
import cn.aaron911.admin.modules.sys.entity.SysMenuEntity;
import cn.aaron911.admin.modules.sys.entity.SysRoleMenuEntity;


@Service("sysMenuService")
public class SysMenuService extends ServiceImpl<SysMenuDao, SysMenuEntity>  {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	public List<SysMenuEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	public void delete(Long menuId){
		//删除菜单
		this.removeById(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("menu_id", menuId));
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		
		for(SysMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
