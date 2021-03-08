package com.cpunisher.onmyojibot.eventhandler;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractMessageEventHandler implements IMessageEventHandler {

    private List<String> triggerStrings;

    protected AbstractMessageEventHandler(String... triggerStrings) {
        this.triggerStrings = Arrays.asList(triggerStrings);
    }

    @Override
    public List<String> getTriggerStrings() {
        return triggerStrings;
    }
}
