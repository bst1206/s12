package com.stackbaek.rnsarith.eventListener;

import java.util.EventListener;

import com.stackbaek.rnsarith.event.LogEvent;

public interface LogEventListener extends EventListener{
	public void handleLogEvent(LogEvent le);
}
