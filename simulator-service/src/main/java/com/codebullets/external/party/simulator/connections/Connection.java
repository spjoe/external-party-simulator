/*
 * Copyright 2013 Stefan Domnanovits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codebullets.external.party.simulator.connections;

/**
 * Represents a connection, can either be inbound or outbound.<p/>
 * Implementations of this interface are expected to have a constructor with with an
 * empty argument list.
 */
public interface Connection {
    /**
     * Called when it is time for the connection to be established.
     */
    void start(ConnectionConfig config);

    /**
     * Called by the simulator service. The methods on the injected monitor
     * need to be called to trigger actions inside the service.
     */
    void setMonitor(ConnectionMonitor monitor);

    /**
     * Sends a single text string over the connection.
     */
    void send(String text);

    /**
     * Sends a single text string over the connection. This
     * message is sent as part of a specific context. For example sending
     * a message back only a single client attached to single connection.
     */
    void send(ConnectionContext context, String text);

    /**
     * Sends a buffer of bytes over the connection.
     */
    void send(byte[] buffer);

    /**
     * Sends a buffer of bytes over the connection. This
     * message is sent as part of a specific context. For example sending
     * a message back only a single client attached to single connection.
     */
    void send(ConnectionContext context, byte[] buffer);

    /**
     * Sends any kind of object over the connection.
     */
    void send(Object object);

    /**
     * Sends any kind of object over the connection. This
     * message is sent as part of a specific context. For example sending
     * a message back only a single client attached to single connection.
     */
    void send(ConnectionContext context, Object object);
}