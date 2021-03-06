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
package com.codebullets.external.party.simulator.pipeline;

import com.codebullets.external.party.simulator.connections.ConnectionContext;
import com.codebullets.external.party.simulator.worker.ConnectionEvent;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkState;

/**
 * Information about a specific message that has been received.
 */
public final class MessageReceivedEvent implements ConnectionEvent {
    private final ContentType contentType;
    private final String messageType;
    private final Object content;
    private final ConnectionContext connectionContext;

    /**
     * Use static builder method to create instance.
     */
    private MessageReceivedEvent(
            final ConnectionContext connectionContext, final Object content, final ContentType contentType,
            @Nullable final String messageType) {
        this.contentType = contentType;
        this.messageType = messageType;
        this.connectionContext = connectionContext;
        this.content = content;
    }

    /**
     * Gets the type of message that has been received.
     */
    public ContentType getContentType() {
        return contentType;
    }

    /**
     * Gets the text content in case message is of type text.
     */
    public String getTextContent() {
        checkState(contentType.equals(ContentType.TEXT), "Message content needs to be of type TEXT.");
        return (String) content;
    }

    /**
     * Gets the binary content in case message is of type binary.
     */
    public byte[] getBinaryContent() {
        return (byte[]) content;
    }

    /**
     * Gets the Java object content.
     */
    public Object getObjectContent() {
        return content;
    }

    /**
     * Gets the message instance type.
     */
    @Nullable
    public String getMessageType() {
        return messageType;
    }

    /**
     * Gets the source connection context.
     */
    public ConnectionContext getConnectionContext() {
        return connectionContext;
    }

    /**
     * Creates a message received event of type text.
     */
    public static MessageReceivedEvent create(final ConnectionContext connectionContext, final String text, @Nullable final String msgType) {
        return new MessageReceivedEvent(connectionContext, text, ContentType.TEXT, msgType);
    }

    /**
     * Creates a message received event of type text.
     */
    public static MessageReceivedEvent create(final ConnectionContext connectionContext, final String text) {
        return MessageReceivedEvent.create(connectionContext, text, null);
    }

    /**
     * Creates a message received event of type binary.
     */
    public static MessageReceivedEvent create(final ConnectionContext connectionContext, final byte[] buffer, @Nullable final String msgType) {
        return new MessageReceivedEvent(connectionContext, buffer, ContentType.BINARY, msgType);
    }

    /**
     * Creates a message received event of type binary.
     */
    public static MessageReceivedEvent create(final ConnectionContext connectionContext, final byte[] buffer) {
        return MessageReceivedEvent.create(connectionContext, buffer, null);
    }

    /**
     * Creates a message received event of type object.
     */
    public static MessageReceivedEvent create(final ConnectionContext connectionContext, final Object obj, @Nullable final String msgType) {
        return new MessageReceivedEvent(connectionContext, obj, ContentType.OBJECT, msgType);
    }

    /**
     * Creates a message received event of type object.
     */
    public static MessageReceivedEvent create(final ConnectionContext connectionContext, final Object obj) {
        return MessageReceivedEvent.create(connectionContext, obj, null);
    }
}