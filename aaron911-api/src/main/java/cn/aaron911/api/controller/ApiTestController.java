
package cn.aaron911.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.aaron911.api.annotation.Login;
import cn.aaron911.api.annotation.LoginUser;
import cn.aaron911.api.entity.UserEntity;
import cn.aaron911.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试接口
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="测试接口")
public class ApiTestController {

    @Login
    @GetMapping("/userInfo")
    @ApiOperation(value="获取用户信息", response=UserEntity.class)
    public Result<UserEntity> userInfo(@ApiIgnore @LoginUser UserEntity user){
        return Result.ok(user);
    }

    @Login
    @GetMapping("/userId")
    @ApiOperation("获取用户ID")
    public Result<Integer> userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId){
        return Result.ok(userId);
    }

    @GetMapping("/notToken")
    @ApiOperation("忽略Token验证测试")
    public Result<String> notToken(){
        return Result.ok("无需token也能访问。。。");
    }

}
