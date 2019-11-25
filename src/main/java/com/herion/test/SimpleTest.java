package com.herion.test;

import com.herion.dao.TUserMapper;
import com.herion.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

    @Autowired
    private TUserMapper tUserMapper;

    @Test
    public void Test() {
        List<TUser> tUser = tUserMapper.selectList(null);
        System.out.println(tUser);
    }
}
