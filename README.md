# 无人机信息管理系统

> **核心定位**：基于 Spring Boot + Vue 3 的前后端分离架构，实现无人机信息的录入、查询、修改与删除；后端提供 REST API，前端通过 API 与后端交互。工作区根目录部署 Harness 监管层，对 `baseplatform` 子项目自动生效。

---

## 项目概述

本系统用于管理无人机基础信息。无人机属性字段由 AI 自动生成，支持通过 Web 界面完成 CRUD 操作。初期版本使用 SQLite 作为默认数据库，后续可通过配置切换至 MySQL，无需修改业务代码。

| 特性 | 说明 |
|------|------|
| 前后端分离 | Vue 3 前端与 Spring Boot 后端完全独立部署，通过 REST API 交换数据 |
| 多数据库支持 | SQLite（初期默认）、MySQL，配置文件切换 |
| AI 辅助建模 | 无人机属性字段由 AI 自动生成 |
| 工程规范 | Harness 六阶段研发流程 + 四层架构约束 |

---

## 技术栈

### 系统环境

| 组件 | 版本 |
|------|------|
| Java EE | 8 |
| Servlet | 3.0 |
| Apache Maven | 3.x |

### 主框架

| 组件 | 版本 |
|------|------|
| Spring Boot | 2.2.x |
| Spring Framework | 5.2.x |
| Apache Shiro | 1.7 |

### 持久层

| 组件 | 版本 |
|------|------|
| Apache MyBatis | 3.5.x |
| Hibernate Validation | 6.0.x |
| Alibaba Druid | 1.2.x |

### 视图层

| 组件 | 版本 |
|------|------|
| Bootstrap | 4 |
| Vue | 3 |

---

## 业务功能

### 无人机信息管理

| 功能 | 说明 |
|------|------|
| 录入 | 新增无人机信息，属性字段由 AI 自动生成 |
| 查询 | 按条件检索无人机列表及详情 |
| 修改 | 更新已有无人机信息 |
| 删除 | 删除指定无人机记录 |

---

## 架构设计

### 前后端分离

```
┌─────────────┐       REST API        ┌──────────────────┐
│  Vue 3 前端  │  ◄──────────────────►  │  Spring Boot 后端 │
│ Bootstrap 4 │                        │  MyBatis + Shiro  │
└─────────────┘                        └────────┬─────────┘
                                                │
                                    ┌───────────┴───────────┐
                                    │  SQLite  /  MySQL     │
                                    └───────────────────────┘
```

### 后端分层与包结构

服务层与数据操作层采用**接口与实现分离**设计：

```
com.md.basePlatform/
├── config/                          # 配置类（数据源、MyBatis、Shiro、CORS 等）
├── common/                          # 公共工具、统一响应体
├── exception/                       # 全局异常处理
├── interceptor/                     # 拦截器（独立包，打印请求信息）
├── controller/                      # REST 控制器
├── service/
│   ├── DroneService.java            # 服务接口
│   └── impl/
│       └── DroneServiceImpl.java    # 服务实现
├── domain/                          # 实体与 DTO
└── repository/
    ├── DroneMapper.java             # 数据操作接口（MyBatis Mapper）
    └── impl/                        # 自定义 Repository 实现（如有）
```

### 拦截器

- 拦截器统一放在 `interceptor` 包
- 执行时打印请求信息（请求 URI、HTTP 方法、客户端 IP、请求参数等）
- 通过 Spring MVC `HandlerInterceptor` 或 Shiro Filter 链注册

### 数据库切换

通过 `application.properties` / `application.yml` 配置数据源类型，初期默认 SQLite：

```properties
# 初期默认：SQLite
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.datasource.url=jdbc:sqlite:./data/drone.db

# 切换 MySQL 时修改以下配置
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.datasource.url=jdbc:mysql://localhost:3306/drone?useSSL=false&serverTimezone=UTC
# spring.datasource.username=root
# spring.datasource.password=
```

Druid 连接池统一管理数据源，MyBatis 负责 SQL 映射，切换数据库仅需修改配置。

---

## 补充设计（需求遗漏项）

除上述明确要求外，补充以下设计以保证系统可维护性与前后端协作顺畅：

