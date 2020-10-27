package cn.aaron911.api.service.impl;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.aaron911.api.dao.UserDao;
import cn.aaron911.api.entity.TokenEntity;
import cn.aaron911.api.entity.UserEntity;
import cn.aaron911.api.form.LoginForm;
import cn.aaron911.api.service.TokenService;
import cn.aaron911.api.service.UserService;
import cn.aaron911.common.exception.LoginErrorException;
import cn.hutool.crypto.digest.MD5;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	
	@Autowired
	private TokenService tokenService;

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
	}

	@Override
	public TokenEntity login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		cn.hutool.core.lang.Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(MD5.create().digestHex(form.getPassword()))){
			throw new LoginErrorException();
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());
		return tokenEntity;
	}

}
