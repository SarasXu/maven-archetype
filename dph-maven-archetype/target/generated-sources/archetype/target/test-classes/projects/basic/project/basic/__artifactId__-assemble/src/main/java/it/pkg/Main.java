package it.pkg;


import com.dph.common.utils.annotation.AppBoot;
import com.dph.common.utils.base.Boot;
import org.mybatis.spring.annotation.MapperScan;

/**
 * description: saras_xu@163.com 2017-04-01 16:22 创建
 */
@AppBoot(env = "local", port = 9098)
@MapperScan("it.pkg.mapper")
public class Main {
    public static void main(String[] args) {
        Boot.run(Main.class);
        System.out.println("当前运行系统:" + System.getProperty("os.name"));
        System.out.println("********************************************************");
    }
}
