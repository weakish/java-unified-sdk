package cn.leancloud;

import cn.leancloud.codec.Base64Encoder;
import cn.leancloud.codec.MD5;
import cn.leancloud.core.AVOSCloud;
import cn.leancloud.types.AVNull;
import cn.leancloud.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class AVFileTest extends TestCase {
  private boolean testSucceed = false;
  private CountDownLatch latch = null;
  public AVFileTest(String name) {
    super(name);
    Configure.initializeRuntime();
  }

  public static Test suite() {
    return new TestSuite(AVFileTest.class);
  }

  @Override
  protected void setUp() throws Exception {
    testSucceed = false;
    latch = new CountDownLatch(1);
  }

  @Override
  protected void tearDown() throws Exception {
    ;
  }

  public void testCreateWithObjectId() throws Exception {
    String fileObjectId = "5c2a1c4d808ca4565c20f0f2";
    AVFile.withObjectIdInBackground(fileObjectId).subscribe(new Observer<AVFile>() {
      public void onSubscribe(Disposable disposable) {

      }

      public void onNext(AVFile avFile) {
        System.out.println(avFile);
        String url = avFile.getUrl();
        String name = avFile.getName();
        String key = avFile.getKey();
        int size = avFile.getSize();
        String objectId = avFile.getObjectId();
        String thumbnailUrl = avFile.getThumbnailUrl(true, 200, 200);
        String mimeType = avFile.getMimeType();
        System.out.println("url=" + url + ", name=" + name + ", key=" + key + ", size=" + size);
        System.out.println("objId=" + objectId + ", thumbnail=" + thumbnailUrl + ", mime=" + mimeType);
        testSucceed = url.length() > 0 && thumbnailUrl.length() > 0 && name.length() > 0 && key.length() > 0;
        testSucceed = testSucceed && (size > 0 && objectId.equals("5c2a1c4d808ca4565c20f0f2"));
        testSucceed = testSucceed && (mimeType.length() > 0);
        latch.countDown();
      }

      public void onError(Throwable throwable) {
        latch.countDown();
      }

      public void onComplete() {
      }
    });
    latch.await();
    assertTrue(testSucceed);
  }

  public void testCreateWithExtension() throws Exception {
    File localFile = new File("./20160704174809.jpeg");
    AVFile file = new AVFile("test.jpeg", localFile);
    Observable<AVFile> result = file.saveInBackground();
    result.subscribe(new Observer<AVFile>() {
      public void onSubscribe(Disposable disposable) {
      }

      public void onNext(AVFile avFile) {
        System.out.println("[Thread:" + Thread.currentThread().getId() +
                "]succeed to upload file. objectId=" + avFile.getObjectId());
        avFile.deleteInBackground().subscribe(new Observer<AVNull>() {
          public void onSubscribe(Disposable disposable) {

          }

          public void onNext(AVNull aVoid) {
            System.out.println("[Thread:" + Thread.currentThread().getId() + "]succeed to delete file.");
            testSucceed = true;
            latch.countDown();
          }

          public void onError(Throwable throwable) {
            latch.countDown();
          }

          public void onComplete() {

          }
        });
      }

      public void onError(Throwable throwable) {
        latch.countDown();
      }

      public void onComplete() {

      }
    });
    latch.await();
    assertTrue(testSucceed);
  }

  public void testBase64Data() throws Exception {
    String contents = StringUtil.getRandomString(640);
    AVFile file = new AVFile("testfilename", contents.getBytes());
//    Map<String, Object> metaData = new HashMap<>();
//    metaData.put("format", "dat file");
//    file.setMetaData(metaData);
    file.setACL(new AVACL());
    file.saveInBackground().subscribe(new Observer<AVFile>() {
      @Override
      public void onSubscribe(Disposable disposable) {

      }

      @Override
      public void onNext(AVFile avFile) {
        testSucceed = true;
        latch.countDown();
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
        latch.countDown();
      }

      @Override
      public void onComplete() {

      }
    });
    latch.await();
    assertTrue(file.getObjectId().length() > 0);
  }

  public void testExternalFile2() throws Exception {
    String url = "http://i1.wp.com/blog.avoscloud.com/wp-content/uploads/2014/05/screen568x568-1.jpg?resize=202%2C360";
    AVFile file = new AVFile("screen.jpg", url);
    file.saveInBackground().subscribe(new Observer<AVFile>() {
      @Override
      public void onSubscribe(Disposable disposable) {

      }

      @Override
      public void onNext(AVFile avFile) {
        testSucceed = true;
        latch.countDown();
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
        latch.countDown();
      }

      @Override
      public void onComplete() {

      }
    });
    latch.await();
    assertTrue(testSucceed);
  }

  public void testExternalFile() throws Exception {
    AVFile portrait = new AVFile("thumbnail", "https://tvax1.sinaimg.cn/crop.0.0.200.200.180/a8d43f7ely1fnxs86j4maj205k05k74f.jpg");
    portrait.saveInBackground().subscribe(new Observer<AVFile>() {
      @Override
      public void onSubscribe(Disposable disposable) {

      }

      @Override
      public void onNext(AVFile avFile) {
        avFile.delete();
        testSucceed = true;
        latch.countDown();
      }

      @Override
      public void onError(Throwable throwable) {
        throwable.printStackTrace();
        latch.countDown();
      }

      @Override
      public void onComplete() {

      }
    });

    latch.await();
    assertTrue(testSucceed);
  }

  public void testBlockSave() throws Exception {
    AVFile leanFile = new AVFile("name.txt", "name".getBytes());
    leanFile.save();
  }

  public void testUploader() throws Exception {
    String contents = StringUtil.getRandomString(640);
    AVFile file = new AVFile("test", contents.getBytes());
    Observable<AVFile> result = file.saveInBackground();
    result.subscribe(new Observer<AVFile>() {
      public void onSubscribe(Disposable disposable) {

      }

      public void onNext(AVFile avFile) {
        System.out.println("[Thread:" + Thread.currentThread().getId() +
                "]succeed to upload file. objectId=" + avFile.getObjectId());
        avFile.deleteInBackground().subscribe(new Observer<AVNull>() {
          public void onSubscribe(Disposable disposable) {

          }

          public void onNext(AVNull aVoid) {
            System.out.println("[Thread:" + Thread.currentThread().getId() + "]succeed to delete file.");
            testSucceed = true;
            latch.countDown();
          }

          public void onError(Throwable throwable) {
            latch.countDown();
          }

          public void onComplete() {

          }
        });
      }

      public void onError(Throwable throwable) {
        latch.countDown();
      }

      public void onComplete() {

      }
    });
    latch.await();
    assertTrue(testSucceed);
  }
}
