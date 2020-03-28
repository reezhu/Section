package org.xjcraft.section.config;

import lombok.Data;
import org.xjcraft.annotation.Instance;
import org.xjcraft.annotation.RConfig;

import java.util.ArrayList;
import java.util.List;

@RConfig
@Data
public class Config {
    @Instance
    public static Config config = new Config();
    List<String> whitelist = new ArrayList<String>() {{
        add("build");
        add("test");
        add("fun");
    }};
    String shutdown = "服务器即将在%count%后重启！";


}
