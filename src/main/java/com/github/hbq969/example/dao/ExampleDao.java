package com.github.hbq969.example.dao;

import com.github.hbq969.code.common.datasource.DS;
import com.github.hbq969.code.common.datasource.context.DefaultPolicy;
import com.github.hbq969.example.dao.entity.DictEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : hbq969@gmail.com
 * @description : 示例数据访问层接口
 * @createTime : 2023/8/11 09:05
 */
@Mapper
@DS(DefaultPolicy.class)
public interface ExampleDao {

  List<DictEntity> queryList();
}
