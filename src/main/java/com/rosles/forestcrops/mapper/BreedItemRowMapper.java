package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.shared.response.BreedItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BreedItemRowMapper implements RowMapper<BreedItem> {
    @Override
    public BreedItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        BreedItem breedItem = new BreedItem();

        breedItem.setId(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l));
        breedItem.setName(ResultSetHelper.getNullableObject(rs, "name_breed", String.class, ""));
        breedItem.setShortName(ResultSetHelper.getNullableObject(rs, "short_name", String.class, ""));

        return breedItem;
    }
}
