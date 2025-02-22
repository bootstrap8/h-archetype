package com.github.hbq969.example.service;

import com.github.hbq969.example.dao.entity.DictEntity;
import java.util.List;

/**
 * @author : hbq969@gmail.com
 * @description : 示例业务接口
 * @createTime : 2023/8/11 09:44
 */
public interface ExampleService {

  List<DictEntity> queryList(String key);

}
