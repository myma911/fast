package cn.aaron911.admin.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.aaron911.admin.modules.sys.entity.SysDictEntity;
import cn.aaron911.admin.modules.sys.service.SysDictService;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;

/**
 * 数据字典
 *
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
	
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public Result list(@RequestParam Map<String, Object> params){
        IPage page = sysDictService.queryPage(params);
        return Result.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public Result info(@PathVariable("id") Long id){
        SysDictEntity dict = sysDictService.getById(id);
        return Result.ok(dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public Result save(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.save(dict);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public Result update(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public Result delete(@RequestBody Long[] ids){
        sysDictService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }

}
