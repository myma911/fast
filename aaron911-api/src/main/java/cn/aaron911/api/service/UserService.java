package cn.aaron911.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.aaron911.api.entity.TokenEntity;
import cn.aaron911.api.entity.UserEntity;
import cn.aaron911.api.form.LoginForm;

/**
 * 用户
 *
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	TokenEntity login(LoginForm form);
}
