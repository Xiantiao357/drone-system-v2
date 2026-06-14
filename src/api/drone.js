import request from '@/utils/request'

/**
 * 获取无人机列表（分页）
 * @param {Object} params - 查询参数
 * @param {number} params.page - 页码
 * @param {number} params.size - 每页条数
 * @param {string} params.keyword - 搜索关键字
 * @returns {Promise} 分页结果
 */
export function fetchDroneList(params) {
  return request.get('/drones', { params })
}

/**
 * 根据ID获取无人机详情
 * @param {number} id - 无人机ID
 * @returns {Promise} 无人机详情
 */
export function fetchDroneById(id) {
  return request.get(`/drones/${id}`)
}

/**
 * 创建新无人机
 * @param {Object} data - 创建数据
 * @param {string} data.model - 型号
 * @param {string} data.manufacturer - 厂商
 * @returns {Promise} 创建的无人机
 */
export function createDrone(data) {
  return request.post('/drones', data)
}

/**
 * 更新无人机信息
 * @param {number} id - 无人机ID
 * @param {Object} data - 更新数据
 * @returns {Promise} 更新后的无人机
 */
export function updateDrone(id, data) {
  return request.put(`/drones/${id}`, data)
}

/**
 * 删除无人机
 * @param {number} id - 无人机ID
 * @returns {Promise} 删除结果
 */
export function deleteDrone(id) {
  return request.delete(`/drones/${id}`)
}
