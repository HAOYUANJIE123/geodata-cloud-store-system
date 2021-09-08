package test.vo;

/**
 * Created by lai on 2019/5/11.
 */
public class FriendClass {
    private static String friendid;

    private static String friendname;

    public static String getFriendid() {
        return friendid;
    }

    public static void setFriendid(String friendid) {
        FriendClass.friendid = friendid;
    }

    public static String getFriendname() {
        return friendname;
    }

    public static void setFriendname(String friendname) {
        FriendClass.friendname = friendname;
    }
}
