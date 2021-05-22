/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.mimieye;

import io.nuls.base.basic.AddressTool;
import net.mimieye.core.core.ioc.SpringLiteContext;
import net.mimieye.server.RpcServerManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: PierreLuo
 * @date: 2019-07-10
 */
public class HttpServerBootstrap {
    public static void main(String[] args) {
        SpringLiteContext.init("net.mimieye");
        initConfig();
        AddressTool.addPrefix(5, "TNVT");
        AddressTool.addPrefix(9, "NERVE");
        RpcServerManager.getInstance().startServer(System.getProperty("ip", "127.0.0.1"), Integer.parseInt(System.getProperty("port", "9898")));
    }

    private static void initConfig() {
        try(InputStream in = HttpServerBootstrap.class.getClassLoader().getResourceAsStream("server.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            properties.stringPropertyNames().stream().forEach(name -> System.setProperty(name, String.valueOf(properties.get(name))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
