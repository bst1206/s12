package com.stackbaek.rnsarith.eventListener;

import java.util.EventListener;

import com.stackbaek.rnsarith.event.RNSArithEvent;

public interface RNSArithEventListener extends EventListener{
	public void handleRNSARithEvent(RNSArithEvent rae);
}
