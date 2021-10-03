package io.github.dunwu.module.mnt.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Linux 远程 scp 命令工具
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-10-02
 */
public class ScpClientUtil {

    private static final Map<String, ScpClientUtil> INSTANCE = Maps.newHashMap();

    private final String ip;
    private final int port;
    private final String username;
    private final String password;

    public static synchronized ScpClientUtil getInstance(String ip, int port, String username, String password) {
        if (INSTANCE.get(ip) == null) {
            INSTANCE.put(ip, new ScpClientUtil(ip, port, username, password));
        }
        return INSTANCE.get(ip);
    }

    public ScpClientUtil(String ip, int port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void getFile(String remoteFile, String localTargetDirectory) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            client.get(remoteFile, localTargetDirectory);
        } catch (IOException ex) {
            Logger.getLogger(SCPClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conn.close();
        }
    }

    public void putFile(String localFile, String remoteTargetDirectory) {
        putFile(localFile, null, remoteTargetDirectory);
    }

    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory) {
        putFile(localFile, remoteFileName, remoteTargetDirectory, null);
    }

    public void putFile(String localFile, String remoteFileName, String remoteTargetDirectory, String mode) {
        Connection conn = new Connection(ip, port);
        try {
            conn.connect();
            boolean isAuthenticated = conn.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                System.err.println("authentication failed");
            }
            SCPClient client = new SCPClient(conn);
            if ((mode == null) || (mode.length() == 0)) {
                mode = "0600";
            }
            if (remoteFileName == null) {
                client.put(localFile, remoteTargetDirectory);
            } else {
                client.put(localFile, remoteFileName, remoteTargetDirectory, mode);
            }
        } catch (IOException ex) {
            Logger.getLogger(ScpClientUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conn.close();
        }
    }

}
