package it.pkg.code.demo.service.impl;

import it.pkg.code.demo.dao.ExampleDao;
import it.pkg.code.demo.dao.entity.DictEntity;
import it.pkg.code.demo.service.ExampleService;
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
