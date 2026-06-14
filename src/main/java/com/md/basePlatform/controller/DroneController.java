package com.md.basePlatform.controller;

import com.md.basePlatform.common.ApiResponse;
import com.md.basePlatform.common.PageResult;
import com.md.basePlatform.domain.DroneCreateRequest;
import com.md.basePlatform.domain.DroneDTO;
import com.md.basePlatform.domain.DroneQueryRequest;
import com.md.basePlatform.domain.DroneUpdateRequest;
import com.md.basePlatform.service.DroneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 无人机 REST 控制器.
 */
@Api(tags = "无人机管理")
@RestController
@RequestMapping("/api/v1/drones")
@Validated
public class DroneController {

    private final DroneService droneService;

    /**
     * 构造控制器.
     *
     * @param droneService 无人机服务
     */
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * 查询无人机列表.
     * 当 page 参数为空时返回所有数据，否则按分页查询
     *
     * @param page    页码（不传则返回所有）
     * @param size    每页条数
     * @param keyword 关键字
     * @return 分页结果或全部结果
     */
    @ApiOperation("查询无人机列表")
    @GetMapping
    public ApiResponse<?> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        if (page == null) {
            // 不分页，返回所有数据
            return ApiResponse.success(droneService.listAll(keyword));
        }
        // 分页查询
        DroneQueryRequest query = new DroneQueryRequest();
        query.setPage(page);
        query.setSize(size);
        query.setKeyword(keyword);
        return ApiResponse.success(droneService.list(query));
    }

    /**
     * 查询无人机详情.
     *
     * @param id 主键
     * @return 无人机详情
     */
    @ApiOperation("查询无人机详情")
    @GetMapping("/{id}")
    public ApiResponse<DroneDTO> getById(@PathVariable Long id) {
        return ApiResponse.success(droneService.getById(id));
    }

    /**
     * 录入无人机.
     *
     * @param request 创建请求
     * @return 创建的无人机
     */
    @ApiOperation("录入无人机")
    @PostMapping
    public ResponseEntity<ApiResponse<DroneDTO>> create(@Valid @RequestBody DroneCreateRequest request) {
        DroneDTO created = droneService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "创建成功", created));
    }

    /**
     * 更新无人机.
     *
     * @param id      主键
     * @param request 更新请求
     * @return 更新后的无人机
     */
    @ApiOperation("更新无人机")
    @PutMapping("/{id}")
    public ApiResponse<DroneDTO> update(@PathVariable Long id, @Valid @RequestBody DroneUpdateRequest request) {
        return ApiResponse.success(droneService.update(id, request));
    }

    /**
     * 删除无人机.
     *
     * @param id 主键
     * @return 删除结果
     */
    @ApiOperation("删除无人机")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        droneService.delete(id);
        return ApiResponse.success(200, "删除成功", null);
    }
}
