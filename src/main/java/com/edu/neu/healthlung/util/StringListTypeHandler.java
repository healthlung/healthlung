package com.edu.neu.healthlung.util;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

@MappedJdbcTypes(JdbcType.VARCHAR) // 数据库中该字段存储的类型
@MappedTypes(List.class)
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {

    Logger logger = LoggerFactory.getLogger(StringListTypeHandler.class);

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String str : strings) {
            stringJoiner.add(str);
        }
//        logger.error(stringJoiner.toString());
        preparedStatement.setString(i, stringJoiner.toString());
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.stringToList(resultSet.getString(s));
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.stringToList(resultSet.getString(i));

    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.stringToList(callableStatement.getString(i));
    }

    private List<String> stringToList(String str) {
        String[] strings = str.split(",");
        ArrayList<String> arrayList = new ArrayList<>(strings.length);
        Collections.addAll(arrayList, strings);
        return arrayList;
    }
}
