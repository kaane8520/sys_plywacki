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
import com.my_app.sys_plywacki.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import java.util.List;
import java.util.Arrays;

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
		/*HsqlProperties p = new HsqlProperties();
		p.setProperty("server.database.0","file:./hsqldb/mydb");
		p.setProperty("server.dbname.0","mydb");
		// set up the rest of properties

		// alternative to the above is
		Server server = new Server();
		server.setProperties(p);
		server.setLogWriter(null); // can use custom writer
		server.setErrWriter(null); // can use custom writer
		server.start();*/

	}
	/*
    @Bean
    CommandLineRunner init (RoleRepository roleRepo){
        return args -> {
        	if(roleRepo.findAll() == null) { //jesli nie ma nic w tabeli role
        	//roleRepo.deleteAll();
	            List<String> role_names = Arrays.asList("zawodnik", "trener","sedzia","organizator");
	            Long i = 1L;
	            for(String rn: role_names) {
	            	Role r = new Role();
	            	r.setName(rn);
	            	System.out.println("Role name: "+rn);
	            	r.setId(i);
	            	System.out.println("Role id: "+i);
	            	roleRepo.save(r);
	            	i=i+1;
	            }
        	}
            //role_names.forEach(role_names -> roleRepo.save(new Role(role_names)));
        };
    }
    */
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