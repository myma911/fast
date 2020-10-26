package cn.aaron911.file;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({cn.aaron911.file.FileAutoConfiguration.class})
public class EnableAaron911FileUploadConfiguration {

}
