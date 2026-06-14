package com.md.basePlatform.service;

import com.md.basePlatform.common.PageResult;
import com.md.basePlatform.domain.DroneCreateRequest;
import com.md.basePlatform.domain.DroneDTO;
import com.md.basePlatform.domain.DroneQueryRequest;
import com.md.basePlatform.domain.DroneUpdateRequest;

/**
 * 无人机业务服务接口.
 */
public interface DroneService {

    /**
     * 录入无人机.
     *
     * @param request 创建请求
     * @return 无人机 DTO
     */
    DroneDTO create(DroneCreateRequest request);

    /**
     * 按 ID 查询.
     *
     * @param id 主键
     * @return 无人机 DTO
     */
    DroneDTO getById(Long id);

    /**
     * 分页查询.
     *
     * @param query 查询参数
     * @return 分页结果
     */
    PageResult<DroneDTO> list(DroneQueryRequest query);

    /**
     * 查询所有无人机（不分页）.
     *
     * @param keyword 搜索关键字（可选）
     * @return 无人机列表
     */
    java.util.List<DroneDTO> listAll(String keyword);

    /**
     * 更新无人机.
     *
     * @param id      主键
     * @param request 更新请求
     * @return 无人机 DTO
     */
    DroneDTO update(Long id, DroneUpdateRequest request);

    /**
     * 删除无人机.
     *
     * @param id 主键
     */
    void delete(Long id);
}
