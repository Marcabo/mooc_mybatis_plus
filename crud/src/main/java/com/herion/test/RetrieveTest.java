package com.herion.test;

import com.herion.dao.TUserMapper;
import com.herion.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RetrieveTest {

    @Autowired
    private TUserMapper tUserMapper;

    @Test
    public void selectById() {
        TUser tUser = tUserMapper.selectById(1087982257332887553L);
        System.out.println(tUser);
    }

    @Test
    public void selectByMap() {
        // map.put("name","王天风");
        // map.put("age",25);
        Map<String,Object> keyMap = new HashMap<>();
        keyMap.put("name","王天风");
        keyMap.put("age",25);
        List<TUser> tUsers = tUserMapper.selectByMap(keyMap);
        System.out.println(tUsers);
    }



}
