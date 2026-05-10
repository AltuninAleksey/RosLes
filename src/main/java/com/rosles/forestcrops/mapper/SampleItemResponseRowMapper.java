package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.sample.response.SampleItemResponse;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SampleItemResponseRowMapper implements RowMapper<SampleItemResponse> {
    @Override
    public SampleItemResponse mapRow(ResultSet rs, int rowNum) throws SQLException {

        SampleItemResponse sampleItemResponse = new SampleItemResponse();

        sampleItemResponse.setId(rs.getInt("id"));
        sampleItemResponse.setNumber(ResultSetHelper.getNullableObject(rs, "number", String.class, ""));
        sampleItemResponse.setIdListRegion(rs.getInt("id_list_region"));
        sampleItemResponse.setLength(ResultSetHelper.getNullableObject(rs, "length", Double.class, 0.0));
        sampleItemResponse.setWidth(ResultSetHelper.getNullableObject(rs, "width", Double.class, 0.0));

        sampleItemResponse.setSquare(sampleItemResponse.getLength()*sampleItemResponse.getWidth());

        return sampleItemResponse;
    }
}
