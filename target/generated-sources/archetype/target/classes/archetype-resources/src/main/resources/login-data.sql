insert into h_zkc_roles(id,name,description,created_at) values(1,'ADMIN','超级管理员',1735800456);
insert into h_zkc_roles(id,name,description,created_at) values(2,'MANAGE','维护管理员',1735800456);
insert into h_zkc_roles(id,name,description,created_at) values(3,'USER','普通用户',1735800456);
insert into h_zkc_menus(id,name,url,parent_id,order_index,created_at) values(1,'系统管理菜单','system',-1,8,1735800456);
insert into h_zkc_users(username,password,role_id,created_at) values('admin','$14Gr2er',1,1735800456);
insert into h_zkc_users(username,password,role_id,created_at) values('manage','123456',2,1735800456);
insert into h_zkc_users(username,password,role_id,created_at) values('user','123456',3,1735800456);