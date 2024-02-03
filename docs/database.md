`t_user`（用户表）：

| 字段名称   | 字段类型     | 主键/索引 | 允许NULL/默认值 | 备注                        |
| ---------- | ------------ | --------- | --------------- | --------------------------- |
| id         | bigint       | 主键      | NOT NULL        | 雪花算法ID                  |
| real_name  | varchar(8)   |           | 空串            | 真实姓名                    |
| address    | varchar(128) |           | 空串            | 住址，加密存储              |
| mobile     | varchar(24)  |           | 空串            | 手机号码/座机号码，加密存储 |
| state      | tinyint      |           | 0               | 状态                        |
| version    | int          |           | 0               |                             |
| created_at | timestamp    |           | 当前时间戳      |                             |
| updated_at | timestamp    |           | 当前时间戳      |                             |

`t_cylinder`（钢瓶表）：

| 字段名称   | 字段类型    | 主键/索引 | 允许NULL/默认值 | 备注       |
| ---------- | ----------- | --------- | --------------- | ---------- |
| id         | bigint      | 主键      | NOT NULL        | 雪花算法ID |
| serial_no  | varchar(32) | 唯一索引  | 空串            | 钢印号     |
| barcode    | varchar(32) | 唯一索引  | 空串            | 气瓶条码   |
| state      | tinyint     |           | 0               | 状态       |
| version    | int         |           | 0               |            |
| created_at | timestamp   |           | 当前时间戳      |            |
| updated_at | timestamp   |           | 当前时间戳      |            |

`t_delivery`（配送表）：

| 字段名称    | 字段类型  | 主键/索引 | 允许NULL/默认值 | 备注                       |
| ----------- | --------- | --------- | --------------- | -------------------------- |
| id          | bigint    | 主键      | NOT NULL        | 雪花算法ID                 |
| user_id     | bigint    |           | NOT NULL        | 用户ID，关联`t_user`表     |
| cylinder_id | bigint    |           | NOT NULL        | 钢瓶ID，关联`t_cylinder`表 |
| state       | tinyint   |           | 0               | 状态                       |
| version     | int       |           | 0               |                            |
| created_at  | timestamp |           | 当前时间戳      |                            |
| updated_at  | timestamp |           | 当前时间戳      |                            |
