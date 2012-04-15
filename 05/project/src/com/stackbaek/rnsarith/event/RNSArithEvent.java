package com.stackbaek.rnsarith.event;

import java.util.EventObject;

import com.stackbaek.rnsarith.domain.RNSValue;

public class RNSArithEvent extends EventObject{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7698814165029507542L;
	private RNSValue _x;
	private RNSValue _y;
	private String _operation;
	private int[] _mods;
	
	
	public RNSArithEvent(Object source, RNSValue x, RNSValue y, String operation, int[] mods) {
		super(source);
		_x = x;
		_y = y;
		_operation = operation;
		_mods = mods;
	}


	public RNSValue getX() {
		return _x;
	}
	public void set_x(RNSValue x) {
		this._x = x;
	}
	public RNSValue getY() {
		return _y;
	}
	public void setY(RNSValue y) {
		this._y = y;
	}
	public String getOperation() {
		return _operation;
	}
	public void setOperation(String operation) {
		this._operation = operation;
	}
	public int[] getMods() {
		return _mods;
	}
	public void setMods(int[] mods) {
		this._mods = mods;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
