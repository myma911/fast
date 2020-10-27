package cn.aaron911.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.api.annotation.Login;
import cn.aaron911.api.entity.TokenEntity;
import cn.aaron911.api.form.LoginForm;
import cn.aaron911.api.service.TokenService;
import cn.aaron911.api.service.UserService;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录接口
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
public class ApiLoginController {
	
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<TokenEntity> login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        TokenEntity tokenEntity = userService.login(form);

        return Result.ok(tokenEntity);
    }

    @Login
    @PostMapping("/logout")
    @ApiOperation("退出")
    public Result<String> logout(@ApiIgnore @RequestAttribute("userId") long userId){
        tokenService.expireToken(userId);
        return Result.ok();
    }

}
