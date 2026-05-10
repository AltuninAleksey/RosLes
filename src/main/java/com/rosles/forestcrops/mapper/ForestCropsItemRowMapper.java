package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.sample.response.ForestCropsItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ForestCropsItemRowMapper implements RowMapper<ForestCropsItem> {
    @Override
    public ForestCropsItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        ForestCropsItem forestCropsItem = new ForestCropsItem();

        forestCropsItem.setId(rs.getInt("id"));
        forestCropsItem.setCountDead(ResultSetHelper.getNullableObject(rs, "count_dead", Integer.class, 0));
        forestCropsItem.setCountLiving(ResultSetHelper.getNullableObject(rs, "count_living", Integer.class, 0));
        forestCropsItem.setIdBreed(ResultSetHelper.getNullableObject(rs, "id_breed_id", Long.class, 0l));

        return forestCropsItem;
    }
}
