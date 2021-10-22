## 模块六：数据库性能调优

### 33 | MySQL调优之SQL语句：如何写出高性能SQL语句？

慢SQL的诱因：

1. 无索引、索引失效、回表查询导致慢查询
2. 锁等待，行锁上升为表锁（对一张表使用大量行锁、更新操作时条件索引失效）
3. 不适当的操作：SELECT *，大表分页查询(LIMIT M,N)



优化分析：

1. **通过 EXPLAIN 分析 SQL 执行计划**，重要关注点是可能使用的索引，真正使用的索引，索引执行的方式（结果值从好到差依次是：system > const > eq_ref > ref > range > index > ALL）。这个主要分析使用索引的情况，分析索引是否失效、是否缺索引、是否需要创建联合索引。联合索引主要尽量最左匹配原则、精准查询尽量放在左侧。
2. **通过 Show Profile 分析 SQL 执行性能**，获取到 Query_ID 之后，我们再通过 Show Profile for Query ID 语句，就能够查看到对应 Query_ID 的 SQL 语句在执行过程中线程的每个状态所消耗的时间了



优化策略：

1. 优化分页查询

   方式一：前端返回上一页的最后ID，通过条件查询避免过多的索引遍历

   方式二：子查询出最后的id，然后通过条件查询过滤，此方案不需要前端配合

   ```
   
   select * from `demo`.`order` where id> (select id from `demo`.`order` order by order_no limit 10000, 1)  limit 20;

2. 优化SELECT COUNT(*)

   COUNT(*) 和 COUNT(1) 这两种方式了，其实两者没有明显的区别，在拥有主键的情况下，它们都是利用主键列实现了行数的统计。

   方式一：使用近似值，使用EXPLAIN

   方式二：增加汇总统计表

3. 优化SELECT *