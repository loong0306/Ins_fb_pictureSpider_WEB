package me.dragon.biz.tools;

import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dragon on 4/10/2017.
 */
public class WindowsSystemTool {
    public static void main(String[] args) {
        try {
            property();
            System.out.println("----------------------------------");
            cpu();
            System.out.println("----------------------------------");
            net();
            System.out.println("----------------------------------");
            memory();
            System.out.println("----------------------------------");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private static void property() throws UnknownHostException {
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");// 获取用户名
        String computerName = map.get("COMPUTERNAME");// 获取计算机名
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
        System.out.println("用户名:" + userName);
        System.out.println("计算机名:" + computerName);
        System.out.println("本地主机名:" + addr.getHostName());
        System.out.println("本地ip地址:" + ip);
    }

    private static void memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        float getTotal = Float.parseFloat(String.valueOf(mem.getTotal())) / 1024 / 1024 /1024;
        System.out.println("内存总量:    " + getTotal + "G av");
        float getUsed = Float.parseFloat(String.valueOf(mem.getUsed())) / 1024 / 1024 /1024;
        System.out.println("当前内存使用量:    " + getUsed + "G used");
        float getFree = Float.parseFloat(String.valueOf(mem.getFree())) / 1024 / 1024 /1024;
        System.out.println("当前内存剩余量:    " + getFree + "G free");
        Swap swap = sigar.getSwap();
    }

    private static void cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        for (int i = 0; i < infos.length; i++) {
            CpuInfo info = infos[i];
            System.out.println("第" + (i + 1) + "块CPU信息");
            printCpuPerc(cpuList[i]);
        }
    }

    private static void printCpuPerc(CpuPerc cpu) {
        System.out.println("CPU用户使用率:" + CpuPerc.format(cpu.getUser()));
        System.out.println("CPU系统使用率:" + CpuPerc.format(cpu.getSys()));
        System.out.println("CPU当前空闲率:" + CpuPerc.format(cpu.getIdle()));
        System.out.println("CPU总的使用率:" + CpuPerc.format(cpu.getCombined()));
    }


    private static void net() throws Exception {
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            if(!"0.0.0.0".equals(ifconfig.getAddress())){
                NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
                if (ifstat.getRxPackets() > 0){
                    System.out.println("网络设备名:" + name);
                    System.out.println("IP地址:" + ifconfig.getAddress());
                    System.out.println("子网掩码:" + ifconfig.getNetmask());
                    if ((ifconfig.getFlags() & 1L) <= 0L) {
                        System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                        continue;
                    }
                    System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());
                    System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());
                }
            }
        }
    }
}
