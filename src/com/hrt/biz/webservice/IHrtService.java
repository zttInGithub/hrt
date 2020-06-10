package com.hrt.biz.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IHrtService {

	public String echo(String id);
	
    public String getUserByName(@WebParam(name = "name") String name);

    public void setUser(User user);
}
