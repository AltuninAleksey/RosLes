package com.rosles.forestcrops.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetHelper {
    public static <T> T getNullableObject(ResultSet rs, String columnName, Class<T> type, T defaultValue) {
        try {

            if (!hasColumn(rs, columnName)) {
                return defaultValue;
            }

            T value = rs.getObject(columnName, type);
            return value != null ? value : defaultValue;

        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean hasColumn(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
