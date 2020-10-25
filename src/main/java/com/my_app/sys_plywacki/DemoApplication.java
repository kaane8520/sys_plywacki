package com.my_app.sys_plywacki;

import com.my_app.sys_plywacki.model.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages= {"com.my_app"})

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		final HyperSqlDbServer dbServer = context.getBean(HyperSqlDbServer.class);
		dbServer.displayInfo();

		try (final Scanner sc = new Scanner(System.in)) {
			do {
				System.out.println("Shutdown HSQLDB?[Y/N]: ");
			} while (sc.hasNext() && (!sc.next().equalsIgnoreCase("y")));
		}

		// =============================================================

		// SHUTDOWN DATABASE ...
		final DataSource dataSource = context.getBean(DataSource.class);
		final JdbcTemplate template = new JdbcTemplate(dataSource);
		template.execute("SHUTDOWN");

		context.close();
	}

}