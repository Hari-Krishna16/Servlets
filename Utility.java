package de.zeroco.servlets;

import java.util.Collection;
import java.util.Map;

public class Utility {

	public static void main(String[] args) {
		
	}
	
	public static boolean isBlank(Object input) {
		if (input == null) return true;
		else if (input instanceof String) {
			if (((String) input).trim().equals("")) return true;
		} else if (input instanceof Byte) {
			if ((byte) input <= 0) return true;
		} else if (input instanceof Short) {
			if ((short) input <= 0) return true;
		} else if (input instanceof Integer) {
			if ((int) input <= 0) return true;
		} else if (input instanceof Long) {
			if ((long) input <= 0) return true;
		} else if (input instanceof Float) {
			if ((float) input <= 0) return true;
		} else if (input instanceof Double) {
			if ((double) input <= 0.0) return true;
		} else if (input instanceof Boolean) {
			if ((boolean) input == false) return true;
		} else if (input instanceof Character) {
			if ((char) input == ' ') return true;
		} else if (input instanceof Map) {
			if (((Map<?, ?>) input).isEmpty()) return true;
		} else if (input instanceof Object[]) {
			if (((Object[]) input).length == 0) return true;
		} else if (input instanceof Collection) {
			if (((Collection<?>) input).isEmpty()) return true;
		}
		return false;
	}
}
