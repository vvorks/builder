package com.github.vvorks.builder.server.controller;

import com.github.vvorks.builder.server.common.net.annotation.JsonRpcController;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcMethod;
import com.github.vvorks.builder.server.common.net.annotation.JsonRpcParam;

@JsonRpcController("/rpc")
public class RpcController {

//	@Autowired
//	private JsonRpcServer server;

	@JsonRpcMethod("subtract")
	public int test(@JsonRpcParam("x") int x, @JsonRpcParam("y") int y) {
		return x - y;
	}

}
