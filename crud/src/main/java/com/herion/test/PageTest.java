package com.herion.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.herion.dao.TUserMapper;
import com.herion.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PageTest {

    @Autowired
    private TUserMapper tUserMapper;

    @Test
    public void PageTest1() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        // 当前页1,每页数目3
        Page<TUser> page = new Page<>(1,3);
        IPage<TUser> iPage = tUserMapper.selectPage(page, wrapper);
        System.out.println("总记录数: " + iPage.getTotal());
        System.out.println("总页数: " + iPage.getSize() + " =others= " + iPage.getPages());
        List<TUser> tUsers = iPage.getRecords();
        System.out.println(tUsers);
    }

    /**
     * 自定义分页查询
     */
    @Test
    public void PageTest2() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        // 当前页1,每页数目3
        Page<TUser> page = new Page<>(1,3);
        IPage<TUser> iPage = tUserMapper.selectUserPage(page, wrapper);
        System.out.println("总记录数: " + iPage.getTotal());
        System.out.println("总页数: " + iPage.getSize() + " =others= " + iPage.getPages());
        List<TUser> tUsers = iPage.getRecords();
        System.out.println(tUsers);
    }
}
