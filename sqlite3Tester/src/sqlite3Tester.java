
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.Statement;
import java.sql.*;

public class sqlite3Tester {
    public static void main(String[] args) throws Exception {
        Connection connection = null;
        try {
            // SQLite JDBC 드라이버 로드
            Class.forName("org.sqlite.JDBC");
            
            // SQLite 데이터베이스 파일 경로
            String dbFile = "./db/kcnet_test.db3";
            
            // SQLite 데이터베이스 연결
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);

            querySql(connection);
            updateSql(connection);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static void querySql(Connection connection) throws SQLException {
        // 데이터 조회 SQL
        String selectSQL = "SELECT * FROM TB_ECL113L " +
                "WHERE LBRY_PRCS_TPCD = '4' " +
                "AND PDF_TRFM_STCD = 'W'";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);

        // 결과 출력
        while (resultSet.next()) {
            System.out.println(resultSet.getRow());
        }
    }

    private static void updateSql(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String updateSQL = "UPDATE TB_ECL113L " + 
            "SET " +  
                "PDF_TRFM_STCD = 'S'," + 
                "PDF_TRFM_FILE_SIZE = '82 KB',"  + 
                "PDF_TRFM_ERR_NO = 'R',"  + 
                "PDF_TRFM_WKNG_ID = 'RDOC000092913424-d43fc618-c355-44db-b7e3-c2a16513d166',"  + 
                "MLT_TRFM_SRVR_PT_VAL = 'B',"  + 
                "MLT_TRFM_SRVR_IP = '10.101.102.104',"  + 
                "TRFM_REQT_DTL_DTTM = '23/12/01 01:13:06.360658000',"  + 
                "TRFM_STRT_DTL_DTTM = '23/12/01 01:13:10.000000000',"  + 
                "TRFM_CMPL_DTL_DTTM = '23/12/01 01:13:21.000000000',"  + 
                "TRFM_REREQT_NCNT = '0',"  + 
                "PDF_TRFM_RQRD_HR_VAL = '10.453s',"  + 
                "PDF_PGE_AQTY = '1' "  + 
            "WHERE LBRY_DOC_MT_NO = 'DOC000092913481'";
        int rowsUpdated = statement.executeUpdate(updateSQL);
        // 업데이트된 행 수 출력
        System.out.println(rowsUpdated + "개의 행이 업데이트되었습니다.");
    }

    private void query_updateSql(Connection connection) throws SQLException {
        // 데이터 조회 SQL
        String selectSQL =  "SELECT * FROM TB_ECL113L " +
        "WHERE LBRY_PRCS_TPCD = '4' " +
        "AND PDF_TRFM_STCD = 'W'";
                            
        // 데이터 조회 및 업데이트 가능한 ResultSet 생성
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery(selectSQL);

        // 결과 출력
        while (resultSet.next()) {
            if (resultSet.getString("LBRY_DOC_MT_NO") == "DOC000092913481") {
                // 원하는 열의 값을 업데이트
                resultSet.updateString("PDF_TRFM_STCD", "S");
                resultSet.updateString("PDF_TRFM_FILE_SIZE", "82 KB");
                resultSet.updateString("PDF_TRFM_ERR_NO", "R");
                resultSet.updateString("PDF_TRFM_WKNG_ID", "RDOC000092913424-d43fc618-c355-44db-b7e3-c2a16513d166");
                resultSet.updateString("MLT_TRFM_SRVR_PT_VAL", "B");
                resultSet.updateString("MLT_TRFM_SRVR_IP", "10.101.102.104");
                resultSet.updateString("TRFM_REQT_DTL_DTTM", "23/12/01 01:13:06.360658000");
                resultSet.updateString("TRFM_STRT_DTL_DTTM", "23/12/01 01:13:10.000000000");
                resultSet.updateString("TRFM_CMPL_DTL_DTTM", "23/12/01 01:13:21.000000000");
                resultSet.updateString("TRFM_REREQT_NCNT", "0");
                resultSet.updateString("PDF_TRFM_RQRD_HR_VAL", "10.453s");
                resultSet.updateString("PDF_PGE_AQTY", "1");
                // 업데이트 반영
                resultSet.updateRow();
                System.out.println("데이터 업데이트 완료.");
            }
        }
    }
}

    
    
    
        
    

