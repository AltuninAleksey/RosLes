package com.rosles.forestcrops.mapper.mobile;

import com.rosles.forestcrops.dto.mobile.sample.response.SampleItemMobile;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SampleItemMobileRowMapper implements RowMapper<SampleItemMobile> {
    @Override
    public SampleItemMobile mapRow(ResultSet rs, int rowNum) throws SQLException {

        SampleItemMobile sampleItemMobile = new SampleItemMobile();

        sampleItemMobile.setId(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l));
        sampleItemMobile.setNumber(ResultSetHelper.getNullableObject(rs, "number", String.class, ""));
        sampleItemMobile.setLength(ResultSetHelper.getNullableObject(rs, "length", Double.class, 0.0));
        sampleItemMobile.setWidth(ResultSetHelper.getNullableObject(rs, "width", Double.class, 0.0));
        sampleItemMobile.setUuid(ResultSetHelper.getNullableObject(rs, "uuid", UUID.class, UUID.randomUUID()));
        sampleItemMobile.setUuidListRegion(ResultSetHelper.getNullableObject(rs, "parent_uuid", UUID.class, UUID.randomUUID()));

        return sampleItemMobile;
    }

}
