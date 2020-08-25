package cn.aaron911.sso.server.service;

import cn.aaron911.sso.core.entity.ReturnT;
import cn.aaron911.sso.server.core.model.UserInfo;

public interface UserService {

    public ReturnT<UserInfo> findUser(String username, String password);

}
