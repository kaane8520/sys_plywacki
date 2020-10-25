package com.my_app.sys_plywacki;

import com.my_app.sys_plywacki.model.Role;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
@ComponentScan(basePackages= {"com.my_app"})

public class DemoApplication {

	public static void main(String[] args) throws IOException, ServerAcl.AclFormatException {
		SpringApplication.run(DemoApplication.class, args);
		HsqlProperties p = new HsqlProperties();
		p.setProperty("server.database.0","file:./hsqldb/mydb");
		p.setProperty("server.dbname.0","mydb");
		// set up the rest of properties

		// alternative to the above is
		Server server = new Server();
		server.setProperties(p);
		server.setLogWriter(null); // can use custom writer
		server.setErrWriter(null); // can use custom writer
		server.start();

	}
//	public DemoApplication() throws SQLException {
//	}

//	public static void main(String[] args) throws IOException, ServerAcl.AclFormatException {
//		SpringApplication.run(DemoApplication.class, args);
//		final ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//
//		final HyperSqlDbServer dbServer = context.getBean(HyperSqlDbServer.class);
////		dbServer.displayInfo();
//
//		try (final Scanner sc = new Scanner(System.in)) {
//			do {
//				System.out.println("Shutdown HSQLDB?[Y/N]: ");
//			} while (sc.hasNext() && (!sc.next().equalsIgnoreCase("y")));
//		}
//
//		// =============================================================
//
//		// SHUTDOWN DATABASE ...
//		final DataSource dataSource = context.getBean(DataSource.class);
//		final JdbcTemplate template = new JdbcTemplate(dataSource);
//		template.execute("SHUTDOWN");
//
//		context.close();
//	}
//	Connection conn = DriverManager.getConnection("jdbc:hsqldb:file:mydb", "sa", "");


}