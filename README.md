# Winfae_Architecture_Android
- 设计目的是用于搭建一个可快速进行业务开发的Android框架
- 同时利用自身提供的丰富控件及兼容处理
- 让开发者能专注于业务需求而无需耗费精力在基础代码的设计上。使业务的开发大大简化，大幅度提升开发效率和项目质量。

## 引用依赖说明
```
    模块gradle顶部
        黄油刀基本标配
        apply plugin: 'com.jakewharton.butterknife'
    路由库
       annotationProcessor "com.alibaba:arouter-compiler:$rootProject.arouterCompilerVersion"

    以下是业务研发基本需要依赖的库
    compile project(path: ':projectcore')
```

## 模块介绍
### app
提供 App壳工程，集合了其他所有模块。
#####功能：
1. 负责模块的拆解组合
2. Splash引导
3. 支持组件化的编译，模块独立编译。

### projectCore
- 业务开发模块的核心库
- 依赖于底层Base库
    api project(path: ':basePlatform:core')
    api project(path: ':basePlatform:net')
    api project(':otherLib:customview')
- 所有其他模块依赖后，都会继承这里的依赖关系

### basePlatform-network
- 提供简单易用的网络调用框架。
- 请注意需要用Observable进行调用。Call的方式不纳入管理。

### basePlatform-core
- 提供baseMVP，baseActivity，baseFragment之类的基础模块；
- 提供丰富常用的 UI 控件，例如 BottomSheet、Tab、圆角 ImageView、下拉刷新等，使用方便灵活，并且支持自定义控件的样式。
- 提供高效的工具方法，包括设备信息、屏幕信息、键盘管理、状态栏管理等，可以解决各种常见场景并大幅度提升开发效率。

### otherLib:customview
- 主要是存放我们自己的lib，以及某些三方lib特殊的
- 完全独立的库

#编码基本原则
- 代码函数依照 public protected private排序
- 其余参考AliLint的规范