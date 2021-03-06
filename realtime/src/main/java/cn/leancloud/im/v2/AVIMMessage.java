package cn.leancloud.im.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import cn.leancloud.utils.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AVIMMessage {
  protected String conversationId;
  protected String content;
  protected String from;
  protected long timestamp;
  protected long deliveredAt;
  protected long readAt;
  protected long updateAt;

  protected List<String> mentionList = null;
  protected boolean mentionAll = false;
  protected String currentClient = null;

  protected String messageId;
  protected String uniqueToken;

  protected AVIMMessageStatus status;
  protected AVIMMessageIOType ioType;

  public AVIMMessage() {
    this(null, null);
  }

  public AVIMMessage(String conversationId, String from) {
    this(conversationId, from, 0, 0);
  }

  public AVIMMessage(String conversationId, String from, long timestamp, long deliveredAt) {
    this(conversationId, from, timestamp, deliveredAt, 0);
  }

  public AVIMMessage(String conversationId, String from, long timestamp, long deliveredAt, long readAt) {
    this.ioType = AVIMMessageIOType.AVIMMessageIOTypeOut;
    this.status = AVIMMessageStatus.AVIMMessageStatusNone;
    this.conversationId = conversationId;
    this.from = from;
    this.timestamp = timestamp;
    this.deliveredAt = deliveredAt;
    this.readAt = readAt;
  }

  /**
   * 获取当前聊天对话对应的id
   * <p/>
   * 对应的是AVOSRealtimeConversations表中的objectId
   *
   * @return
   * @since 3.0
   */
  public String getConversationId() {
    return conversationId;
  }

  /**
   * 设置消息所在的conversationId，本方法一般用于从反序列化时
   *
   * @param conversationId
   */
  public void setConversationId(String conversationId) {
    this.conversationId = conversationId;
  }

  /**
   * 获取消息体的内容
   *
   * @return
   * @since 3.0
   */
  public String getContent() {
    return content;
  }

  /**
   * 设置消息体的内容
   *
   * @param content
   * @since 3.0
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * 获取消息的发送者
   *
   * @return
   */
  public String getFrom() {
    return from;
  }

  /**
   * 设置消息的发送者
   *
   * @param from
   * @since 3.7.3
   */
  public void setFrom(String from) {
    this.from = from;
  }

  /**
   * 获取消息发送的时间
   *
   * @return
   */
  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  /**
   * @deprecated Please use {@link #getDeliveredAt()}
   * 获取消息成功到达接收方的时间
   *
   * @return
   * @see AVIMConversation#RECEIPT_MESSAGE_FLAG
   */
  public long getReceiptTimestamp() {
    return getDeliveredAt();
  }

  /**
   * @deprecated Please use {@link #setDeliveredAt(long)}
   *
   * @return
   * @see AVIMConversation#RECEIPT_MESSAGE_FLAG
   */
  public void setReceiptTimestamp(long receiptTimestamp) {
    setDeliveredAt(receiptTimestamp);
  }

  /**
   * 设置消息成功到达接收方的时间
   * @return
   */
  void setDeliveredAt(long deliveredAt) {
    this.deliveredAt = deliveredAt;
  }

  /**
   * 获取消息成功到达接收方的时间
   * @return
   */
  public long getDeliveredAt() {
    return deliveredAt;
  }

  public void setReadAt(long readAt) {
    this.readAt = readAt;
  }

  public long getReadAt() {
    return readAt;
  }

  /**
   * set the update time of the message
   * @param updateAt
   */
  public void setUpdateAt(long updateAt) {
    this.updateAt = updateAt;
  }

  /**
   * get the update time of the message
   * @return
   */
  public long getUpdateAt() {
    return updateAt;
  }

  /**
   * 设置消息当前的状态，本方法一般用于从反序列化时
   *
   * @param status
   */
  public void setMessageStatus(AVIMMessageStatus status) {
    this.status = status;
  }

  /**
   * 获取消息当前的状态
   *
   * @return
   */

  public AVIMMessageStatus getMessageStatus() {
    return this.status;
  }

  /**
   * 获取消息IO类型
   *
   * @return
   */
  public AVIMMessageIOType getMessageIOType() {
    return ioType;
  }

  /**
   * 设置消息的IO类型，本方法一般用于反序列化
   *
   * @param ioType
   */
  public void setMessageIOType(AVIMMessageIOType ioType) {
    this.ioType = ioType;
  }

  /**
   * 获取消息的全局Id
   * <p/>
   * 这个id只有在发送成功或者收到消息时才会有对应的值
   *
   * @return
   */
  public String getMessageId() {
    return messageId;
  }

  /**
   * 仅仅是用于反序列化消息时使用，请不要在其他时候使用
   *
   * @param messageId
   */
  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }
  /**
   * 判断消息里面是否 mention 了当前用户
   * @return
   */
  public boolean mentioned() {
    return isMentionAll() || (null != mentionList && mentionList.contains(currentClient));
  }

  /**
   * 设置 mention 用户列表
   * @param peerIdList mention peer id list
   */
  public void setMentionList(List<String> peerIdList) {
    this.mentionList = peerIdList;
  }

  /**
   * 获取 mention 用户列表
   * @return
   */
  public List<String> getMentionList() {
    return this.mentionList;
  }

  /**
   * 获取 mention 用户列表的字符串（逗号分隔）
   * @return
   */
  @JSONField(serialize = false, deserialize = false)
  public String getMentionListString() {
    if (null == this.mentionList) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < this.mentionList.size(); i++) {
      builder.append(this.mentionList.get(i));
      if (i != this.mentionList.size() - 1) {
        builder.append(",");
      }
    }
    return builder.toString();
  }

  /**
   * 设置 mention 用户列表字符串（逗号分隔），功能与 #setMentionList(List<String> peerIdList) 相同，两者调用一个即可。
   * @param content
   */
  @JSONField(serialize = false, deserialize = false)
  public void setMentionListString(String content) {
    if (StringUtil.isEmpty(content)) {
      this.mentionList = null;
    } else {
      this.mentionList = new ArrayList<>();
      String[] peerIdArray = content.split("[,\\s]");
      for (String peer: peerIdArray) {
        if (!StringUtil.isEmpty(peer)) {
          this.mentionList.add(peer);
        }
      }
    }
  }

  /**
   * 判断是否 mention 了所有人
   * @return
   */
  public boolean isMentionAll() {
    return mentionAll;
  }

  /**
   * 设置是否 mention 所有人
   * @param mentionAll
   */
  public void setMentionAll(boolean mentionAll) {
    this.mentionAll = mentionAll;
  }

  void setCurrentClient(String clientId) {
    this.currentClient = clientId;
  }

  protected synchronized void generateUniqueToken() {
    if (StringUtil.isEmpty(uniqueToken)) {
      uniqueToken = UUID.randomUUID().toString();
    }
  }

  public void setUniqueToken(String uniqueToken) {
    this.uniqueToken = uniqueToken;
  }

  public String getUniqueToken() {
    return uniqueToken;
  }

  // FIXME
  public int hashCode() {
    return (uniqueToken + messageId + conversationId).hashCode();
  }

  public boolean equals(Object other) {
    if (null == other) {
      return false;
    }
    if (this == other) {
      return true;
    }
    if (!(other instanceof AVIMMessage)) {
      return false;
    }
    AVIMMessage otherMsg = (AVIMMessage) other;
    return StringUtil.equals(conversationId, otherMsg.conversationId)
            && StringUtil.equals(content, otherMsg.content)
            && StringUtil.equals(from, otherMsg.from) && (timestamp == otherMsg.timestamp)
            && (deliveredAt == otherMsg.deliveredAt) && (readAt == otherMsg.readAt) && (updateAt == otherMsg.updateAt)
            && (getMessageStatus() == otherMsg.getMessageStatus())
            && (getMessageIOType() == otherMsg.getMessageIOType())
            && StringUtil.equals(messageId, otherMsg.messageId)
            && StringUtil.equals(mentionList, otherMsg.mentionList)
            && (mentionAll == otherMsg.mentionAll)
            && StringUtil.equals(uniqueToken, otherMsg.uniqueToken);
  }

  public String toJSONString() {
    return JSON.toJSONString(this);
  }

  public static AVIMMessage parseJSONString(String content) {
    if (StringUtil.isEmpty(content)) {
      return null;
    }
    return JSON.parseObject(content, AVIMMessage.class);
  }

  public enum AVIMMessageStatus {
    AVIMMessageStatusNone(0), AVIMMessageStatusSending(1), AVIMMessageStatusSent(2),
    AVIMMessageStatusReceipt(3), AVIMMessageStatusFailed(4), AVIMMessageStatusRecalled(5);
    int statusCode;

    AVIMMessageStatus(int status) {
      this.statusCode = status;
    }

    public int getStatusCode() {
      return statusCode;
    }

    public static AVIMMessageStatus getMessageStatus(int statusCode) {
      switch (statusCode) {
        case 0:
          return AVIMMessageStatusNone;
        case 1:
          return AVIMMessageStatusSending;
        case 2:
          return AVIMMessageStatusSent;
        case 3:
          return AVIMMessageStatusReceipt;
        case 4:
          return AVIMMessageStatusFailed;
        case 5:
          return AVIMMessageStatusRecalled;
        default:
          return null;
      }
    }
  }


  public enum AVIMMessageIOType {
    /**
     * 标记收到的消息
     */
    AVIMMessageIOTypeIn(1),
    /**
     * 标记发送的消息
     */
    AVIMMessageIOTypeOut(2);
    int ioType;

    AVIMMessageIOType(int type) {
      this.ioType = type;
    }

    public int getIOType() {
      return ioType;
    }

    public static AVIMMessageIOType getMessageIOType(int type) {
      switch (type) {
        case 1:
          return AVIMMessageIOTypeIn;
        case 2:
          return AVIMMessageIOTypeOut;
      }
      return AVIMMessageIOTypeOut;
    }
  }
}
