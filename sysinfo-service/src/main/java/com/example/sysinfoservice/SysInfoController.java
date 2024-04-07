package com.example.sysinfoservice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.hardware.GlobalMemory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class SysInfoController {

    private String osName;
    private String osVersion;
    private String osArch;
    private int processorsCount;
    private double systemLoadAverage;
    private LocalDateTime bootTime;
    private long totalMemory;
    private long usedMemory;
    private double memoryUsagePercentage;

    public static OsInfo getOsInfo() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();

        OsInfo osInfo = new OsInfo();
        osInfo.osName = operatingSystem.getName();
        osInfo.osVersion = operatingSystem.getVersion();
        osInfo.osArch = operatingSystem.getArch();
        osInfo.processorsCount = hardware.getProcessors().getLogicalProcessorCount();
        osInfo.systemLoadAverage = hardware.getProcessor().getSystemLoadAverage(1);
        osInfo.bootTime = operatingSystem.getSystemBootTime();

        return osInfo;
    }
    public static SystemStatus getSystemStatus() {
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        GlobalMemory memory = hardware.getMemory();

        SystemStatus systemStatus = new SystemStatus();
        systemStatus.osName = operatingSystem.getName();
        systemStatus.osVersion = operatingSystem.getVersion();
        systemStatus.osArch = operatingSystem.getArch();
        systemStatus.processorsCount = hardware.getProcessors().getLogicalProcessorCount();
        systemStatus.systemLoadAverage = hardware.getProcessor().getSystemLoadAverage(1);
        systemStatus.bootTime = operatingSystem.getSystemBootTime();
        systemStatus.totalMemory = memory.getTotal();
        systemStatus.usedMemory = memory.getTotal() - memory.getAvailable();
        systemStatus.memoryUsagePercentage = FormatUtil.formatPercentage((double) systemStatus.usedMemory / systemStatus.totalMemory);

        return systemStatus;
    }

    // ... 构造函数、getter/setter 方法以及其他辅助方法
    @GetMapping("/os/info")
    public OsInfo getOsInfo() {
        return OsInfo.getOsInfo();
    }


    @GetMapping("/os/status")
    public SystemStatus getSystemStatus() {
        return SystemStatus.getSystemStatus();
    }
}


