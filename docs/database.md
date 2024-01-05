`t_user`（用户表）：

| 字段名称   | 字段类型  | 主键/外键 | 是否为空 | 字段含义     | 备注     |
| ---------- | --------- | --------- | -------- | ------------ | -------- |
| id         | bigint    | 主键      |          | 用户id       |          |
| name       |           |           |          | 用户真实姓名 | 默认空串 |
| address    |           |           |          |              |          |
| mobile     |           |           |          |              |          |
|            |           |           |          |              |          |
| state      | tinyint   |           |          |              |          |
| created_at | timestamp |           |          |              |          |
| updated_at | timestamp |           |          |              |          |
| version    | bigint    |           |          |              |          |

