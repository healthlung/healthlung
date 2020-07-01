package com.edu.neu.healthlung.util;

import com.edu.neu.healthlung.entity.LungNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.Reader;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@MappedJdbcTypes(JdbcType.VARCHAR) // 数据库中该字段存储的类型
@MappedTypes(List.class)
public class LungNodeListTypeHandler extends BaseTypeHandler<List<LungNode>> {

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<LungNode> lungNodeList, JdbcType jdbcType) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lungNodeList);
        preparedStatement.setString(i, jsonString);
    }

    @SneakyThrows
    @Override
    public List<LungNode> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = new StringReader(resultSet.getString(s));
        List<LungNode> lungNodes =  objectMapper.readValue(reader, List.class);
        return lungNodes;
    }

    @SneakyThrows
    @Override
    public List<LungNode> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = new StringReader(resultSet.getString(i));
        List<LungNode> lungNodes =  objectMapper.readValue(reader, List.class);
        return lungNodes;
    }

    @SneakyThrows
    @Override
    public List<LungNode> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = new StringReader(callableStatement.getString(i));
        List<LungNode> lungNodes =  objectMapper.readValue(reader, List.class);
        return lungNodes;
    }
}
