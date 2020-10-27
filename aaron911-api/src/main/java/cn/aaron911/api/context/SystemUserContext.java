package cn.aaron911.api.context;

import com.alibaba.ttl.TransmittableThreadLocal;

import cn.aaron911.api.entity.UserEntity;


public class SystemUserContext {

    private static TransmittableThreadLocal<UserEntity> userThreadLocal = new TransmittableThreadLocal<>();


    /**
     * 获取线程user
     * @return
     */
    public static UserEntity getUserEntity(){
        return userThreadLocal.get();
    }

    /**
     * 设置线程user
     * @param user
     */
    public static void setUserEntity(UserEntity user){
        if (null == user) {
            throw new IllegalArgumentException();
        }
        userThreadLocal.set(user);
    }


    /**
     * 清除
     */
    public static void remove(){
    	userThreadLocal.remove();
    }
}
