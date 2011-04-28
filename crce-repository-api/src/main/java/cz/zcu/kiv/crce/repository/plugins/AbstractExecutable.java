package cz.zcu.kiv.crce.repository.plugins;

import java.util.Properties;

/**
 *
 * @author Jiri Kucera (kalwi@students.zcu.cz, kalwi@kalwi.eu)
 */
public abstract class AbstractExecutable implements Executable {

    @Override
    public boolean isExclusive() {
        return false;
    }

    @Override
    public Properties getProperties() {
        return new Properties();
    }

}