package org.easley.demo.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JdbcUtils
 *
 * @author Easley
 * @date 2021/2/21
 * @since 1.0
 */
@Slf4j
public class JdbcUtils {

    /**
     * Connect to database.
     *
     * @param driver   {@link Driver} driver implement class name.
     * @param url      db connection url
     * @param name     db authentication name
     * @param password db authentication password
     * @return {@link Connection}
     */
    public static Connection connect(String driver, String url, String name, String password) {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return connection;
    }

    /**
     * Disconnect from a database.
     *
     * @param connection {@link Connection}
     */
    public static void disconnect(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
    }

    /**
     * Execute command.
     *
     * @param connection {@link Connection}
     * @param cmd        Command
     * @return true/false
     */
    public static boolean execute(Connection connection, String cmd) {
        if (connection == null || cmd == null || cmd.isEmpty()) {
            return false;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(cmd);
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return false;
    }

    /**
     * Execute query sql.
     *
     * @param connection {@link Connection}
     * @param sql        Query sql statement : "select * from table where id = ? and name = ?"
     * @param params     Query sql parameters : [1, "abc"]
     * @return result rows [{"id":1}, {"id":2}, {"id":3}, {"id":4}]
     */
    public static List<Map<String, Object>> query(Connection connection, String sql, Object... params) {
        return query(connection, Command.build().sql(sql).params(params));
    }

    /**
     * Execute query sql.
     *
     * @param connection {@link Connection}
     * @param sql        Query sql statement : "select * from table where id = ${id} and name = ${name}"
     * @param params     Query sql parameters : {"id":1, "name":"abc"}
     * @return result rows [{"id":1}, {"id":2}, {"id":3}, {"id":4}]
     */
    public static List<Map<String, Object>> query(Connection connection, String sql, Map<String, Object> params) {
        return query(connection, Command.build().sql(generatePreparedSql(sql, params)).params(generatePreparedParams(sql, params)));
    }

    /**
     * Execute query sql.
     *
     * @param connection {@link Connection}
     * @param command    {@link Command}
     * @return result rows [{"id":1}, {"id":2}, {"id":3}, {"id":4}]
     */
    public static List<Map<String, Object>> query(Connection connection, Command command) {
        List<Map<String, Object>> rows = new ArrayList<>();
        query(connection, command, (row, number) -> rows.add(row));
        return rows;
    }

    /**
     * Execute query sql.
     *
     * @param connection         {@link Connection}
     * @param command            {@link Command}
     * @param resultRowProcessor {@link ResultRowProcessor}
     * @return result row count
     */
    public static int query(Connection connection, Command command, ResultRowProcessor... resultRowProcessor) {
        if (connection == null || command == null || command.getSql() == null
                || resultRowProcessor == null || resultRowProcessor.length == 0) {
            return 0;
        }
        AtomicInteger dataCount = new AtomicInteger(0);
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(command.getSql());
            if (command.getParams() != null) {
                for (int i = 0; i < command.getParams().size(); i++) {
                    statement.setObject(i + 1, command.getParams().get(i));
                }
            }
            resultSet = statement.executeQuery();
            List<String> columns = new ArrayList<>();
            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                columns.add(resultSet.getMetaData().getColumnLabel(i + 1));
            }
            while (resultSet.next()) {
                int currentRowNum = dataCount.incrementAndGet();
                Map<String, Object> row = new LinkedHashMap<>();
                for (String column : columns) {
                    row.put(column, resultSet.getObject(column));
                }
                for (ResultRowProcessor processor : resultRowProcessor) {
                    processor.process(row, currentRowNum);
                }
            }
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return dataCount.get();
    }

    /**
     * Execute update sql statement.
     *
     * @param connection {@link Connection}
     * @param sql        Update sql statement : "delete from table_user where id = ?"
     * @param params     Update sql parameters : [1]
     * @return affected rows
     */
    public static int update(Connection connection, String sql, Object... params) {
        return update(connection, Command.build().sql(sql).params(params));
    }

    /**
     * Execute update sql statement.
     *
     * @param connection {@link Connection}
     * @param sql        Update sql statement : "delete from table_user where id = ${id}"
     * @param params     Update sql parameters : {"id":1}
     * @return affected rows
     */
    public static int update(Connection connection, String sql, Map<String, Object> params) {
        return update(connection, Command.build().sql(generatePreparedSql(sql, params)).params(generatePreparedParams(sql, params)));
    }

