package com.cnten.deviceresolve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.cnten.deviceresolve.platform.**.dao")
@SpringBootApplication
public class DeviceResolveApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceResolveApplication.class, args);
	}

}
