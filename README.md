
# 最新日志

版本:1.0.1

	## 框架特点
    - **快速开发**
        此项目是基于MVVMHabit做了些改造,在MVVMHabit中使用BindingCollectionAdapter替换为BaseRecyclerViewAdapterHelper,adapter 配置更加灵活。
        项目中也集成了MVP模块，MVP模块是基于androidmvp项目简化封装，可任意选择。
    	httpz 只需要写项目的业务逻辑，不用再去关心网络请求。

    - **维护方便**

    	MVVM开发模式，低耦合，逻辑分明。Model层负责将请求的数据交给ViewModel；ViewModel层负责将请求到的数据做业务逻辑处理，使用LiveData将数据交给View层去展示，与View一一对应；View层只负责界面绘制刷新，不处理业务逻辑，非常适合分配独立模块开发。

        MVP开发模式，低耦合，逻辑分明。Presenter负责调用Model，Model负责网络请求在将传递给Presenter，Presenter将数据处理完，最后交给View层去展示
    - **基类封装**

    	专门针对MVVM模式打造的　BaseModelFragment、BaseViewModel，在View层中不再需要定义ViewDataBinding和ViewModel，直接在BaseActivity、BaseFragment上限定泛型即可使用。普通界面只需要编写Fragment，然后使用ContainerActivity盛装(代理)，这样就不需要每个界面都在AndroidManifest中注册一遍。

        专门针对MVP模式打造的BaseActivity、 BaseFragment 、BasePresenter自定义，直接在BaseActivity、BaseFragment上限定泛型即可使用
    - **全局操作**
    	1. 全局的Activity堆栈式管理，在程序任何地方可以打开、结束指定的Activity，一键退出应用程序。
    	2. LoggingInterceptor全局拦截网络请求日志，打印Request和Response，格式化json、xml数据显示，方便与后台调试接口。
    	3. 全局Cookie，支持SharedPreferences和内存两种管理模式。
    	4. 通用的网络请求异常监听，根据不同的状态码或异常设置相应的message。
    	5. 全局的异常捕获，程序发生异常时不会崩溃，可跳入异常界面重启应用。
    	6. 全局事件回调，提供RxBus、Messenger两种回调方式。
    	7. 全局任意位置一行代码实现文件下载进度监听（暂不支持多文件进度监听）。
        8. 全局点击事件防抖动处理，防止点击过快。

### 1.1、启用databinding
在主工程app的build.gradle的android {}中加入：
```gradle
dataBinding {
    enabled true
}
```
### 1.2、依赖Library
从远程依赖：

在根目录的build.gradle中加入
```gradle
allprojects {
    repositories {
		...
        google()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```
在主项目app的build.gradle中依赖
```gradle
dependencies {
    ...
    implementation 'com.github.qianxiangsen521:httpz:1.01'
}
```
或

下载例子程序，在主项目app的build.gradle中依赖例子程序中的**mvvmhabit**：
```gradle
dependencies {
    ...
    implementation project(':httpz')
}
```

### 1.3、配置config.gradle
如果不是远程依赖，而是下载的例子程序，那么还需要将例子程序中的config.gradle放入你的主项目根目录中，然后在根目录build.gradle的第一行加入：

```gradle
apply from: "config.gradle"
```
### 1.4、配置AndroidManifest
添加权限：
```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
## 2、快速上手　MVVM使用，可以下载项目查看

### 2.1、第一个Fragment
> 以大家都熟悉的登录操作为例：三个文件**MainModuleFragment.java**、**MainModuleViewModel.java**、**main_module_fragment.xml**

##### 2.1.1、关联ViewModel
```xml
<layout>
    .....

