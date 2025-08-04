package net.jmp.spring.boot.valkey.sail;

/*
 * (#)SailConfig.java   0.5.0   08/04/2025
 *
 * @author   Jonathan Parker
 *
 * MIT License
 *
 * Copyright (c) 2025 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/// The Sail configuration class.
///
/// @version    0.5.0
/// @since      0.5.0
public class SailConfig {
    /// The host name.
    private final String hostName;

    /// The port.
    private final int port;

    /// The client name.
    private final String clientName;

    /// True if SSL is in use.
    private final boolean useSSL;

    /// The constructor.
    ///
    /// @param  builder net.jmp.spring.boot.valkey.sail.SailConfig.Builder
    private SailConfig(final Builder builder) {
        this.hostName = builder.hostName;
        this.port = builder.port;
        this.clientName = builder.clientName;
        this.useSSL = builder.useSSL;
    }

    /// Return the builder for this class.
    ///
    /// @return net.jmp.spring.boot.valkey.sail.SailConfig.Builder
    public static Builder builder() {
        return new Builder();
    }

    /// Return the host name.
    ///
    /// @return java.lang.String
    public String getHostName() {
        return this.hostName;
    }

    /// Return the port.
    ///
    /// @return int
    public int getPort() {
        return this.port;
    }

    /// Return the client name.
    ///
    /// @return java.lang.String
    public String getClientName() {
        return this.clientName;
    }

    /// Return true if SSL is in use.
    ///
    /// @return boolean
    public boolean isUseSSL() {
        return this.useSSL;
    }

    /// Return a string representation of this class.
    ///
    /// @return java.lang.String
    @Override
    public String toString() {
        return "SailConfig{" +
                "hostName='" + this.hostName + '\'' +
                ", port=" + this.port +
                ", clientName='" + this.clientName + '\'' +
                ", useSSL=" + this.useSSL +
                '}';
    }

    /// The builder class.
    public static class Builder {
        /// The host name.
        private String hostName;

        /// The port.
        private int port;

        /// The client name.
        private String clientName;

        /// True if SSL is in use.
        private boolean useSSL;

        /// The constructor.
        private Builder() {
            super();
        }

        /// Set the host name.
        ///
        /// @param  hostName    java.lang.String
        /// @return             net.jmp.spring.boot.valkey.sail.SailConfig.Builder
        public Builder hostName(final String hostName) {
            this.hostName = hostName;

            return this;
        }

        /// Set the port.
        ///
        /// @param  port    int
        /// @return         net.jmp.spring.boot.valkey.sail.SailConfig.Builder
        public Builder port(final int port) {
            this.port = port;

            return this;
        }

        /// Set the client name.
        ///
        /// @param  clientName    java.lang.String
        /// @return               net.jmp.spring.boot.valkey.sail.SailConfig.Builder
        public Builder clientName(final String clientName) {
            this.clientName = clientName;

            return this;
        }

        /// Set the SSL flag.
        ///
        /// @param  useSSL    boolean
        /// @return           net.jmp.spring.boot.valkey.sail.SailConfig.Builder
        public Builder useSSL(final boolean useSSL) {
            this.useSSL = useSSL;

            return this;
        }

        /// Build and return the configuration.
        ///
        /// @return net.jmp.spring.boot.valkey.sail.SailConfig
        public SailConfig build() {
            return new SailConfig(this);
        }
    }
}
