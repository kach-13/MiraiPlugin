package mirai;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.utils.BotConfiguration;

public class RunMirai  {
    public static void main(String[] args) {
        Bot bot = BotFactory.INSTANCE.newBot(1097723239, "a1097723239.com");
        bot.login();
    }
}

class WithConfiguration1 {
    public static void main(String[] args) {
        // 使用自定义配置
        Bot bot = BotFactory.INSTANCE.newBot(123456, "password", new BotConfiguration() {{
            fileBasedDeviceInfo(); // 使用 device.json 存储设备信息
//            setProtocol(MiraiProtocol.ANDROID_PAD); // 切换协议
        }});
        bot.login();
    }
}