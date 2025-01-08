insert into h_roles(id,name,description,created_at) values(1,'ADMIN','超级管理员',1735800456);
insert into h_roles(id,name,description,created_at) values(2,'MANAGE','维护管理员',1735800456);
insert into h_roles(id,name,description,created_at) values(3,'USER','普通用户',1735800456);
insert into h_menus(id,name,url,parent_id,order_index,menu_level,icon_name,created_at) values(1,'系统管理','/system',0,0,1,'SettingsIcon',1735800456);
insert into h_menus(id,name,url,parent_id,order_index,menu_level,icon_name,created_at) values(2,'角色管理','/system/Role',1,0,2,'RoleIcon',1735800456);
insert into h_menus(id,name,url,parent_id,order_index,menu_level,icon_name,created_at) values(3,'用户管理','/system/User',1,1,2,'UserIcon',1735800456);
insert into h_menus(id,name,url,parent_id,order_index,menu_level,icon_name,created_at) values(4,'菜单管理','/system/Menu',1,2,2,'MenuIcon',1735800456);
insert into h_users(id,username,password,role_id,created_at) values(1,'admin','$14Gr2er',1,1735800456);
insert into h_users(id,username,password,role_id,created_at) values(2,'manage','123456',2,1735800456);
insert into h_users(id,username,password,role_id,created_at) values(3,'user','123456',3,1735800456);
insert into h_role_menus(role_id,menu_id) values(2,1);
insert into h_role_menus(role_id,menu_id) values(2,2);
insert into h_role_menus(role_id,menu_id) values(2,3);
insert into h_role_menus(role_id,menu_id) values(2,4);

insert into h_dict_base(dict_name,dict_desc,dict_source,key_column,val_column,app) values('menu,icon','菜单图标',1,'key','value','common');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','DashboardIcon','控制面板');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','FolderIcon','文件夹');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','LogIcon','文件');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','MenuIcon','菜单');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','PermissionIcon','权限');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','RoleIcon','角色');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','SettingsIcon','设置');
insert into h_dict_pairs(dict_name,pair_key,pair_value) values('menu,icon','UserIcon','用户');