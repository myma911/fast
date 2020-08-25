**项目说明** 
- 文件上传，本地上传，阿里云、七牛云、MinIon


** 使用说明 **

1. 在配置文件中配置上传属性
	具体配置参数详见/aaron911-file/src/main/java/cn/aaron911/file/property/FileProperties.java
	
2. @autowried GlobalFileUploader 或者 BaseFileUploader 进行上传

** 附加说明 **

在文件 aaron911-file/src/main/resources/META-INF/spring.factories 中 声明了配置属性类 cn.aaron911.file.FileAutoConfiguration

在类 cn.aaron911.file.FileAutoConfiguration 中 声明了属性类cn.aaron911.file.property.FileProperties， 注入了对象cn.aaron911.file.BaseFileUploader 和 cn.aaron911.file.GlobalFileUploader
