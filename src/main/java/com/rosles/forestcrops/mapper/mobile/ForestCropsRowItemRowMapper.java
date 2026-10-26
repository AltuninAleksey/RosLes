package com.rosles.forestcrops.mapper.mobile;

import com.rosles.forestcrops.dto.mobile.forestcropsrow.response.ForestCropsRowItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ForestCropsRowItemRowMapper implements RowMapper<ForestCropsRowItem> {
    @Override
    public ForestCropsRowItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        ForestCropsRowItem forestCropsRowItem = new ForestCropsRowItem();

        forestCropsRowItem.setId(ResultSetHelper.getNullableObject(rs, "id",
                Long.class, 0l));
        forestCropsRowItem.setNumber(ResultSetHelper.getNullableObject(rs, "number",
                Integer.class, 0));
        forestCropsRowItem.setIdBreed(ResultSetHelper.getNullableObject(rs, "id_breed_id",
                Long.class, 0l));
        forestCropsRowItem.setCountLiving(ResultSetHelper.getNullableObject(rs, "count_living",
                Integer.class, 0));
        forestCropsRowItem.setCountDead(ResultSetHelper.getNullableObject(rs, "count_dead",
                Integer.class, 0));
        forestCropsRowItem.setUuid(ResultSetHelper.getNullableObject(rs, "uuid",
                UUID.class, UUID.randomUUID()));
        forestCropsRowItem.setUuidSample(ResultSetHelper.getNullableObject(rs, "parent_uuid",
                UUID.class, UUID.randomUUID()));

        return forestCropsRowItem;
    }
}
