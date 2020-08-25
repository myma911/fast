package cn.aaron911.file.alioss.entity;

import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 */
@Data
public class RefererEntity extends AbstractEntity {

    List<String> refererList;

    public RefererEntity(String bucketName) {
        super(bucketName);
    }

    public void setRefererList(List<String> refererList) {
        this.refererList = refererList;
    }
}
