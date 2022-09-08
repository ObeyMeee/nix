package ua.com.andromeda.actions;

import org.flywaydb.core.Flyway;

public class FlywayCommand implements Command {
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1337";
    private static final String URL = "jdbc:postgresql://localhost:5432/test_hibernate";

    @Override
    public void execute() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USERNAME, PASSWORD)
                .baselineOnMigrate(true)
                .locations("db/migration")
                .load();
        flyway.migrate();
    }
}
