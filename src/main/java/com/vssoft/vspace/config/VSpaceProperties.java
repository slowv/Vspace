package com.vssoft.vspace.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "vspace", ignoreUnknownFields = false)
public class VSpaceProperties {

    private final Security security = new Security();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final Cache cache = new Cache();
    private final Logging logging = new Logging();
    private final Mail mail = new Mail();
    private final ApiDocs apiDocs = new ApiDocs();
    private final ClientApp clientApp = new ClientApp();
    private final AuditEvents auditEvents = new AuditEvents();

    public static class Security {

        private String contentSecurityPolicy = VSpaceDefaults.Security.contentSecurityPolicy;

        private final ClientAuthorization clientAuthorization = new ClientAuthorization();

        private final Authentication authentication = new Authentication();

        private final RememberMe rememberMe = new RememberMe();

        private final OAuth2 oauth2 = new OAuth2();

        public ClientAuthorization getClientAuthorization() {
            return clientAuthorization;
        }

        public Authentication getAuthentication() {
            return authentication;
        }

        public RememberMe getRememberMe() {
            return rememberMe;
        }

        public OAuth2 getOauth2() {
            return oauth2;
        }

        public String getContentSecurityPolicy() {
            return contentSecurityPolicy;
        }

        public void setContentSecurityPolicy(String contentSecurityPolicy) {
            this.contentSecurityPolicy = contentSecurityPolicy;
        }

        @Data
        public static class ClientAuthorization {

            private String accessTokenUri = VSpaceDefaults.Security.ClientAuthorization.accessTokenUri;

            private String tokenServiceId = VSpaceDefaults.Security.ClientAuthorization.tokenServiceId;

            private String clientId = VSpaceDefaults.Security.ClientAuthorization.clientId;

            private String clientSecret = VSpaceDefaults.Security.ClientAuthorization.clientSecret;
        }

        @Getter
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Data
            public static class Jwt {

                private String secret = VSpaceDefaults.Security.Authentication.Jwt.secret;

                private String base64Secret = VSpaceDefaults.Security.Authentication.Jwt.base64Secret;

                private long tokenValidityInSeconds = VSpaceDefaults.Security.Authentication.Jwt
                        .tokenValidityInSeconds;

                private long tokenValidityInSecondsForRememberMe = VSpaceDefaults.Security.Authentication.Jwt
                        .tokenValidityInSecondsForRememberMe;
            }
        }

        public static class RememberMe {

            @NotNull
            private String key = VSpaceDefaults.Security.RememberMe.key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class OAuth2 {
            private List<String> audience = new ArrayList<>();

            public List<String> getAudience() {
                return Collections.unmodifiableList(audience);
            }

            public void setAudience(@NotNull List<String> audience) {
                this.audience.addAll(audience);
            }
        }
    }

    public static class Cache {

        private final Hazelcast hazelcast = new Hazelcast();

        private final Caffeine caffeine = new Caffeine();

        private final Ehcache ehcache = new Ehcache();

        private final Infinispan infinispan = new Infinispan();

        private final Memcached memcached = new Memcached();

        private final Redis redis = new Redis();

        public Hazelcast getHazelcast() {
            return hazelcast;
        }

        public Caffeine getCaffeine() {
            return caffeine;
        }

        public Ehcache getEhcache() {
            return ehcache;
        }

        public Infinispan getInfinispan() {
            return infinispan;
        }

        public Memcached getMemcached() {
            return memcached;
        }

        public Redis getRedis() {
            return redis;
        }

        public static class Hazelcast {

            private int timeToLiveSeconds = VSpaceDefaults.Cache.Hazelcast.timeToLiveSeconds;

            private int backupCount = VSpaceDefaults.Cache.Hazelcast.backupCount;

            public int getTimeToLiveSeconds() {
                return timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public int getBackupCount() {
                return backupCount;
            }

            public void setBackupCount(int backupCount) {
                this.backupCount = backupCount;
            }
        }

        public static class Caffeine {

            private int timeToLiveSeconds = VSpaceDefaults.Cache.Caffeine.timeToLiveSeconds;

            private long maxEntries = VSpaceDefaults.Cache.Caffeine.maxEntries;

            public int getTimeToLiveSeconds() {
                return timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public long getMaxEntries() {
                return maxEntries;
            }

            public void setMaxEntries(long maxEntries) {
                this.maxEntries = maxEntries;
            }
        }

        public static class Ehcache {

            private int timeToLiveSeconds = VSpaceDefaults.Cache.Ehcache.timeToLiveSeconds;

