package com.gdu.drawauction.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface DrawService {
	public Map<String, Object> getDrawList(HttpServletRequest request);

}
