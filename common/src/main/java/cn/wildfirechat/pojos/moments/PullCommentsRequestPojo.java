package cn.wildfirechat.pojos.moments;

import java.util.List;

public class PullCommentsRequestPojo {
    public static class IdPair {
        public long fid;
        public long cid;
    }
    public List<IdPair> ids;
}
