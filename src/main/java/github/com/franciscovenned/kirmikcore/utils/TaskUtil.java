package github.com.franciscovenned.kirmikcore.utils;

import github.com.franciscovenned.kirmikcore.KirmikCore;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtil {

    public static void runTaskAsync(Runnable runnable) {
        KirmikCore.getInstance().getServer().getScheduler().runTaskAsynchronously(KirmikCore.getInstance(), runnable);
    }

    public static void runTaskLater(Runnable runnable, long delay) {
        KirmikCore.getInstance().getServer().getScheduler().runTaskLater(KirmikCore.getInstance(), runnable, delay);
    }
    public static void runTaskLaterAsync(Runnable runnable, long delay) {
        KirmikCore.getInstance().getServer().getScheduler().runTaskLaterAsynchronously(KirmikCore.getInstance(), runnable, delay);
    }

    public static void runTaskTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(KirmikCore.getInstance(), delay, timer);
    }

    public static void runTaskTimer(Runnable runnable, long delay, long timer) {
        KirmikCore.getInstance().getServer().getScheduler().runTaskTimer(KirmikCore.getInstance(), runnable, delay, timer);
    }

    public static void runTask(Runnable runnable) {
        KirmikCore.getInstance().getServer().getScheduler().runTask(KirmikCore.getInstance(), runnable);
    }
}
