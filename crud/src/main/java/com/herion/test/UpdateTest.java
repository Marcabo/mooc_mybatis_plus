package com.herion.test;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.herion.dao.TUserMapper;
import com.herion.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTest {

    @Autowired
    private TUserMapper tUserMapper;

    @Test
    public void updateById1() {
        TUser tUser = new TUser();
        tUser.setId(1088248166370832385L);
        tUser.setAge(27);
        tUser.setEmail("wtf2@baomidou.com");
        int rows = tUserMapper.updateById(tUser);
        System.out.println("影响总行数 : " + rows);
    }

    @Test
    public void updateByWrapper() {
        TUser tUser = new TUser();
        tUser.setEmail("lyw2019@baomidou.com");
        tUser.setAge(29);

        UpdateWrapper<TUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", "李艺伟").eq("age", 28);

        int rows = tUserMapper.update(tUser, wrapper);
        System.out.println("影响总行数 : " + rows);
    }

    @Test
    public void updateByWrapper2() {
        UpdateWrapper<TUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("name", "李艺伟").eq("age", 30).set("age", 29);

        int rows = tUserMapper.update(null, wrapper);
        System.out.println("影响总行数 : " + rows);
    }

    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<TUser> lambdaUpdateWrapper = Wrappers.<TUser>lambdaUpdate();
        lambdaUpdateWrapper.eq(TUser::getName,"李艺伟").eq(TUser::getAge,29).set(TUser::getAge,30);

        int rows = tUserMapper.update(null,lambdaUpdateWrapper);
        System.out.println("影响总行数 : " + rows);
    }

    @Test
    public void updateByWrapperLambdaChain() {
        LambdaUpdateChainWrapper<TUser> lambdaUpdateChainWrapper = new LambdaUpdateChainWrapper<>(tUserMapper);
        lambdaUpdateChainWrapper.eq(TUser::getName,"李艺伟").eq(TUser::getAge,29).set(TUser::getAge, 30);
        boolean success = lambdaUpdateChainWrapper.update();
        System.out.println("更新是否成功 : " + success);

    }
}
