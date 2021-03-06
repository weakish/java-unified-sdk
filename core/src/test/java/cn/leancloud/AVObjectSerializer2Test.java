package cn.leancloud;

import cn.leancloud.core.AVOSCloud;
import cn.leancloud.types.AVGeoPoint;
import cn.leancloud.utils.LogUtil;
import com.alibaba.fastjson.JSON;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AVObjectSerializer2Test extends TestCase {
  private static final AVLogger LOGGER = LogUtil.getLogger(AVObjectSerializer2Test.class);
  private static final String CLASSNAME_STUDENT = "Student";
  private static final String FILE_OBJECT_ID = "5bff45249f54540066d4d829";
  private String studentId = null;
  public AVObjectSerializer2Test(String name) {
    super(name);
    Configure.initializeRuntime();
  }
  public static Test suite() {
    return new TestSuite(AVObjectSerializer2Test.class);
  }

  @Override
  protected void setUp() throws Exception {
  }

  @Override
  protected void tearDown() throws Exception {
  }

  public void testComplexObjectDescerializer() {
    AVQuery q = new AVQuery("Student");
    q.orderByDescending("createdAt");
    q.whereExists("portrait");
    AVObject o = q.getFirst();
//    if (o.getAVFile("portrait") == null) {
//      AVFile portrait = new AVFile("thumbnail", "https://tvax1.sinaimg.cn/crop.0.0.200.200.180/a8d43f7ely1fnxs86j4maj205k05k74f.jpg");
//      o.put("portrait", portrait);
//      o.save();
//    }
    System.out.println(o.toJSONString());
    AVObject newO = AVObject.parseAVObject(o.toJSONString());
    assertTrue(null != newO);
    assertTrue(null != newO.getAVFile("portrait"));

    String arhiveString = ArchivedRequests.getArchiveContent(o, false);
    System.out.println(arhiveString);
    AVObject newV = ArchivedRequests.parseAVObject(arhiveString);
    System.out.println(newV.toJSONString());
    assertTrue(null != newV);
    assertTrue(null != newV.getAVFile("portrait"));
  }

  public void testDeserializedWithOperationQueueData() throws Exception {
    String json = "{ \"@type\":\"com.avos.avoscloud.AVObject\",\"objectId\":\"5b4f1e0dfe88c200357fda80\",\"updatedAt\":\"2018-12-25T15:18:51.574Z\",\"createdAt\":\"2018-07-18T11:01:33.931Z\",\"className\":\"Caricature\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"update\":\"更新至201话\",\"read_url\":\"http://ac.qq.com/ComicView/index/id/624388/cid/3\",\"type\":\"\",\"des\":\"洪明轩-外号“雪豹”这个传说中的格斗高手竟然——遭遇校园冷暴力?!被逼无奈，只能转学的他决心隐藏自己的真正实力，结果转学第一天就惹到了学校老大？还被学校里的一群麻烦家伙缠上了，“雪豹”到底该如何抉择呢，要展示一下自己的真正实力吗？【授权/周四六更新】\",\"tag\":\"校园 爆笑\",\"views\":3.3E+8,\"source_name\":\"腾讯动漫\",\"order\":1891,\"author\":\"作者：KTOON / YUYU \",\"isValid\":\"1\",\"book_img_url\":\"https://manhua.qpic.cn/vertical/0/08_22_58_fb3925ac11acc5063f244ad2aab3aad7_1507474687604.jpg/420\",\"url\":\"http://ac.qq.com/Comic/comicInfo/id/624388\",\"name\":\"进击吧,雪豹\",\"category\":\"\"},\"operationQueue\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"lastUrl\":{\"@type\":\"com.avos.avoscloud.ops.SetOp\",\"key\":\"lastUrl\",\"type\":\"Set\",\"values\":\"https://m.ac.qq.com/chapter/index/id/624388/cid/3\"}}}";
    AVObject obj = AVObject.parseAVObject(json);
    assertTrue(null != obj);

    json = "{ \"@type\":\"com.avos.avoscloud.AVObject\",\"objectId\":\"5b04d56a9f545400880c1e00\",\"updatedAt\":\"2018-05-30T03:33:51.183Z\",\"createdAt\":\"2018-05-23T02:43:54.804Z\",\"className\":\"EnglishWebsite\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"Order\":599,\"ad_filte\":\"class:app-guiding bottom-fixed no-close#class:app-guiding#class:down-app-mask\",\"Title\":\"腾讯动漫\",\"category\":\"caricature\",\"ImgUrl\":\"http://www.mzxbkj.com/images/cartoon/cartoon_001.png\",\"Url\":\"http://m.ac.qq.com/\",\"IsValid\":\"1\"}}";
    obj = AVObject.parseAVObject(json);
    assertTrue(null != obj);
  }

  public void testDeserializedWithOldVersionStatus() throws Exception {
    String json = "{\"@type\":\"com.avos.avoscloud.AVStatus\",\"objectId\":\"5b4f1e0dfe88c200357fda80\",\"updatedAt\":\"2018-12-25T15:18:51.574Z\",\"createdAt\":\"2018-07-18T11:01:33.931Z\",\"className\":\"_Status\",\"dataMap\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"update\":\"更新至201话\"},\"inboxType\":\"default\", \"messageId\":\"pp-guiding bottom-fixed no-close#c\",\"source\":\"\"}";
    AVStatus status = (AVStatus) AVObject.parseAVObject(json);
    assertTrue(null != status);
    System.out.println(status);
  }

  public void testDeserializAVObject() throws Exception {
    String json = "{ \"@type\":\"com.avos.avoscloud.AVInstallation\",\"objectId\":\"0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs\",\"updatedAt\":null,\"createdAt\":\"2018-12-28T03:16:19.239Z\",\"className\":\"_Installation\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"deviceType\":\"android\",\"timeZone\":\"Asia/Shanghai\",\"installationId\":\"fd6605e9a1679d355457ad5c37fc99b3\"}}";
    AVInstallation installation = (AVInstallation) AVObject.parseAVObject(json);
    assertTrue(null != installation);
    assertTrue(installation.getObjectId().equals("0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs"));

    json = "{ \"@type\":\"com.avos.avoscloud.AVObject\",\"objectId\":\"0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs\",\"updatedAt\":null,\"createdAt\":\"2018-12-28T03:16:19.239Z\",\"className\":\"Student\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"deviceType\":\"android\",\"timeZone\":\"Asia/Shanghai\",\"installationId\":\"fd6605e9a1679d355457ad5c37fc99b3\"}}";
    AVObject student = (AVObject) AVObject.parseAVObject(json);
    assertTrue(null != student);
    assertTrue(student.getObjectId().equals("0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs"));

    json = "{ \"@type\":\"com.avos.avoscloud.AVUser\",\"objectId\":\"5c282efc9f54540070f04e9a\",\"updatedAt\":\"2018-12-30T02:35:40.331Z\",\"createdAt\":\"2018-12-30T02:35:40.331Z\",\"className\":\"_User\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"emailVerified\":false,\"password\":\"qwertyuiop\",\"sessionToken\":\"bckq6t7gvvfns2t7eer9imxq1\",\"portrait\":{\"@type\":\"com.avos.avoscloud.AVFile\",\"dataAvailable\":true,\"dirty\":false,\"metaData\":{\"@type\":\"java.util.HashMap\",\"_name\":\"thumbnail\",\"__source\":\"external\"},\"name\":\"5c282efc808ca4565c079bc6\",\"objectId\":\"5c282efc808ca4565c079bc6\",\"originalName\":\"thumbnail\",\"size\":-1,\"url\":\"https://tvax1.sinaimg.cn/crop.0.0.200.200.180/a8d43f7ely1fnxs86j4maj205k05k74f.jpg\"},\"mobilePhoneVerified\":false,\"username\":\"er@a.com\"}}";
    AVUser user = (AVUser) AVObject.parseAVObject(json);
    assertTrue(null != user);
    assertTrue(user.getObjectId().equals("5c282efc9f54540070f04e9a"));
    AVFile userProfile = user.getAVFile("portrait");
    assertTrue(null != userProfile);

    json = "{ \"@type\":  \"com.avos.avoscloud.AVStatus\",\"objectId\":\"0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs\",\"updatedAt\":null,\"createdAt\":\"2018-12-28T03:16:19.239Z\",\"className\":\"_Status\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"deviceType\":\"android\",\"timeZone\":\"Asia/Shanghai\",\"installationId\":\"fd6605e9a1679d355457ad5c37fc99b3\"}}";
    AVStatus status = (AVStatus) AVObject.parseAVObject(json);
    assertTrue(null != status);
    assertTrue(status.getObjectId().equals("0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs"));

    json = "{ \"@type\":\"com.avos.avoscloud.AVFile\",\"objectId\":\"0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs\",\"updatedAt\":null,\"createdAt\":\"2018-12-28T03:16:19.239Z\",\"className\":\"_File\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"deviceType\":\"android\",\"timeZone\":\"Asia/Shanghai\",\"installationId\":\"fd6605e9a1679d355457ad5c37fc99b3\"}}";
    AVFile file = (AVFile) AVObject.parseAVObject(json);
    assertTrue(null != file);
    assertTrue(file.getObjectId().equals("0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs"));

    try {
      json = "{ \"@type\":\"com.avos.avoscloud.AVPushOOO\",\"objectId\":\"0qYaOiU08hqm8bgpDk4CrTXXBs1NPtSs\",\"updatedAt\":null,\"createdAt\":\"2018-12-28T03:16:19.239Z\",\"className\":\"Push\",\"serverData\":{\"@type\":\"java.util.concurrent.ConcurrentHashMap\",\"deviceType\":\"android\",\"timeZone\":\"Asia/Shanghai\",\"installationId\":\"fd6605e9a1679d355457ad5c37fc99b3\"}}";
      AVObject o = (AVObject) AVObject.parseAVObject(json);
      System.out.println(o);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
