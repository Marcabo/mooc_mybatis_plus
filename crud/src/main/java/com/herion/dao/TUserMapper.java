package com.herion.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.herion.pojo.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TUserMapper extends BaseMapper<TUser> {

    /**
     * Mooc_4_1
     */
    @Select("select * from t_user ${ew.customSqlSegment}")
    public List<TUser> selectAll(@Param(Constants.WRAPPER)Wrapper<TUser> wrapper);

    public List<TUser> selectAll1(@Param(Constants.WRAPPER)Wrapper<TUser> wrapper);

    /**
     * 多表联查分页方法实例
     * @param page 必须,分页方法的第一个参数必须是page
     * @param wrapper 查询条件
     * @return
     */
    public IPage<TUser> selectUserPage(Page<TUser> page,@Param(Constants.WRAPPER)Wrapper<TUser> wrapper);
}
