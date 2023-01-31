// cout DAO Class DBの操作を行う
package video;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class videoDao {
    private final String SQL_CREATE;
    private final String URL;
    public static final String SQL_SELECT
            = "SELECT * FROM video;";

    public videoDao(String sqlCreate, String url) {
        this.SQL_CREATE = sqlCreate;
        this.URL = url;
    }

    public void insertStoredProc(String name, long size, long date, double duration) {
        final String SQL_insert = "INSERT INTO video(name,size,date,duration) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_CREATE)) {
            preparedStatement.execute();
            System.out.println("SQL_CREATE");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERR_SQL_CREATE");
        }
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(SQL_insert)) {
                ps.setString(1, name);
                ps.setLong(2, size);
                ps.setLong(3, date);
                ps.setDouble(4, duration);
                ps.executeUpdate();
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.out.println("rollback");
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("処理が完了しました");
        }
    }

    public Map<String, Object> isunique(String forwardMatch, long size, long date) {
        Map<String, Object> values = new HashMap<String, Object>();
        final String subQuery = "SELECT EXISTS(SELECT * FROM video WHERE (name) LIKE (?) ORDER BY name DESC)";
        final String select_sql_id_1 = "SELECT * FROM video WHERE (name) LIKE (?);";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(subQuery);
             PreparedStatement ps1 = conn.prepareStatement(select_sql_id_1)
        ) {
            ps.setString(1, forwardMatch);
            ps1.setString(1, forwardMatch);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("DROP:" + forwardMatch + "======" + size + "======" + date);
            if (resultSet.next()) {
                boolean found = resultSet.getBoolean(1);
                if (found) {
                    System.out.println("テーブルに存在します。");

                    values.put("isnull", true);
                } else {
                    System.err.println("テーブルにありません。");

                    values.put("isnull", false);
                }
            }
            try (ResultSet resultSet1 = ps1.executeQuery()) {
                while (resultSet1.next()) {
                    System.out.println("resultSet");
                    System.out.println(
                            "Name:" + resultSet1.getString("name")
                                    + "size:" + resultSet1.getLong("size")
                                    + "date:" + resultSet1.getLong("date")
                    );
                    values.put("name", resultSet1.getString("name"));
                    values.put("size", resultSet1.getLong("size"));
                    values.put("date", resultSet1.getLong("date"));
                    values.put("duration", resultSet1.getDouble("duration"));
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERR_select_sql_id");
        }
        return values;
    }
}
