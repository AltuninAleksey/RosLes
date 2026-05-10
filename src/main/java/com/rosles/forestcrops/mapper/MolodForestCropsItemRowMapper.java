package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.sample.response.MolodForestCropsItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MolodForestCropsItemRowMapper implements RowMapper<MolodForestCropsItem> {
    @Override
    public MolodForestCropsItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        MolodForestCropsItem molodForestCropsItem = new MolodForestCropsItem();

        molodForestCropsItem.setId(rs.getInt("id"));
        molodForestCropsItem.setTo0_5(ResultSetHelper.getNullableObject(rs, "to0_5", Integer.class, 0));
        molodForestCropsItem.setFrom0_6To1_5(ResultSetHelper.getNullableObject(rs, "from0_6To1_5", Integer.class, 0));
        molodForestCropsItem.setFrom1_5(ResultSetHelper.getNullableObject(rs, "from1_5", Integer.class, 0));
        molodForestCropsItem.setIdBreed(ResultSetHelper.getNullableObject(rs, "id_breed_id", Long.class, 0l));

        return molodForestCropsItem;
    }
}
