import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:chat_history.db";

    public DatabaseManager() {
        // Initialize the database table when the object is created
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                String sql = "CREATE TABLE IF NOT EXISTS messages (" +
                             "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                             "channel TEXT," +
                             "sender TEXT," +
                             "content TEXT," +
                             "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                             ");";
                Statement stmt = conn.createStatement();
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Database init error: " + e.getMessage());
        }
    }
    
    public void saveMessage(String channel, String message) {
        // Simple logic to parse "Username: Message" format
        String[] parts = message.split(": ", 2);
        String sender = (parts.length > 1) ? parts[0] : "System";
        String content = (parts.length > 1) ? parts[1] : message;

        String sql = "INSERT INTO messages(channel, sender, content) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, channel);
            pstmt.setString(2, sender);
            pstmt.setString(3, content);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Save error: " + e.getMessage());
        }
    }

    public java.util.List<String> getHistory(String channel) {
    java.util.List<String> history = new java.util.ArrayList<>();
    String sql = "SELECT sender, content FROM messages WHERE channel = ? ORDER BY timestamp ASC";
    try (Connection conn = DriverManager.getConnection(URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, channel);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            history.add(rs.getString("sender") + ": " + rs.getString("content"));
        }
    } catch (SQLException e) {
        System.err.println("Load error: " + e.getMessage());
    }
    return history;
}
}
