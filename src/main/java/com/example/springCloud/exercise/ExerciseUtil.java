package com.example.springCloud.exercise;

import com.example.springCloud.po.UserPo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExerciseUtil {
    @Test
    public void test_list_to_map() {
        List<UserPo> userPoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserPo userPo = new UserPo();
            userPo.setAge(i);
            userPo.setName("测试" + i);
            userPoList.add(userPo);
        }

        Map<String, UserPo> userPoMap = userPoList.stream().collect(Collectors.toMap(UserPo::getName, v -> v, (k1, k2) -> k1));
        Map<String, List<UserPo>> userPoMap_list = userPoList.stream().collect(Collectors.groupingBy(UserPo::getName));
        System.out.println(userPoMap);

    }
}
