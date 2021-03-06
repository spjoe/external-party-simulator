/*
 * Copyright 2014 Stefan Domnanovits
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
package com.codebullets.external.party.simulator.pipeline;

import com.codebullets.external.party.simulator.connections.Connection;
import com.codebullets.external.party.simulator.connections.ConnectionContext;
import com.codebullets.external.party.simulator.connections.ConnectionsContainer;
import com.codebullets.external.party.simulator.worker.SimulatorStateContainer;
import com.codebullets.external.party.simulator.worker.WorkerQueue;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Base class for any kind of pipeline event handler.
 */
public abstract class AbstractHandler {
    private static final String ECHO_CONNECTION_NAME = "echo";

    private WorkerQueue workerQueue;
    private SimulatorStateContainer state;
    private ConnectionsContainer connections;

    /**
     * Override this method to apply a filter string that limits the  the handler to be executed.<p/>
     * The filter is applied on the source events connections name. Glob wildcards like '*' or '?'
     * are possible. The default is '*' which means the handler is executed for all connections.
     */
    protected String getConnectionFilter() {
        return "*";
    }

    /**
     * Called by the simulator framework to set the queue.
     */
    protected final void setWorkerQueue(final WorkerQueue queue) {
        workerQueue = queue;
    }

    /**
     * Gets the simulator worker queue. This queue can be used to enqueue additional
     * {@link MessageReceivedEvent} for further processing.
     */
    public final WorkerQueue getWorkerQueue() {
        return workerQueue;
    }

    /**
     * Gets a container holding all available connections.
     */
    public final ConnectionsContainer getConnections() {
        return connections;
    }

    /**
     * Called by the simulator framework to set list of available connections.
     */
    public final void setConnections(final ConnectionsContainer connectionContainer) {
        this.connections = connectionContainer;
    }

    /**
     * Gets the specific connection based on the context.
     */
    protected final Connection getConnection(final ConnectionContext context) {
        String connectionName = context.getConnectionName();
        return connections.get(connectionName);
    }

    /**
     * Gets a container where message handlers can store and retrieve any kind of data.
     */
    public final SimulatorStateContainer getState() {
        return state;
    }

    /**
     * Called by the simulator framework to set the state.
     */
    protected final void setState(final SimulatorStateContainer simulatorState) {
        this.state = simulatorState;
    }

    /**
     * Sends a text message over the connection matching the context.
     */
    protected final void sendTo(final ConnectionContext context, final String text) {
        Connection connection = getConnection(context);
        connection.send(context, text);
    }

    /**
     * Sends a binary message over the connection matching the context.
     */
    protected final void sendTo(final ConnectionContext context, final byte[] buffer) {
        Connection connection = getConnection(context);
        connection.send(context, buffer);
    }

    /**
     * Sends a Java object message over the connection matching the context.
     */
    protected final void sendTo(final ConnectionContext context, final Object object) {
        Connection connection = getConnection(context);
        connection.send(context, object);
    }

    /**
     * Sends a text message over the connection matching the connection name.<br/>
     * This most likely only works with outgoing connections.
     */
    protected final void sendTo(final String connectionName, final String text) {
        Connection connection = getConnections().get(connectionName);
        checkArgument(connection != null, "Unknown connection with name '" + connectionName + "'.");

        connection.send(text);
    }

    /**
     * Sends a binary message over the connection matching the connection name.<br/>
     * This most likely only works with outgoing connections.
     */
    protected final void sendTo(final String connectionName, final byte[] buffer) {
        Connection connection = getConnections().get(connectionName);
        checkArgument(connection != null, "Unknown connection with name '" + connectionName + "'.");

        connection.send(buffer);
    }

    /**
     * Sends a Java object message over the connection matching the connection name.<br/>
     * This most likely only works with outgoing connections.
     */
    protected final void sendTo(final String connectionName, final Object object) {
        Connection connection = getConnections().get(connectionName);
        checkArgument(connection != null, "Unknown connection with name '" + connectionName + "'.");

        connection.send(object);
    }

    /**
     * Sends a text to all clients connected to the connection named echo.
     */
    protected final void echo(final String text) {
        Connection echo = getEcho();
        if (echo != null) {
            echo.send(text);
        }
    }

    /**
     * Sends a text to all clients connected to the connection named echo. The text
     * is formatted with the arguments provided by {@code args}. The text is formatted
     * with {@code String.format} behavior.<p/>
     * <strong>Example:</strong><p/>
     * {@code echo("Received the message %s times.", numReceived);}
     */
    protected final void echo(final String text, final Object... args) {
        String formattedText = String.format(text, args);
        echo(formattedText);
    }

    private Connection getEcho() {
        return getConnections().get(ECHO_CONNECTION_NAME);
    }
}