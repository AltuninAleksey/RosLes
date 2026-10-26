package com.rosles.forestcrops.mapper.mobile;

import com.rosles.forestcrops.dto.mobile.forestcropsplants.response.ForestCropsPlantsItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ForestCropsPlantsItemRowMapper implements RowMapper<ForestCropsPlantsItem> {
    @Override
    public ForestCropsPlantsItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        ForestCropsPlantsItem item = new ForestCropsPlantsItem();

        item.setId(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l));
        item.setNumber(ResultSetHelper.getNullableObject(rs, "number", Integer.class, 0));
        item.setDiameter(ResultSetHelper.getNullableObject(rs, "diameter", Double.class, 0.0));
        item.setHeight(ResultSetHelper.getNullableObject(rs, "height", Double.class, 0.0));
        item.setIdBreed(ResultSetHelper.getNullableObject(rs, "id_breed_id", Long.class, 0l));
        item.setUuid(ResultSetHelper.getNullableObject(rs, "uuid", UUID.class, UUID.randomUUID()));
        item.setUuidSample(ResultSetHelper.getNullableObject(rs, "parent_uuid", UUID.class, UUID.randomUUID()));

        return item;
    }
}
