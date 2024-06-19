package cn.wildfirechat.sdk;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.MessageContent;
import cn.wildfirechat.message.UnknownMessageContent;
import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.proto.WFCMessage;
import cn.wildfirechat.sdk.utilities.ClassUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageDecoder {
    private static final MessageDecoder instance = new MessageDecoder();

    private MessageDecoder() {
        this.registerAllMessageContent();
    }

    public static MessageDecoder getInstance() {
        return instance;
    }

    public Message deoceMessage(WFCMessage.Message protoMessage) {
        Message message = new Message();
        message.content = this.decodeMessageContent(protoMessage.getContent());
        WFCMessage.Conversation protoConversation = protoMessage.getConversation();
        message.conversation = new Conversation(Conversation.ConversationType.type(protoConversation.getType()), protoConversation.getTarget(), protoConversation.getLine());
        message.messageUid = protoMessage.getMessageId();
        message.sender = protoMessage.getFromUser();
        message.serverTime = protoMessage.getServerTimestamp();
        message.toUsers = protoMessage.getToList().toArray(new String[0]);
        return message;
    }

    // 不支持解码组合消息
    public MessageContent decodeMessageContent(WFCMessage.MessageContent protoMessageContent) {
        MessagePayload payload = new MessagePayload(protoMessageContent);
        return messageContentFromPayload(payload, null);

    }

    // 不支持解码组合消息
    private MessageContent messageContentFromPayload(MessagePayload payload, String from) {
        MessageContent content = contentOfType(payload.type);
        try {
            // 目前不支持 decode 组合消息
//            if (content instanceof CompositeMessageContent) {
//                ((CompositeMessageContent) content).decode(payload, this);
//            } else {
//                content.decode(payload);
//            }
            content.decode(payload);
//            if (content instanceof NotificationMessageContent) {
//                if (content instanceof RecallMessageContent) {
//                    RecallMessageContent recallMessageContent = (RecallMessageContent) content;
//                    if (recallMessageContent.getOperatorId().equals(userId)) {
//                        ((NotificationMessageContent) content).fromSelf = true;
//                    }
//                } else if (from.equals(userId)) {
//                    ((NotificationMessageContent) content).fromSelf = true;
//                }
//            }
            content.extra = payload.extra;
        } catch (Exception e) {
            e.printStackTrace();
            if (content.getPersistFlag() == PersistFlag.Persist || content.getPersistFlag() == PersistFlag.Persist_And_Count) {
                content = new UnknownMessageContent();
                ((UnknownMessageContent) content).setOrignalPayload(payload);
            } else {
                return null;
            }
        }
        return content;

    }

    /*
    * 从数据库读取消息参考示例
    WFCMessage.Message getMessage(long messageId) {
        String sql = "select  `_from`, `_type`, `_target`, `_line`, `_data`, `_dt` from " + MessageShardingUtil.getMessageTable(messageId) +" where _mid = ? order by `_dt` DESC limit 1";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setLong(1, messageId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                WFCMessage.Message.Builder builder = WFCMessage.Message.newBuilder();
                builder.setMessageId(messageId);
                int index = 1;
                builder.setFromUser(resultSet.getString(index++));
                WFCMessage.Conversation.Builder cb = WFCMessage.Conversation.newBuilder();
                cb.setType(resultSet.getInt(index++));
                cb.setTarget(resultSet.getString(index++));
                cb.setLine(resultSet.getInt(index++));
                builder.setConversation(cb.build());
                Blob blob = resultSet.getBlob(index++);

                WFCMessage.MessageContent messageContent = WFCMessage.MessageContent.parseFrom(encryptMessageContent(toByteArray(blob.getBinaryStream()), false));
                builder.setContent(messageContent);
                builder.setServerTimestamp(resultSet.getTimestamp(index++).getTime());
                WFCMessage.Message message = builder.build();
                return message;
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            Utility.printExecption(LOG, e, RDBS_Exception);
        } finally {
            DBUtil.closeDB(connection, statement, resultSet);
        }
        return null;
    }
     */

    private void registerAllMessageContent() {
        try {
            for (Class cls : ClassUtil.getAllAssignedClass(MessageContent.class)) {
                ContentTag annotation = (ContentTag) cls.getAnnotation(ContentTag.class);
                if (annotation != null) {
                    contentMapper.put(annotation.type(), cls);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private MessageContent contentOfType(int type) {
        Class<? extends MessageContent> cls = contentMapper.get(type);
        if (cls != null) {
            try {
                return cls.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new UnknownMessageContent();
    }

    private Map<Integer, Class<? extends MessageContent>> contentMapper = new HashMap<>();

}
