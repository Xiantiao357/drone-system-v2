/**
 * Toast 消息提示工具
 * 
 * 提供统一的消息提示功能，支持四种类型：success、error、warning、info
 * 
 * 使用方式：
 * import { toast } from '@/utils/toast'
 * 
 * toast.success('操作成功')
 * toast.error('操作失败')
 * toast.warning('警告信息')
 * toast.info('提示信息')
 */

// Toast 容器元素
let toastContainer = null

/**
 * 创建 Toast 容器
 */
function createToastContainer() {
  if (toastContainer) return
  
  toastContainer = document.createElement('div')
  toastContainer.className = 'toast-container'
  document.body.appendChild(toastContainer)
}

/**
 * 获取 Toast 图标
 * @param {string} type - Toast 类型
 * @returns {string} 图标 HTML
 */
function getToastIcon(type) {
  const icons = {
    success: '✓',
    error: '✕',
    warning: '⚠',
    info: 'ℹ'
  }
  return icons[type] || 'ℹ'
}

/**
 * 创建 Toast 元素
 * @param {string} message - 消息内容
 * @param {string} type - Toast 类型
 * @returns {HTMLElement} Toast 元素
 */
function createToast(message, type) {
  const toast = document.createElement('div')
  toast.className = `toast toast-${type}`
  
  const icon = document.createElement('span')
  icon.className = 'toast-icon'
  icon.textContent = getToastIcon(type)
  
  const messageEl = document.createElement('span')
  messageEl.className = 'toast-message'
  messageEl.textContent = message
  
  const close = document.createElement('span')
  close.className = 'toast-close'
  close.textContent = '×'
  close.addEventListener('click', () => removeToast(toast))
  
  toast.appendChild(icon)
  toast.appendChild(messageEl)
  toast.appendChild(close)
  
  return toast
}

/**
 * 移除 Toast 元素
 * @param {HTMLElement} toast - Toast 元素
 */
function removeToast(toast) {
  if (toast && toast.parentNode) {
    toast.style.animation = 'fadeOut 0.3s ease forwards'
    setTimeout(() => {
      toast.parentNode.removeChild(toast)
    }, 300)
  }
}

/**
 * 显示 Toast
 * @param {string} message - 消息内容
 * @param {string} type - Toast 类型
 */
function showToast(message, type) {
  createToastContainer()
  
  const toast = createToast(message, type)
  toastContainer.appendChild(toast)
  
  // 3秒后自动移除
  setTimeout(() => {
    removeToast(toast)
  }, 3000)
}

/**
 * Toast API
 */
export const toast = {
  /**
   * 成功消息
   * @param {string} message - 消息内容
   */
  success(message) {
    showToast(message, 'success')
  },
  
  /**
   * 错误消息
   * @param {string} message - 消息内容
   */
  error(message) {
    showToast(message, 'error')
  },
  
  /**
   * 警告消息
   * @param {string} message - 消息内容
   */
  warning(message) {
    showToast(message, 'warning')
  },
  
  /**
   * 信息消息
   * @param {string} message - 消息内容
   */
  info(message) {
    showToast(message, 'info')
  }
}
