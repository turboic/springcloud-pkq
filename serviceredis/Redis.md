使用场景：涵盖缓存，分布式锁，秒杀，签到


Redis数据库持久化的需求
RDB（Redis DataBase）和AOF（Append Only File）

RDB快照方式备份Redis数据，恢复时间相对较于AOF优势，但可能存在数据丢失的情况。
AOF文件体积大，恢复数据时间长。

AOF文件修复
1.备份被写坏的AOF文件
2.运行redis-check-aof –fix进行修复
3.用diff -u来看下两个文件的差异，确认问题点
4.重启redis，加载修复后的AOF文件