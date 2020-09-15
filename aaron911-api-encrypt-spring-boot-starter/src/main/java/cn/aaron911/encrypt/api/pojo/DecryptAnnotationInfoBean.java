package cn.aaron911.encrypt.api.pojo;


import cn.aaron911.encrypt.api.enums.DecryptBodyMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>解密注解信息</p>
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DecryptAnnotationInfoBean {

    private DecryptBodyMethod decryptBodyMethod;

    private String key;

}