</layout>
```
##### 2.1.2、MainModuleFragment
public class MainModuleFragment extends BaseModelFragment<MainModuleFragmentBinding,MainModuleViewModel> {

    public static MainModuleFragment newInstance() {
        return new MainModuleFragment();
    }

    private BaseAdapter baseAdapter;

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    protected SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_module_fragment;
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        baseAdapter = new BaseAdapter(viewModel.liveData.getValue());
        binding.recyclerView.setAdapter(baseAdapter);
        baseAdapter.getLoadMoreModule().setOnLoadMoreListener(this);

        viewModel.liveData.observe(this, recommendInfoBeans -> {
            if (isRefresh){
                baseAdapter.setList(recommendInfoBeans);
            }else {
                baseAdapter.addData(recommendInfoBeans);
            }
            if (baseAdapter.getData().size() < commonParam_fetchDataIndex){
                //如果不够一页,显示没有更多数据布局
                baseAdapter.getLoadMoreModule().loadMoreEnd();
            } else {
                baseAdapter.getLoadMoreModule().loadMoreComplete();
            }
        });
    }

    @Override
    public void onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        baseAdapter.getLoadMoreModule().setEnableLoadMore(false);
        super.onRefresh();
    }

    @Override
    public void onLoadMore() {
        // 打开或关闭加载更多功能（默认为true）
        baseAdapter.getLoadMoreModule().setEnableLoadMore(true);

        // 是否自定加载下一页（默认为true）
        baseAdapter.getLoadMoreModule().setAutoLoadMore(true);

        // 当数据不满一页时，是否继续自动加载（默认为true）
        baseAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        // 所有数据加载完成后，是否允许点击（默认为false）
        //baseAdapter.getLoadMoreModule().setEnableLoadMoreEndClick(false);

        // 是否处于加载中
        //baseAdapter.getLoadMoreModule().isLoading();

        // 预加载的位置（默认为1）
        //baseAdapter.getLoadMoreModule().setPreLoadNumber(1);

        super.onLoadMore();
    }

    @Override
    public void requestData() {
        viewModel.requestNetWork();
    }

    @Override
    public MainModuleViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return  ViewModelProviders.of(this, factory).get(MainModuleViewModel.class);
    }
}
## 3、快速上手　MVP使用，可以下载项目查看

### 3.1、第一个Fragment
> 以大家都熟悉的登录操作为例：三个文件**MainFragment.java**、**LivePrgListInteractorImpl.java**、**main_module_fragment.xml**

##### 3.1.1、关联ViewModel

```xml
<layout>
    .....

</layout>
```
##### 3.1.2、MainFragment
public class MainFragment extends BaseFragment<MainPresenter> {

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<DemoEntity.ResultBean.ItemsBean> listBeans;

    private BaseAdapter baseAdapter;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this,new LivePrgListInteractorImpl());
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_module_fragment;
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        listBeans = new ArrayList<>();
        recyclerView = mView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        baseAdapter = new BaseAdapter(listBeans);
        recyclerView.setAdapter(baseAdapter);
        baseAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void requestData() {
        if (mvpPresenter != null){
            HashMap<String,String> hashMap = new HashMap<>();
            mvpPresenter.request(hashMap);
        }
    }
    @Override
    public void onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        baseAdapter.getLoadMoreModule().setEnableLoadMore(false);
        super.onRefresh();
    }
    @Override
    public void onLoadMore() {
        // 打开或关闭加载更多功能（默认为true）
        baseAdapter.getLoadMoreModule().setEnableLoadMore(true);

        // 是否自定加载下一页（默认为true）
        baseAdapter.getLoadMoreModule().setAutoLoadMore(true);

        // 当数据不满一页时，是否继续自动加载（默认为true）
        baseAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);

        // 所有数据加载完成后，是否允许点击（默认为false）
        //baseAdapter.getLoadMoreModule().setEnableLoadMoreEndClick(false);

        // 是否处于加载中
        //baseAdapter.getLoadMoreModule().isLoading();

        // 预加载的位置（默认为1）
        //baseAdapter.getLoadMoreModule().setPreLoadNumber(1);

        super.onLoadMore();
    }

    @Override
    public void onNext(BaseResponse baseResponse) {
        if (baseResponse instanceof DemoEntity){
            DemoEntity liveProgramListResponse = (DemoEntity)baseResponse;
            if (isRefresh){
                baseAdapter.setList(liveProgramListResponse.getResult().getItems());
            }else {
                baseAdapter.addData(liveProgramListResponse.getResult().getItems());
            }
            if (baseAdapter.getData().size() < commonParam_fetchDataIndex){
                //如果不够一页,显示没有更多数据布局
                baseAdapter.getLoadMoreModule().loadMoreEnd();
            } else {
                baseAdapter.getLoadMoreModule().loadMoreComplete();
            }
        }
    }
}