            private long maxEntries = VSpaceDefaults.Cache.Ehcache.maxEntries;

            public int getTimeToLiveSeconds() {
                return timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public long getMaxEntries() {
                return maxEntries;
            }

            public void setMaxEntries(long maxEntries) {
                this.maxEntries = maxEntries;
            }
        }

        public static class Infinispan {

            private String configFile = VSpaceDefaults.Cache.Infinispan.configFile;

            private boolean statsEnabled = VSpaceDefaults.Cache.Infinispan.statsEnabled;

            private final Local local = new Local();

            private final Distributed distributed = new Distributed();

            private final Replicated replicated = new Replicated();

            public String getConfigFile() {
                return configFile;
            }

            public void setConfigFile(String configFile) {
                this.configFile = configFile;
            }

            public boolean isStatsEnabled() {
                return statsEnabled;
            }

            public void setStatsEnabled(boolean statsEnabled) {
                this.statsEnabled = statsEnabled;
            }

            public Local getLocal() {
                return local;
            }

            public Distributed getDistributed() {
                return distributed;
            }

            public Replicated getReplicated() {
                return replicated;
            }

            public static class Local {

                private long timeToLiveSeconds = VSpaceDefaults.Cache.Infinispan.Local.timeToLiveSeconds;

                private long maxEntries = VSpaceDefaults.Cache.Infinispan.Local.maxEntries;

                public long getTimeToLiveSeconds() {
                    return timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }

            }

            public static class Distributed {

                private long timeToLiveSeconds = VSpaceDefaults.Cache.Infinispan.Distributed.timeToLiveSeconds;

                private long maxEntries = VSpaceDefaults.Cache.Infinispan.Distributed.maxEntries;

                private int instanceCount = VSpaceDefaults.Cache.Infinispan.Distributed.instanceCount;

                public long getTimeToLiveSeconds() {
                    return timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }

                public int getInstanceCount() {
                    return instanceCount;
                }

                public void setInstanceCount(int instanceCount) {
                    this.instanceCount = instanceCount;
                }
            }

            public static class Replicated {

                private long timeToLiveSeconds = VSpaceDefaults.Cache.Infinispan.Replicated.timeToLiveSeconds;

                private long maxEntries = VSpaceDefaults.Cache.Infinispan.Replicated.maxEntries;

                public long getTimeToLiveSeconds() {
                    return timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }

            }
        }

        public static class Memcached {

            private boolean enabled = VSpaceDefaults.Cache.Memcached.enabled;

            /**
             * Comma or whitespace separated list of servers' addresses.
             */
            private String servers = VSpaceDefaults.Cache.Memcached.servers;

            private int expiration = VSpaceDefaults.Cache.Memcached.expiration;

            private boolean useBinaryProtocol = VSpaceDefaults.Cache.Memcached.useBinaryProtocol;

            private Authentication authentication = new Authentication();

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getServers() {
                return servers;
            }

            public void setServers(String servers) {
                this.servers = servers;
            }

            public int getExpiration() {
                return expiration;
            }

            public void setExpiration(int expiration) {
                this.expiration = expiration;
            }

            public boolean isUseBinaryProtocol() {
                return useBinaryProtocol;
            }

            public void setUseBinaryProtocol(boolean useBinaryProtocol) {
                this.useBinaryProtocol = useBinaryProtocol;
            }

            public Authentication getAuthentication() {
                return authentication;
            }

            public static class Authentication {

                private boolean enabled = VSpaceDefaults.Cache.Memcached.Authentication.enabled;
                private String username;
                private String password;

                public boolean isEnabled() {
                    return enabled;
                }

                public Authentication setEnabled(boolean enabled) {
                    this.enabled = enabled;
                    return this;
                }

                public String getUsername() {
                    return username;
                }

                public Authentication setUsername(String username) {
                    this.username = username;
                    return this;
                }

                public String getPassword() {
                    return password;
                }

                public Authentication setPassword(String password) {
                    this.password = password;
                    return this;
                }
            }
        }

        public static class Redis {
            private String[] server = VSpaceDefaults.Cache.Redis.server;
            private int expiration = VSpaceDefaults.Cache.Redis.expiration;
            private boolean cluster = VSpaceDefaults.Cache.Redis.cluster;
            private int connectionPoolSize = VSpaceDefaults.Cache.Redis.connectionPoolSize;
            private int connectionMinimumIdleSize = VSpaceDefaults.Cache.Redis.connectionMinimumIdleSize;
            private int subscriptionConnectionPoolSize = VSpaceDefaults.Cache.Redis.subscriptionConnectionPoolSize;
            private int subscriptionConnectionMinimumIdleSize = VSpaceDefaults.Cache.Redis.subscriptionConnectionMinimumIdleSize;

