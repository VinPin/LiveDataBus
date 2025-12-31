# LiveDataBus

基于 Android LiveData 实现的轻量级事件总线，替代传统 EventBus，具备生命周期感知能力，自动避免内存泄漏，使用简单、无侵入性。

1. **生命周期感知**：依托 LiveData 特性，观察者（Activity/Fragment）会根据自身生命周期自动注册 /
   解注册，无需手动解绑，彻底解决内存泄漏问题；
2. **线程安全**：内部使用 ConcurrentHashMap 管理事件通道，支持多线程并发操作，避免线程安全问题；
3. **自动创建通道**：获取事件通道时，若通道不存在则自动创建，无需提前初始化；
4. **轻量级**：无额外依赖（仅依赖 Android 官方 LiveData），体积小巧，不增加项目冗余；
5. **灵活操作**：支持获取事件数量、清除指定事件、清除所有事件等辅助操作。

## 快速开始

### 1. 集成方式

```kotlin
implementation("com.vinpin:livedatabus:1.0.1")
```

### 2. API 使用

#### 发送事件（支持在任意线程发送）

```kotlin
LiveDataBus.get<String>("MESSAGE_EVENT")?.postValue("这是一条测试消息")
```

#### 接收事件（在 Activity/Fragment 中）

```kotlin
// 接收 String 类型事件，自动感知生命周期
LiveDataBus.get<String>("MESSAGE_EVENT")?.observe(this) { message ->
    // 在这里处理接收到的事件数据
    Log.d("LiveDataBus", "接收到消息：$message")
}
```

### 3. 辅助 API 使用

```kotlin
// 1. 获取当前所有事件通道的数量
val eventCount = LiveDataBus.count()
Log.d("LiveDataBus", "当前事件总数：$eventCount")

// 2. 清除指定 key 的事件通道
LiveDataBus.clear("MESSAGE_EVENT")

// 3. 清除所有事件通道（如应用退出时）
LiveDataBus.clear()
```

## 注意事项

1. **泛型一致性**：发送事件与接收事件的泛型类型必须一致，否则会出现类型转换异常；
2. **key 唯一性**：建议使用常量管理事件 key，避免因 key 拼写错误导致事件接收失败，示例：
   ```kotlin
   object EventKey {
   const val MESSAGE_EVENT = "MESSAGE_EVENT"
   const val USER_INFO_EVENT = "USER_INFO_EVENT"
   const val LOGIN_STATUS_EVENT = "LOGIN_STATUS_EVENT"
   }
   ```
3. **粘性事件**：默认提供粘性事件开关（isSticky
   参数），粘性事件会保留历史数据，新注册的观察者能接收之前发送的事件；非粘性事件仅接收注册后发送的事件，根据业务场景选择；
4. **线程说明**：postValue 支持在任意线程调用，内部会切换到主线程分发事件；setValue 仅支持在主线程调用，否则会抛出异常。

## 适用场景

1. Activity/Fragment 之间的通信（无需通过 Intent/Bundle 传递复杂数据）；
2. 组件化项目中跨模块通信（无耦合，仅通过事件 key 交互）；
3. 后台任务（如网络请求、数据库操作）完成后通知 UI 更新；
4. 全局状态变化通知（如登录状态、用户信息更新）。

## 对比传统 EventBus

| 特性     | LiveDataBus          | 传统 EventBus                |
|--------|----------------------|----------------------------|
| 生命周期感知 | 支持（自动解绑，无内存泄漏）       | 不支持（需手动解绑，易内存泄漏）           |
| 依赖     | 仅依赖 Android LiveData | 需引入第三方依赖                   |
| 线程切换   | 自动切换主线程（无需配置）        | 需手动指定线程（如 ThreadMode.MAIN） |
| 使用复杂度  | 低（无需注解，直接调用）         | 较高（需添加 @Subscribe 注解）      |
| 内存泄漏风险 | 无                    | 有（忘记解绑时）                   |