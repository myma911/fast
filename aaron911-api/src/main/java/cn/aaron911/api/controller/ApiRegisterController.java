package cn.aaron911.api.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.api.entity.UserEntity;
import cn.aaron911.api.form.RegisterForm;
import cn.aaron911.api.service.UserService;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;
import cn.hutool.crypto.digest.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 注册接口
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result<String> register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setUsername(form.getMobile());
        user.setPassword(MD5.create().digestHex(form.getPassword()));
        user.setCreateTime(new Date());
        userService.save(user);
        return Result.ok();
    }
}
