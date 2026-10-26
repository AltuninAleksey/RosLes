package com.rosles.forestcrops.mapper.mobile;

import com.rosles.forestcrops.dto.shared.response.DachaItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DachaItemRowMapper implements RowMapper<DachaItem> {
    @Override
    public DachaItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        DachaItem dachaItem = new DachaItem();

        dachaItem.setId(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l));
        dachaItem.setName(ResultSetHelper.getNullableObject(rs, "name", String.class, ""));
        dachaItem.setIdDistrictForestly(ResultSetHelper.getNullableObject(rs, "id_district_forestly", Long.class, 0l));

        return dachaItem;
    }
}
