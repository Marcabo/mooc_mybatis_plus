package com.herion.test;

import com.herion.dao.TUserMapper;
import com.herion.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteTest {

    @Autowired
    private TUserMapper tUserMapper;

    @Test
    public void deleteById() {
        TUser tUser = new TUser();
        tUser.setName("刘明强");
        tUser.setAge(24);
        tUser.setManagerId(1087982257332887553L);
        tUser.setCreateTime(LocalDateTime.now());
        int rows = tUserMapper.insert(tUser);
        System.out.println("影响记录行数 : " + rows);
    }
}
