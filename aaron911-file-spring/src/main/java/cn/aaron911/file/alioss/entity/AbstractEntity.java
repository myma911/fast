package cn.aaron911.file.alioss.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractEntity {
    private String bucketName;

    public AbstractEntity() {
    }

    public AbstractEntity(String bucketName) {
        this.bucketName = bucketName;
    }
}
