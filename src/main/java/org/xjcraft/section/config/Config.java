package org.xjcraft.section.config;

import lombok.Data;
import org.xjcraft.annotation.Instance;
import org.xjcraft.annotation.RConfig;

import java.util.HashMap;
import java.util.Map;

@RConfig
@Data
public class Config {
    @Instance
    public static Config config = new Config();
    Map<String, String> whitelist = new HashMap<String, String>() {{
        put("build", "screen -S build -X stuff 'sh run.sh\\n'");
        put("test", "screen -S test -X stuff 'sh run.sh\\n'");
        put("fun", "");
    }};
    String shutdown = "服务器即将在%count%后重启！";
    String starting = "坑加载中……即将开始传送";
    Integer joinDelay = 15;


}
