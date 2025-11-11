package Gamelogic;

import java.util.*;

public class UserBlocker {


    /** מחלקה לניהול חסימות מישתמשים בהיתחברות
     *
     */
    private final Map<String, Long> blockedUsers = new HashMap<>();


    public void blockUser(String username, int seconds) {
        long until = System.currentTimeMillis() + (seconds * 1000L);
        blockedUsers.put(username, until);
        System.out.println("🔒 המשתמש " + username + " נחסם עד: " + new Date(until));
    }


    public boolean canLogin(String username) {
        Long until = blockedUsers.get(username);
        if (until == null) {
            return true; // המשתמש לא חסום
        }
        if (System.currentTimeMillis() > until) {
            blockedUsers.remove(username); // שחרור אוטומטי אחרי זמן
            return true;
        }
        return false;
    }

    /**
     * שחרור ידני של משתמש (אופציונלי)
     */
    public void unblockUser(String username) {
        blockedUsers.remove(username);
        System.out.println("🔓 המשתמש " + username + " שוחרר ידנית");
    }

    /**
     * הצגת כל המשתמשים החסומים כרגע
     */
    public List<String> getCurrentlyBlocked() {
        long now = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Long> entry : blockedUsers.entrySet()) {
            if (entry.getValue() > now) {
                list.add(entry.getKey());
            }
        }
        return list;
    }
}
