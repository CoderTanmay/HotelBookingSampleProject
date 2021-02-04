/**
 * @author tanmay naik
 *
 */
package com.m3bi.hotel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.m3bi.hotel.controller", "com.m3bi.hotel.service", "com.m3bi.hotel.repository",
"com.m3bi.hotel.util" })
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
