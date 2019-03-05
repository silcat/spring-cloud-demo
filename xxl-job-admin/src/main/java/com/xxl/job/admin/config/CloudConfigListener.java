package com.renrendai.loan.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;
import javax.management.QueryExp;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.*;

public class CloudConfigListener implements ApplicationListener<ApplicationPreparedEvent> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CloudConfigListener.class);

    static List<String> ignoredInterfaces = Arrays.asList("docker0");

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        ConfigurableApplicationContext ctx = event.getApplicationContext();
        setServerPort(ctx);
//        setServiceUrl(ctx);
    }

    private void setServerPort(ConfigurableApplicationContext ctx) {
        ctx.getEnvironment().getSystemProperties().put("server.port", getNonSecurePort());
    }

    private void setServiceUrl(ConfigurableApplicationContext ctx) {
        try {
            String contextPath = ((WebApplicationContext) ctx).getServletContext().getContextPath();
            String host = getLocalHostLANAddress().getHostAddress();
            String ep = "http://" + host + ":" + getNonSecurePort() + contextPath;
            ctx.getEnvironment().getSystemProperties().put("spring.boot.admin.client.serviceUrl", ep);
        } catch (UnknownHostException e) {
            throw new RuntimeException("设置spring.boot.admin.client.serviceUrl失败", e);
        }
    }

    private String getNonSecurePort() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            QueryExp subQuery1 = Query.match(Query.attr("protocol"), Query.value("HTTP/1.1"));
            QueryExp subQuery2 = Query.anySubString(Query.attr("protocol"), Query.value("Http11"));
            QueryExp query = Query.or(subQuery1, subQuery2);
            Set<ObjectName> objs = mbs.queryNames(new ObjectName("*:type=Connector,*"), query);
            String port = "";
            for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
                ObjectName obj = i.next();
                String scheme = mbs.getAttribute(obj, "scheme").toString();
                if ("https".equals(scheme)) {
                    continue;
                }
                port = obj.getKeyProperty("port");
                if (!StringUtils.isNumeric(port)) {
                    port = mbs.getAttribute(obj, "localPort").toString();
                }
            }
            LOGGER.info("Get port : {}", port);
            return port;
        } catch (Exception e) {
            throw new RuntimeException("获取tomcat端口失败", e);
        }
    }

    private static boolean ignoreInterface(String interfaceName) {
        for (String regex : ignoredInterfaces) {
            if (interfaceName.matches(regex)) {
                LOGGER.info("Ignoring interface: {}", interfaceName);
                return true;
            }
        }
        return false;
    }

    private static InetAddress getLocalHostLANAddress() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // Iterate all NICs (network interface cards)...
            for (final Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces(); ifaces
                    .hasMoreElements();) {
                final NetworkInterface iface = ifaces.nextElement();

                if (ignoreInterface(iface.getDisplayName())) {
                    continue;
                }
                // Iterate all IP addresses assigned to each card...
                for (final Enumeration<InetAddress> inetAddrs = iface.getInetAddresses(); inetAddrs
                        .hasMoreElements();) {
                    final InetAddress inetAddr = inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {
                        if (inetAddr.isSiteLocalAddress()) {
                            // Found non-loopback site-local address. Return it
                            // immediately...
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // Found non-loopback address, but not necessarily
                            // site-local.
                            // Store it as a candidate to be returned if
                            // site-local address is not subsequently found...
                            candidateAddress = inetAddr;
                            // Note that we don't repeatedly assign non-loopback
                            // non-site-local addresses as candidates,
                            // only the first. For subsequent iterations,
                            // candidate will be non-null.
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other
                // non-loopback address.
                // Server might have a non-site-local address assigned to its
                // NIC (or it might be running
                // IPv which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress;
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost()
            // returns...
            final InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (final Exception e) {
            final UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}