    /**
     * Execute update sql statement.
     *
     * @param connection {@link Connection}
     * @param command    {@link Command}
     * @return affected rows
     */
    public static int update(Connection connection, Command command) {
        if (connection == null || command == null || command.getSql() == null) {
            return 0;
        }
        int result = 0;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(command.getSql());
            if (command.getParams() != null) {
                for (int i = 0; i < command.getParams().size(); i++) {
                    statement.setObject(i + 1, command.getParams().get(i));
                }
            }
            result = statement.executeUpdate();
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return result;
    }

    /**
     * Execute update sql transaction.
     *
     * @param connection {@link Connection}
     * @param commands   sql commands {@link Command}
     * @return true/false
     */
    public static boolean transaction(Connection connection, Command... commands) {
        if (connection == null || commands == null || commands.length == 0) {
            return false;
        }
        List<PreparedStatement> statements = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            for (Command command : commands) {
                String sql = command.getSql();
                List<Object> params = command.getParams();
                PreparedStatement statement = connection.prepareStatement(sql);
                if (params != null) {
                    for (int i = 0; i < params.size(); i++) {
                        statement.setObject(i + 1, params.get(i));
                    }
                }
                statements.add(statement);
                if (statement.executeUpdate() <= 0) {
                    throw new RuntimeException("Rollback.");
                }
            }
            connection.commit();
            return true;
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            try {
                connection.rollback();
            } catch (Exception ex) {
                log.warn(ex.getMessage(), ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                log.warn(e.getMessage(), e);
            }
            for (PreparedStatement statement : statements) {
                try {
                    statement.close();
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return false;
    }

    /**
     * Build an empty sql command.
     *
     * @return {@link Command}
     */
    public static Command buildCommand() {
        return Command.build();
    }

    /**
     * Build a sql command with sql statement and parameters.
     *
     * @param sql    sql statement
     * @param params sql parameters
     * @return {@link Command}
     */
    public static Command buildCommand(String sql, Object... params) {
        return Command.build().sql(sql).params(params);
    }

    /**
     * SQL command.
     * <pre>
     *     sql statement
     *     sql parameters
     * </pre>
     */
    @Getter
    @Setter
    public static class Command {
        /**
         * Sql command.
         * <pre>
         *     "select * from table_user where id = ? or name in (?, ?)"
         * </pre>
         */
        private String sql;
        /**
         * Sql ? prepared statement parameters, orders required.
         * <pre>
         *     SQL: "select * from table_user where id = ? or name in (?, ?)"
         *     params: [100, "abc", "def"]
         * </pre>
         */
        private List<Object> params;

        public static Command build() {
            return new Command();
        }

        public Command sql(String sql) {
            this.sql = sql;
            return this;
        }

        public Command params(Object... params) {
            this.params = Arrays.asList(params);
            return this;
        }

        @Override
        public String toString() {
            return "Command{" +
                    "sql='" + sql + '\'' +
                    ", params=" + params +
                    '}';
        }
    }

    public interface ResultRowProcessor {
        /**
         * Process row.
         *
         * @param row       current row data
         * @param rowNumber current row number
         */
        void process(Map<String, Object> row, int rowNumber);
    }

    private static final Pattern PARAM_PATTERN = Pattern.compile("\\$\\{[^\\{\\}]*\\}");

    private static String generatePreparedSql(String sql, Map<String, Object> params) {
        String preparedSql = sql;
        Matcher settingMatcher = PARAM_PATTERN.matcher(preparedSql);
        while (settingMatcher.find()) {
            String group = settingMatcher.group();
            preparedSql = preparedSql.replace(group, "?");
        }
        return preparedSql;
    }

    private static Object[] generatePreparedParams(String sql, Map<String, Object> params) {
        List<Object> objects = new ArrayList<>();
        Matcher settingMatcher = PARAM_PATTERN.matcher(sql);
        while (settingMatcher.find()) {
            String group = settingMatcher.group();
            String paramKey = group.substring(2, group.length() - 1);
            objects.add(params.getOrDefault(paramKey, null));
        }
        return objects.toArray();
    }
}
