package cn.aaron911.file.alioss.entity;


import java.util.List;

public class RefererEntity extends AbstractEntity {

    List<String> refererList;

    public RefererEntity(String bucketName) {
        super(bucketName);
    }

    public void setRefererList(List<String> refererList) {
        this.refererList = refererList;
    }

	public List<String> getRefererList() {
		return refererList;
	}
    
}
