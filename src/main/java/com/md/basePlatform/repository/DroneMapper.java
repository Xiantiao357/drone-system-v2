package com.md.basePlatform.repository;

import com.md.basePlatform.domain.Drone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 无人机 MyBatis Mapper.
 */
@Mapper
public interface DroneMapper {

    /**
     * 新增无人机.
     *
     * @param drone 实体
     * @return 影响行数
     */
    int insert(Drone drone);

    /**
     * 按 ID 查询.
     *
     * @param id 主键
     * @return 实体
     */
    Drone selectById(@Param("id") Long id);

    /**
     * 分页查询.
     *
     * @param offset  偏移量
     * @param size    条数
     * @param keyword 关键字
     * @return 列表
     */
    List<Drone> selectPage(@Param("offset") int offset,
                           @Param("size") int size,
                           @Param("keyword") String keyword);

    /**
     * 查询所有（不分页）.
     *
     * @param keyword 关键字
     * @return 列表
     */
    List<Drone> selectAll(@Param("keyword") String keyword);

    /**
     * 统计总数.
     *
     * @param keyword 关键字
     * @return 总数
     */
    long countByKeyword(@Param("keyword") String keyword);

    /**
     * 更新无人机.
     *
     * @param drone 实体
     * @return 影响行数
     */
    int updateById(Drone drone);

    /**
     * 删除无人机.
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
}
