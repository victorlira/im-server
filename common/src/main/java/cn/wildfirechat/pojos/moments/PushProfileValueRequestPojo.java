package cn.wildfirechat.pojos.moments;

public class PushProfileValueRequestPojo {
    /*
    WFMUpdateUserProfileType_BackgroudUrl,
    WFMUpdateUserProfileType_StrangerVisiableCount,
    WFMUpdateUserProfileType_VisiableScope
     */
    public int t;

    //string value depends on t
    public String v;

    //int value depends on t
    public int i;
}