| 项 | 说明 |
|----|------|
| RESTful API 规范 | 统一 URL 命名与 HTTP 动词语义（GET 查询、POST 新增、PUT 修改、DELETE 删除） |
| 统一响应格式 | `{ code, message, data }` 结构，便于前端统一处理 |
| 全局异常处理 | `@ControllerAdvice` 捕获业务异常与校验异常，返回标准错误响应 |
| 参数校验 | 使用 Hibernate Validation 注解校验请求体，配合 `@Valid` |
| CORS 跨域配置 | 前后端分离部署时，后端配置允许前端域名跨域访问 |
| 分页查询 | 列表接口支持分页参数（page、size），避免一次性返回大量数据 |
| API 文档 | 使用 Swagger / OpenAPI 自动生成接口文档，前后端联调依据 |
| 日志规范 | 拦截器 + SLF4J 记录请求链路，业务层记录关键操作日志 |
| 环境配置分离 | `application-dev.yml` / `application-prod.yml` 区分开发与生产环境 |

---

## 目录结构

```
Workspace_Root/
├── .cursor/                         ← Harness AI 规则与 hooks
├── harness-collab/                  ← 需求、设计、API 文档等协作文档
├── config/                          ← Checkstyle、SpotBugs 配置
├── baseplatform/                    ← Spring Boot 后端子项目
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/md/basePlatform/
│       │   │   ├── config/
│       │   │   ├── common/
│       │   │   ├── exception/
│       │   │   ├── interceptor/
│       │   │   ├── controller/
│       │   │   ├── service/         # 接口
│       │   │   │   └── impl/        # 实现
│       │   │   ├── domain/
│       │   │   └── repository/      # Mapper 接口
│       │   └── resources/
│       │       ├── application.yml
│       │       └── mapper/          # MyBatis XML 映射文件
│       └── test/
├── frontend/                        ← Vue 3 前端项目
│   ├── package.json
│   └── src/
│       ├── api/
│       └── views/drone/
├── pom.xml                          ← Harness 父 POM（多 Profile 质量门禁）
├── req1.md                          ← 项目需求文档
└── README.md                        ← 本文件
```

---

## 快速入门

### 环境要求

- JDK 8+
- Apache Maven 3.x
- Node.js 16+（前端开发）

### 前端启动

```bash
cd frontend
npm install
npm run dev
```

访问 http://localhost:5173（需先启动后端）。

### 后端启动

```bash
cd baseplatform
mvn spring-boot:run
```

### 前端启动（待创建）

```bash
cd frontend
npm install
npm run dev
```

### 质量门禁

```bash
# 新项目全量门禁（Checkstyle + SpotBugs + JaCoCo ≥ 80%）
mvn clean verify -Pharness-new -pl baseplatform

# 历史项目宽松检查
mvn clean verify -Pharness-legacy -pl baseplatform
```

---

## 研发流程（Harness）

Cursor Agent 在本工作区内遵循 Dev_Lifecycle 六阶段流程：

1. **需求分析** → `harness-collab/01-product-specs/`（参考 `req1.md`）
2. **技术设计** → `harness-collab/02-design-docs/`
3. **编码实现** → 四层架构 + 接口/实现分离 + 同步测试
4. **测试验证** → 覆盖率 ≥ 80%，记录到 `harness-collab/03-exec-plans/`
5. **文档同步** → 更新 `harness-collab/04-api-docs/` 与 `func.md`
6. **CI 发布** → `mvn clean verify -Pharness-new` 通过后创建 PR

完整协议：[harness-collab/AGENTS.md](harness-collab/AGENTS.md)

---

## 相关文档

| 文档 | 说明 |
|------|------|
| [req1.md](req1.md) | 项目需求文档 |
| [AGENTS.md](AGENTS.md) | AI 协作协议入口 |
| [harness-collab/AGENTS.md](harness-collab/AGENTS.md) | AI 协作协议完整版 |
| [harness-collab/05-methodology/architecture-constraints.md](harness-collab/05-methodology/architecture-constraints.md) | 四层架构约束 |
| [harness-collab/06-adapters/bootstrap-guide.md](harness-collab/06-adapters/bootstrap-guide.md) | 新项目接入指南 |
