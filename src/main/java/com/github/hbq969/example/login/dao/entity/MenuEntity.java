package com.github.hbq969.example.login.dao.entity;

import com.github.hbq969.code.common.spring.context.SpringContext;
import com.github.hbq969.code.common.utils.FormatTime;
import com.github.hbq969.code.dict.service.api.DictAware;
import com.github.hbq969.code.dict.service.api.DictModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : hbq969@gmail.com
 * @description : 菜单信息
 * @createTime : 2025/1/2 11:15
 */
@Data
public class MenuEntity implements DictModel, DictAware {
    private Long id;
    private String name;
    private String url;
    private Long parentId = 0L;
    private Integer menuLevel = 1;
    private Integer orderIndex = 0;
    private String iconName = "LogIcon";
    private Long createdAt;
    private String fmtCreatedAt;
    private Long updatedAt;
    private String fmtUpdatedAt;
    private List<MenuEntity> menus;

    @Override
    public void convertDict(SpringContext context) {
        DictAware.super.convertDict(context);
        if (createdAt != null) {
            this.fmtCreatedAt = FormatTime.YYYYMMDDHHMISS.withSecs(createdAt.longValue());
        }
        if (updatedAt != null) {
            this.fmtUpdatedAt = FormatTime.YYYYMMDDHHMISS.withSecs(updatedAt.longValue());
        }
    }

    public void initial() {
        this.createdAt = FormatTime.nowSecs();
    }

    public void update() {
        this.updatedAt = FormatTime.nowSecs();
    }

    public void addMenuEntity(MenuEntity menuEntity) {
        if (menus == null) {
            menus = new ArrayList<>();
        }
        menus.add(menuEntity);
    }
}
