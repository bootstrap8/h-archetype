package com.github.hbq969.example.service.impl;

import com.github.hbq969.example.dao.ExampleDao;
import com.github.hbq969.example.dao.entity.DictEntity;
import com.github.hbq969.example.service.ExampleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : hbq969@gmail.com
 * @description : 示例业务实现类
 * @createTime : 2023/8/11 09:45
 */
@Service
public class ExampleServiceImpl implements ExampleService {

  @Autowired
  private ExampleDao dao;

  @Override
  public List<DictEntity> queryList(String key) {
    return dao.queryList();
  }
}
