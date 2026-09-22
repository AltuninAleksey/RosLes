package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.shared.response.Item;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemListRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {

        Item item = new Item();

        item.setId(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l));
        item.setName(ResultSetHelper.getNullableObject(rs, "name", String.class, ""));

        return item;
    }
}
