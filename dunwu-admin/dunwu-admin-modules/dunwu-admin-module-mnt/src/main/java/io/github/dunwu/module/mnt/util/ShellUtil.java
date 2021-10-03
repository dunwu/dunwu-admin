package io.github.dunwu.module.mnt.util;

import cn.hutool.core.io.IoUtil;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * Shell 命令工具
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
@Slf4j
public class ShellUtil {

    private Vector<String> stdout;

    private Session session;

    public ShellUtil(final String ipAddress, final String username, final String password, int port) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, ipAddress, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(3000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public int execute(final String command) {
        int returnCode = 0;
        ChannelShell channel = null;
        PrintWriter printWriter = null;
        BufferedReader input = null;
        stdout = new Vector<>();
        try {
            channel = (ChannelShell) session.openChannel("shell");
            channel.connect();
            input = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            printWriter = new PrintWriter(channel.getOutputStream());
            printWriter.println(command);
            printWriter.println("exit");
            printWriter.flush();
            log.info("The remote command is: ");
            String line;
            while ((line = input.readLine()) != null) {
                stdout.add(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        } finally {
            IoUtil.close(printWriter);
            IoUtil.close(input);
            if (channel != null) {
                channel.disconnect();
            }
        }
        return returnCode;
    }

    public void close() {
        if (session != null) {
            session.disconnect();
        }
    }

    public String executeForResult(String command) {
        execute(command);
        StringBuilder sb = new StringBuilder();
        for (String str : stdout) {
            sb.append(str);
        }
        return sb.toString();
    }

    public void stopPidByPort(int port) {
        execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));
    }

    public boolean isPortRunning(int port) {
        String result = executeForResult(String.format("fuser -n tcp %d", port));
        return result.indexOf("/tcp:") > 0;
    }

}
