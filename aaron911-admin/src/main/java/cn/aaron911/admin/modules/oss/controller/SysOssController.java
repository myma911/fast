package cn.aaron911.admin.modules.oss.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.gson.Gson;

import cn.aaron911.admin.common.utils.ConfigConstant;
import cn.aaron911.admin.common.utils.Constant;
import cn.aaron911.admin.common.validator.group.AliyunGroup;
import cn.aaron911.admin.common.validator.group.QcloudGroup;
import cn.aaron911.admin.common.validator.group.QiniuGroup;
import cn.aaron911.admin.modules.oss.cloud.CloudStorageConfig;
import cn.aaron911.admin.modules.oss.cloud.OSSFactory;
import cn.aaron911.admin.modules.oss.entity.SysOssEntity;
import cn.aaron911.admin.modules.oss.service.SysOssService;
import cn.aaron911.admin.modules.sys.service.SysConfigService;
import cn.aaron911.common.exception.FailedException;
import cn.aaron911.common.result.Result;
import cn.aaron911.common.validator.ValidatorUtils;

/**
 * 文件上传
 *
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController {
	
	@Autowired
	private SysOssService sysOssService;
	
    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
	public Result list(@RequestParam Map<String, Object> params){
		IPage page = sysOssService.queryPage(params);

		return Result.ok(page);
	}


    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public Result config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return Result.ok(config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public Result saveConfig(@RequestBody CloudStorageConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}

        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return Result.ok();
	}
	

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:oss:all")
	public Result upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new FailedException("上传文件不能为空");
		}

		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);

		return Result.ok(url);
	}


	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public Result delete(@RequestBody Long[] ids){
		sysOssService.removeByIds(Arrays.asList(ids));

		return Result.ok();
	}

}