            public String[] getServer() {
                return server;
            }

            public void setServer(String[] server) {
                this.server = server;
            }

            public int getExpiration() {
                return expiration;
            }

            public void setExpiration(int expiration) {
                this.expiration = expiration;
            }

            public boolean isCluster() {
                return cluster;
            }

            public void setCluster(boolean cluster) {
                this.cluster = cluster;
            }

            public int getConnectionPoolSize() {
                return connectionPoolSize;
            }

            public Redis setConnectionPoolSize(int connectionPoolSize) {
                this.connectionPoolSize = connectionPoolSize;
                return this;
            }

            public int getConnectionMinimumIdleSize() {
                return connectionMinimumIdleSize;
            }

            public Redis setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
                this.connectionMinimumIdleSize = connectionMinimumIdleSize;
                return this;
            }

            public int getSubscriptionConnectionPoolSize() {
                return subscriptionConnectionPoolSize;
            }

            public Redis setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
                this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
                return this;
            }

            public int getSubscriptionConnectionMinimumIdleSize() {
                return subscriptionConnectionMinimumIdleSize;
            }

            public Redis setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
                this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
                return this;
            }
        }
    }

    public static class Logging {

        private boolean useJsonFormat = VSpaceDefaults.Logging.useJsonFormat;

        private final Logstash logstash = new Logstash();

        public boolean isUseJsonFormat() {
            return useJsonFormat;
        }

        public void setUseJsonFormat(boolean useJsonFormat) {
            this.useJsonFormat = useJsonFormat;
        }

        public Logstash getLogstash() {
            return logstash;
        }

        public static class Logstash {

            private boolean enabled = VSpaceDefaults.Logging.Logstash.enabled;

            private String host = VSpaceDefaults.Logging.Logstash.host;

            private int port = VSpaceDefaults.Logging.Logstash.port;

            private int ringBufferSize = VSpaceDefaults.Logging.Logstash.ringBufferSize;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public int getRingBufferSize() {
                return ringBufferSize;
            }

            public void setRingBufferSize(int ringBufferSize) {
                this.ringBufferSize = ringBufferSize;
            }
        }
    }

    public static class Mail {

        private boolean enabled = VSpaceDefaults.Mail.enabled;

        private String from = VSpaceDefaults.Mail.from;

        private String baseUrl = VSpaceDefaults.Mail.baseUrl;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    public static class ApiDocs {

        private String title = VSpaceDefaults.ApiDocs.title;

        private String description = VSpaceDefaults.ApiDocs.description;

        private String version = VSpaceDefaults.ApiDocs.version;

        private String termsOfServiceUrl = VSpaceDefaults.ApiDocs.termsOfServiceUrl;

        private String contactName = VSpaceDefaults.ApiDocs.contactName;

        private String contactUrl = VSpaceDefaults.ApiDocs.contactUrl;

        private String contactEmail = VSpaceDefaults.ApiDocs.contactEmail;

        private String license = VSpaceDefaults.ApiDocs.license;

        private String licenseUrl = VSpaceDefaults.ApiDocs.licenseUrl;

        private String[] defaultIncludePattern = VSpaceDefaults.ApiDocs.defaultIncludePattern;

        private String[] managementIncludePattern = VSpaceDefaults.ApiDocs.managementIncludePattern;

        private Server[] servers = {};

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String[] getDefaultIncludePattern() {
            return defaultIncludePattern;
        }

        public void setDefaultIncludePattern(String[] defaultIncludePattern) {
            this.defaultIncludePattern = defaultIncludePattern;
        }

        public String[] getManagementIncludePattern() {
            return managementIncludePattern;
        }

        public void setManagementIncludePattern(String[] managementIncludePattern) {
            this.managementIncludePattern = managementIncludePattern;
        }

        public Server[] getServers() {
            return servers;
        }

        public void setServers(Server[] servers) {
            this.servers = servers;
        }

        public static class Server {
            private String url;
            private String description;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }

    public static class ClientApp {

        private String name = VSpaceDefaults.ClientApp.name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class AuditEvents {
        private int retentionPeriod = VSpaceDefaults.AuditEvents.retentionPeriod;

        public int getRetentionPeriod() {
            return retentionPeriod;
        }

        public void setRetentionPeriod(int retentionPeriod) {
            this.retentionPeriod = retentionPeriod;
        }
    }

}
