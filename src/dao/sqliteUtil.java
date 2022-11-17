package dao;

import java.sql.*;

public class sqliteUtil {
        private  static Connection con;
        //建立数据库连接
        public static Connection openSQLiteDB()
        {
            String url = "jdbc:sqlite:src/data/dblibrary.db";
            try
            {
                con = DriverManager.getConnection(url);
                return con;
            }
            catch (SQLException e)
            {
                System.out.println(" (openDB) open db faied: " + e.getMessage());
                return null;
            }
        }
        //关闭连接
        public static void closeSQLiteDB() {
            //Connection con=openSQLiteDB();
            if(con == null) {
                return;
            }
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println( " (openSQLiteDB) close db faied: " + e.getMessage());
            }
        }
        /*
           根据SQL语句更新数据库表  update、delete操作
         */
        public static boolean exeSQLupdate(String sql) {
            Connection con=openSQLiteDB();
            if(con == null || sql == null) {
                return false;
            }
            Statement stmt = null;
            boolean ret = false;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
                ret = true;
                System.out.println(ret);
            } catch (SQLException e) {
                System.out.println("444");
                e.printStackTrace();
            } finally {
                if(stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ret;
        }
        /*
        向数据库表中插入数据
        参数：表名，字段名数组，插入的值组成的数组
        返回值为类型为：ResultSet
         */
        public static boolean insert2(String table, String[] params, Object[] values) {
            Connection con=openSQLiteDB();
            boolean ret = false;
            int paramsLength = params.length;
            if(paramsLength < values.length) {
                System.out.println( " (insert2) values is little params.");
                return ret;
            }
            StringBuilder tmp = new StringBuilder("insert into " + table + "(");
            StringBuffer tmp2 = new StringBuffer(" values(");
            int lastParam = paramsLength - 1;
            for(int i = 0; i < paramsLength; i++) {
                if(i == lastParam) {
                    tmp.append(params[i] + ")");
                    tmp2.append("?);");
                } else {
                    tmp.append(params[i] + ",");
                    tmp2.append("?,");
                }
            }
            String sql = tmp.append(tmp2).toString();
            System.out.println( " (insert2) sql : " + sql);
            try {
                PreparedStatement pstmt = con.prepareStatement(sql);
                for(int j = 1; j <= values.length; j++) {
                    Object value = values[j - 1];
                    if(value instanceof String) {
                        pstmt.setString(j, (String)value);
                    } else if(value instanceof Integer) {
                        pstmt.setInt(j, (Integer)value);
                    } else if(value instanceof Boolean) {
                        pstmt.setBoolean(j, (Boolean)value);
                    } else if(value instanceof Double) {
                        pstmt.setDouble(j, (Double)value);
                    } else if(value instanceof Long) {
                        pstmt.setLong(j, (Long)value);
                    } else if(value instanceof Blob) {
                        pstmt.setBlob(j, (Blob)value);
                    } else {
                        pstmt.setObject(j, value);
                    }
                }
                ((PreparedStatement) pstmt).executeUpdate();
                ret = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ret;
        }
        /*
        根据SQL语句查询数据
        返回值为类型为：ResultSet
         */
        public static ResultSet selecte( String sql )  {
            Connection con=openSQLiteDB();
            if(con == null || sql == null) {
                return null;
            }
            Statement stmt = null;
            ResultSet ret = null;
            try {
                stmt = con.createStatement();
                ret = stmt.executeQuery(sql);
                // con.close();
            }
            catch (SQLException e) {
                //  e.printStackTrace();
                System.out.println(" select happen error: " + e.getMessage());
            }
            return ret;
        }
        /*
        删除符合条件的，删除数据库表中的数据
        table：数据库表名  key：字段名  keyValue：字段值
         */
        public static boolean delete(String table, String key, String keyValue) {
            Connection con=openSQLiteDB();
            Statement stmt = null;
            String sql = "delete from " + table + " where " + key + "='" + keyValue + "';";
            boolean ret = false;
            System.out.println(" (delete) sql:" + sql);
            try {
                stmt = con.createStatement();
                stmt.executeUpdate(sql);
                ret = true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if(stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ret;
        }
        //数据库回滚
        private static void rollback(Connection con) {
            //Connection con=openSQLiteDB();
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
