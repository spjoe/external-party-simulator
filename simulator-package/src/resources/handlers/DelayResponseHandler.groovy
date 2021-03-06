import com.codebullets.external.party.simulator.pipeline.AbstractMessageHandler
import com.codebullets.external.party.simulator.pipeline.ContentType
import com.codebullets.external.party.simulator.pipeline.MessageReceivedEvent

import java.util.concurrent.TimeUnit

/**
 * This handler listens for incoming text messages containing purely
 * of digits.<br/>
 * Once such a message is detected it schedules another message item
 * which will be executed after the number of seconds specified
 */
class DelayResponseHandler extends AbstractMessageHandler {

    @Override
    ContentType getContentType() {
        return ContentType.TEXT
    }

    @Override
    String getMessageType() {
        return null
    }

    /**
     * Overriding this method is optional. It ensures this handler is
     * only executed in case the the connection being established is
     * named 'control'. By default the handler would be executed on all
     * connections.
     */
    @Override
    protected String getConnectionFilter() {
        return "control"
    }

    @Override
    void handle(final MessageReceivedEvent receivedEvent) {
        def text = receivedEvent.textContent

        // if incoming text contains only digits
        if (text ==~ /\d+/) {
            def delayInSeconds = Integer.parseInt(text);
            def newWorkItem = MessageReceivedEvent.create(receivedEvent.connectionContext, delayInSeconds, "delayedMessage")
            workerQueue.addDelayed(newWorkItem, delayInSeconds, TimeUnit.SECONDS)
        }
    }
}