package com.dph.temp.base;

import com.dph.temp.Main;
import com.dph.common.utils.base.Apps;
import org.springframework.boot.test.SpringApplicationConfiguration;

/**
 * description:
 * saras_xu@163.com 2017-04-19 13:40 创建
 */
@SpringApplicationConfiguration(classes = Main.class)
public class TestBase extends AppTestBase {
    protected static final String PROFILE = "test";

    static {
        Apps.setProfileIfNotExists(PROFILE);
    }
}
