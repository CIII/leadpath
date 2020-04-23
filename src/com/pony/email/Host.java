package com.pony.email;

import com.pony.publisher.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: martin
 * Date: 7/3/12
 * Time: 3:01 PM
 */
public class Host {

    private final Long id, smtpProviderId;
    private final long maxSendsDaily;
    private final String domainName, ipAddress;
    private final Status status;

    private Host(Long id, Long smtpProviderId, String domainName, String ipAddress, Status status, long maxSendsDaily) {
        this.id = id;
        this.smtpProviderId = smtpProviderId;
        this.domainName = domainName;
        this.ipAddress = ipAddress;
        this.status = status;
        this.maxSendsDaily = maxSendsDaily;
    }

    public static Host create(ResultSet rs) throws SQLException {
        Status status = Status.parse(rs.getInt("status"));
        return create(rs.getLong("id"), rs.getLong("smtp_provider_id"), rs.getString("domain_name"), rs.getString("ip_address"), status, rs.getLong("max_sends_daily"));
    }

    public static Host create(Long id, long smtpProviderId, String domainName, String ipAddress, Status status, long maxSendsDaily) {
        return new Host(id, smtpProviderId, domainName, ipAddress, status, maxSendsDaily);
    }

    public Long getId() {
        return id;
    }

    public Long getSmtpProviderId() {
        return smtpProviderId;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Status getStatus() {
        return status;
    }

    public long getMaxSendsDaily() {
        return maxSendsDaily;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Host host = (Host) o;

        return domainName.equals(host.domainName);
    }

    @Override
    public int hashCode() {
        return domainName.hashCode();
    }

    @Override
    public String toString() {
        return "Host{" +
                ", id=" + id +
                ", smtpProviderId=" + smtpProviderId +
                ", domainName='" + domainName + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", status=" + status +
                '}';
    }

    public static SendCount createSendCount(Host host) {
        return new SendCount(host);
    }

    public static SendCount createSendCount(Long hostId, Long count) {
        return new SendCount(hostId, count);
    }

    public static class SendCount {

        private final Long hostId;
        private Long count;

        private SendCount(Host host) {
            this(host.getId(), 0L);
        }

        private SendCount(Long id, Long count) {
            this.hostId = id;
            this.count = count;
        }

        public Long getCount() {
            return count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            SendCount sendCount = (SendCount) o;

            return hostId.equals(sendCount.hostId);
        }

        @Override
        public String toString() {
            return "SendCount{hostId=" + hostId + ", count=" + count + '}';
        }

        @Override
        public int hashCode() {
            return hostId.hashCode();
        }

        void increment() {
            count++;
        }
    }

}
