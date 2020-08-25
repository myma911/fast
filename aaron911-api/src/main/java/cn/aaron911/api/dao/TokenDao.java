
package cn.aaron911.api.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.aaron911.api.entity.TokenEntity;

/**
 * 用户Token
 *
 */
@Mapper
public interface TokenDao extends BaseMapper<TokenEntity> {
	
}
