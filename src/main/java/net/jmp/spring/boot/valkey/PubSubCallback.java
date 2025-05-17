package net.jmp.spring.boot.valkey;

/*
 * (#)PubSubCallback.java   0.3.0   05/17/2025
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

import glide.api.models.PubSubMessage;

import glide.api.models.configuration.BaseSubscriptionConfiguration;

import java.util.function.BiConsumer;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// The message callback class.
///
/// @version    0.3.0
/// @since      0.3.0
public class PubSubCallback implements BaseSubscriptionConfiguration.MessageCallback {
    /// The logger.
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /// The default constructor.
    public PubSubCallback() {
        super();
    }

    /// The accept method.
    /// The documentation states that object
    /// is a user defined context that will be passed
    /// to the callback for every message received.
    ///
    /// @param  pubSubMessage   glide.api.models.PubSubMessage
    /// @param  object          java.lang.Object
    @Override
    public void accept(final PubSubMessage pubSubMessage, final Object object) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(pubSubMessage.toString(), object));
        }

        this.logger.info("Received message: {}", pubSubMessage.getMessage());
        this.logger.info("Received channel: {}", pubSubMessage.getChannel());

        if (object != null) {
            this.logger.info("Received object: {}", object);
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.trace(exit());
        }
    }

    /// The andThen method.
    ///
    /// @param  after   BiConsumer<? super PubSubMessage, ? super Object>
    /// @return         BiConsumer<PubSubMessage, Object>
    @Override
    public BiConsumer<PubSubMessage, Object> andThen(final BiConsumer<? super PubSubMessage, ? super Object> after) {
        return BaseSubscriptionConfiguration.MessageCallback.super.andThen(after);
    }
}
