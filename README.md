# LeanCloud Java/Android Unified SDK(WIP)

LeanCloud Unified SDK 包含 LeanCloud 平台全部功能的客户端接口，适用于 Java 和 Android 两个平台。

SDK 中所有存储 API 接口与 LeanCloud 云端交互严格遵循 LeanCloud REST API 规范，并且全部采用了 Reactive 函数式编程风格来设计（同时也兼容老的 Callback 方式）。

要想了解更多使用信息，请查看 [Wiki](https://github.com/leancloud/java-sdk-all/wiki).

## Development Progress

注意：此 SDK 当前还在开发中，我们会逐步提供全平台的功能。最新进度如下：

### 存储服务 library（Java/Android 两个平台）
- [x] AVObject 增删改查
- [x] AVQuery 与 AVCloudQuery
- [x] AVUser 内建账户系统
- [x] Role 与 ACL
- [ ] AVFriendship 与 AVStatus（请注意目前还不支持）

### 云引擎 library
- [x] EngineFunction
- [x] EngineHook
- [x] IMHook

### 实时通讯核心 library
- [x] LiveQuery
- [x] RTM

### Android 推送和实时通讯 library
- [x] LiveQuery
- [x] RTM
- [x] Push

### Android 混合推送 library
- [x] 华为 HMS 推送
- [x] 小米推送
- [x] 魅族推送
- [x] Firebase Cloud Messaging

## Bugs and Feedback
大家使用中发现 Bug、或者有任何疑问或建议，请使用  [GitHub Issues](https://github.com/leancloud/java-sdk-all/issues) 来告知我们，非常感谢大家的反馈。

## License

```
Copyright (c) 2017-present, Meiwei Shuqian(Beijing) Information Technology Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
