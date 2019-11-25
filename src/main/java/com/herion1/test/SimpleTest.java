package com.herion1.test;

import com.herion1.mapper.TUserMapper;
import com.herion1.model.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

    @Autowired
    private TUserMapper tUserMapper;

    @Test
    public void Test() {
        TUser tUser = tUserMapper.selectByPrimaryKey(1087982257332887553L);

        System.out.println("SimpleTest" + tUser.getCreateTime() + "----------------------");

    }
}
