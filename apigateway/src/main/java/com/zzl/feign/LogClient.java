package com.zzl.feign;

import com.zzl.core.base.log.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("log-center")
public interface LogClient {

	@PostMapping("/logs-anon/internal")
	void save(@RequestBody Log log);
}
