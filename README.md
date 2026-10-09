# Karen

Android / Kotlin / Compose 示例应用。

## Windows 构建与验证

使用 JDK 17 或 21、Gradle 8.7 和 Android SDK Platform 35；在本地 `local.properties` 设置 `sdk.dir`，不要提交电脑上的路径。Kotlin 2.0 使用同版本 Compose 编译器插件。

```powershell
.\gradlew.bat :app:assembleDebug testDebugUnitTest :app:lintDebug
```

Debug APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。测试覆盖 HTTP GET/POST、UTF-8 字节长度、TLS 证书与主机名校验、Fragment 视图重建、Activity 重建及两个弹窗恢复。TLS 测试使用本地测试证书，不连接外部服务器。

Robolectric 首次运行需要下载 Android 测试运行库。如果该下载环境不可用，可准备 Robolectric 4.17 所需的 API 28 instrumented jar，并传入 `-ProbolectricSdkDir=<本地目录>`；正常构建无需这个参数。

## 基础类迁移

- `BaseFragment` 子类实现 `inflateBinding(inflater, container)`。`mBinding` 仅在视图存在期间可访问，`onDestroyView` 后引用释放。
- `BaseDialogFragment` 使用无参可重建子类。通过 `createContentView` / `bindContent` 创建视图和事件，通过 `arguments` 与保存状态传递可恢复数据。原来的视图对象和回调构造参数已移除；本仓库调用方已迁移。
- Flow 收集跟随视图生命周期；计时更新仅在 Activity 为 STARTED 时运行。Compose 内容在视图生命周期结束后释放。
- HTTPS 默认使用平台信任库与主机名校验。自签名服务器需要配置合法信任链，不能依赖原来的全部信任行为。

Android Gradle Plugin 8.5.2 对 compileSdk 35 有工具版本警告；旧依赖与其他静态检查警告另需分批升级。JVM 测试不代替真机上的图片选择、进程重启、手势及 Android 15 界面体验验证。
