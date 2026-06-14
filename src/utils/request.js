import axios from 'axios'

/**
 * 创建 axios 实例，配置基础请求参数
 * @type {import('axios').AxiosInstance}
 */
const request = axios.create({
  // 基础URL，配合Vite代理转发到后端
  baseURL: '/api/v1',
  // 请求超时时间（15秒）
  timeout: 15000,
  // 默认请求头
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * 响应拦截器：统一处理后端返回
 */
request.interceptors.response.use(
  (response) => {
    const body = response.data
    // 如果返回码不是成功状态（200/201），则抛出异常
    if (body && typeof body.code === 'number' && body.code !== 200 && body.code !== 201) {
      return Promise.reject(new Error(body.message || '请求失败'))
    }
    // 直接返回响应体
    return body
  },
  (error) => {
    // 提取错误信息
    const message =
      error.response?.data?.message || error.message || '网络请求失败'
    return Promise.reject(new Error(message))
  },
)

export default request
