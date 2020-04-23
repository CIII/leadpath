package com.pony.email.sendgrid;

import com.pony.email.Message;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * PonyLeads 2012.
 * User: martin
 * Date: 12/6/12
 * Time: 3:56 PM
 */
public abstract class SendGridEvent {
	private static final Log LOG = LogFactory.getLog(SendGridEvent.class);
	
    private final String type;

    protected static final Map<String, Class> EVENT_HANDLERS = new HashMap<String, Class>();
    protected static final Map<String, Integer> MESSAGE_MAP = new HashMap<String, Integer>();

    static {
        EVENT_HANDLERS.put("processed", ProcessedEvent.class);
        EVENT_HANDLERS.put("deferred", DeferredEvent.class);
        EVENT_HANDLERS.put("delivered", DeliveredEvent.class);
        EVENT_HANDLERS.put("open", OpenEvent.class);
        EVENT_HANDLERS.put("click", ClickEvent.class);
        EVENT_HANDLERS.put("bounce", BounceEvent.class);
        EVENT_HANDLERS.put("dropped", DroppedEvent.class);
        EVENT_HANDLERS.put("spamreport", SpamreportEvent.class);
        EVENT_HANDLERS.put("unsubscribe", UnsubscribeEvent.class);

        MESSAGE_MAP.put("processed", Message.PROCESSED);
        MESSAGE_MAP.put("deferred", Message.DEFERRED);
        MESSAGE_MAP.put("delivered", Message.DELIVERED);
        MESSAGE_MAP.put("dropped", Message.DROPPED);
    }

    SendGridEvent(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    abstract void handle(HashMap<String, String> hash) throws SendGridException;

    abstract void handle(HttpServletRequest request) throws SendGridException;

    public static SendGridEvent get(String type) {
        try {
            Class clazz = EVENT_HANDLERS.get(type);
            if (clazz == null) {
                throw new IllegalArgumentException("no handler for type=" + type);
            }

            return (SendGridEvent) clazz.newInstance();
        }
        catch (InstantiationException e) {
            LOG.error(e);
        }
        catch (IllegalAccessException e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SendGridEvent{" +
                "type='" + type + '\'' +
                '}';
    }
}
