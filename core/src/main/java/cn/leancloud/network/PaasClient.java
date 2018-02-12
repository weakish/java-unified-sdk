package cn.leancloud.network;

import cn.leancloud.core.service.APIService;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.*;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import io.reactivex.Scheduler;

import java.util.concurrent.TimeUnit;

public class PaasClient {
  private static APIService apiService = null;
  private static StorageClient storageClient = null;
  static SchedulerCreator defaultScheduler = null;
  static boolean asynchronized = false;
  public static interface SchedulerCreator{
    Scheduler create();
  }

  public static void config(boolean asyncRequest, SchedulerCreator observerSchedulerCreator) {
    asynchronized = asyncRequest;
    defaultScheduler = observerSchedulerCreator;
  }

  public static StorageClient getStorageClient () {
    if (null == apiService) {
      OkHttpClient okHttpClient = new OkHttpClient.Builder()
              .connectTimeout(15, TimeUnit.SECONDS)
              .readTimeout(10, TimeUnit.SECONDS)
              .writeTimeout(10, TimeUnit.SECONDS)
              .addInterceptor(new RequestPaddingInterceptor())
              .addInterceptor(new LoggingInterceptor())
              .dns(new DNSDetoxicant())
              .build();
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("https://api.leancloud.cn")
              .addConverterFactory(FastJsonConverterFactory.create())
              .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
              .client(okHttpClient)
              .build();
      apiService = retrofit.create(APIService.class);
      storageClient = new StorageClient(apiService, asynchronized, defaultScheduler);
    }
    return storageClient;
  }


  public static void setSchedulerCreator(SchedulerCreator creator) {
    defaultScheduler = creator;
  }
}
