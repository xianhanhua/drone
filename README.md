# 无人机信息管理系统

这是一个前后端分离的小型管理系统，用来录入、查询、修改和删除无人机信息。

## 怎么打开

1. 双击 `start-backend.bat` 启动后端。
2. 看到后端窗口停在运行状态后，双击 `open-frontend.bat` 打开网页。
3. 登录账号：

```text
admin / admin123
```

如果后端端口被占用，可以先双击 `stop-backend.bat`，再重新运行 `start-backend.bat`。

## 保留的入口文件

- `start-backend.bat`：启动后端服务，端口是 `8091`。
- `open-frontend.bat`：打开前端页面。
- `stop-backend.bat`：停止后端服务。

## 文件夹说明

- `backend`：后端代码和 SQLite 数据库。
- `frontend`：前端页面。
- `tools`：本项目使用的本地 Maven。

## 数据库

当前默认使用 SQLite，数据库文件在：

```text
backend/data/drone-management.db
```

这个文件不要随便删除，里面保存着页面里的无人机数据。
