package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.sample.response.PlantsForestCropsItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantsForestCropsItemRowMapper implements RowMapper<PlantsForestCropsItem> {
    @Override
    public PlantsForestCropsItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        PlantsForestCropsItem plantsForestCropsItem = new PlantsForestCropsItem();

        plantsForestCropsItem.setId(rs.getInt("id"));
        plantsForestCropsItem.setDiameter(ResultSetHelper.getNullableObject(rs, "diameter", Double.class, 0.0));
        plantsForestCropsItem.setHeight(ResultSetHelper.getNullableObject(rs, "height", Double.class, 0.0));
        plantsForestCropsItem.setIdBreed(ResultSetHelper.getNullableObject(rs, "id_breed_id", Long.class, 0l));

        return plantsForestCropsItem;
    }
}
