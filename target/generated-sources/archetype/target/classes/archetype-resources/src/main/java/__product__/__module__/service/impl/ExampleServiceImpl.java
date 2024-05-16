#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.${product}.${module}.service.impl;

import ${package}.${product}.${module}.dao.ExampleDao;
import ${package}.${product}.${module}.dao.entity.DictEntity;
import ${package}.${product}.${module}.service.ExampleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : ${author}
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
