package net.mimieye.core.core.config;

import net.mimieye.core.parse.ConfigLoader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static net.mimieye.core.model.CollectionUtils.mapOf;

/**
 * @Author: zhoulijun
 * @Time: 2019-03-13 20:53
 * @Description: 功能描述
 */
public class PropertiesModuleConfigParser implements ModuleConfigParser {
    @Override
    public String fileSuffix() {
        return "properties";
    }

    @Override
    public Map<String, Map<String, ConfigurationLoader.ConfigItem>> parse(String configFile, InputStream inputStream) throws Exception {
        Properties prop = ConfigLoader.loadProperties(inputStream);
        Map<String, ConfigurationLoader.ConfigItem> res = new HashMap<>(prop.size());
        prop.entrySet().forEach(entry-> res.put(String.valueOf(entry.getKey()),new ConfigurationLoader.ConfigItem(configFile, String.valueOf(entry.getValue()))));
        return mapOf(ConfigurationLoader.GLOBAL_DOMAIN,res);
    }
}
