package com.herion.test;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.herion.dao.TUserMapper;
import com.herion.pojo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WrapperTest {

    @Autowired
    private TUserMapper tUserMapper;


    /**
     * 1、名字中包含雨并且年龄小于40
     * 	name like '%雨%' and age<40
     */
    @Test
    public void selectListByWrapper() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.like("name", "%雨%").lt("age", 40);
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
     *    name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectListByWrapper2() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.like("name","雨").between("age", 20, 40).isNotNull("email");
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     *    name like '王%' or age>=25 order by age desc,id asc
     */
    @Test
    public void selectListByWrapper3() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name","王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 4、创建日期为2019年2月14日并且直属上级为名字为王姓
     *      date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void selectListByWrapper4() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14").inSql("manager_id", "select id from t_user where name like'王%'");
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 5、名字为王姓并且（年龄小于40或邮箱不为空）
     *     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectListByWrapper5() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name", "王").and(wp -> wp.lt("age", 40).or().isNotNull("email"));
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
     *     name like '王%' or (age<40 and age>20 and email is not null)
     */
    @Test
    public void selectListByWrapper6() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name", "王").or(wp -> wp.lt("age", 40).gt("age", 20).isNotNull("email"));
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 7、（年龄小于40或邮箱不为空）并且名字为王姓
     *     (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void selectListByWrapper7() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
//        wrapper.and(wp -> wp.lt("age", 40).or().isNotNull("email")).likeRight("name", "王");
        wrapper.nested(wp -> wp.lt("age", 40).isNotNull("email")).likeRight("name", "王");
        List<TUser> tUsers = tUserMapper.selectList(wrapper);

        System.out.println(tUsers);
    }

    /**
     * 8、年龄为30、31、34、35
     *     age in (30、31、34、35)
     */
    @Test
    public void selectListByWrapper8() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.in("age", Arrays.asList(new int[]{30, 31, 34, 35}));
        List<TUser> tUsers = tUserMapper.selectList(wrapper);

        System.out.println(tUsers);
    }

    /**
     * 9、只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    public void selectListByWrapper9() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.in("age", Arrays.asList(new int[]{30, 31, 34, 35})).last("limit 1");
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }


    /**
     * 10、名字中包含雨并且年龄小于40(需求1加强版)
     * 第一种情况：select id,name
     * 	           from user
     * 	           where name like '%雨%' and age<40
     * 第二种情况：select id,name,age,email
     * 	           from user
     * 	           where name like '%雨%' and age<40
     */
    @Test
    public void selectListByWrapper10() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        // 第一种情况
        wrapper.select("id", "name").like("name", "雨").lt("age", 40);
        // 第二种情况
        wrapper.select(TUser.class, info -> !info.getColumn().equals("create_time")&& !info.getColumn().equals("manager_id"))
                .like("name", "雨").lt("age", 40);
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    @Test
    public void selectListByWrapperEntity() {
        TUser whereUser = new TUser();
        whereUser.setName("刘红雨");
        whereUser.setAge(32);
        QueryWrapper<TUser> wrapper = new QueryWrapper<>(whereUser);

        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    @Test
    public void selectListByWrapperallEq() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();

        Map<String,Object> maps = new HashMap<>();
        maps.put("name", "刘红雨");
        maps.put("age", 32);
        wrapper.allEq((k,v) -> !k.equals("name"),maps);
        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    @Test
    public void selectMapsByWrapper() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨").lt("age", 40);

        List<Map<String,Object>> tUsers = tUserMapper.selectMaps(wrapper);

        System.out.println(tUsers);
    }

    /**
     * 10、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */
    @Test
    public void selectMapsByWrapper1() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age").groupBy("manager_id").having("sum(age) < {0}",500);

        List<Map<String,Object>> tUsers = tUserMapper.selectMaps(wrapper);

        System.out.println(tUsers);
    }


    @Test
    public void selectObjsByWrapper() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨").lt("age", 40);

        List<Object> tUsers = tUserMapper.selectObjs(wrapper);

        System.out.println(tUsers);
    }

    @Test
    public void selectCountByWrapper() {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.like("name", "雨").lt("age", 40);

        int count = tUserMapper.selectCount(wrapper);

        System.out.println(count);
    }


    @Test
    public void selectLambda() {
        LambdaQueryWrapper<TUser> wrapper = new QueryWrapper<TUser>().lambda();
        LambdaQueryWrapper<TUser> wrapper1 = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<TUser> wrapper2 = Wrappers.<TUser>lambdaQuery();
    }

    @Test
    public void selectLambda1() {
        LambdaQueryWrapper<TUser> wrapper = new QueryWrapper<TUser>().lambda();
        wrapper.like(TUser::getName,"雨").lt(TUser::getAge, 40);

        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     *     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void selectLambda2() {
        LambdaQueryWrapper<TUser> wrapper = new QueryWrapper<TUser>().lambda();
        wrapper.likeRight(TUser::getName, "王").and(wp -> wp.lt(TUser::getAge,40).isNotNull(TUser::getEmail));

        List<TUser> tUsers = tUserMapper.selectList(wrapper);
        System.out.println(tUsers);
    }


    // 在TUserMapper.java的方法名上用注解写sql
    @Test
    public void selectCustomSql() {
        LambdaQueryWrapper<TUser> wrapper = new QueryWrapper<TUser>().lambda();
        wrapper.likeRight(TUser::getName, "王").and(wp -> wp.lt(TUser::getAge,40).or().isNotNull(TUser::getEmail));
        List<TUser> tUsers = tUserMapper.selectAll(wrapper);
        System.out.println(tUsers);
    }

    // 在TUserMapper.xml中用xml写sql
    @Test
    public void selectCustomSql1() {
        LambdaQueryWrapper<TUser> wrapper = new QueryWrapper<TUser>().lambda();
        wrapper.likeRight(TUser::getName, "王").and(wp -> wp.lt(TUser::getAge,40).or().isNotNull(TUser::getEmail));
        List<TUser> tUsers = tUserMapper.selectAll1(wrapper);
        System.out.println(tUsers);
    }

}
