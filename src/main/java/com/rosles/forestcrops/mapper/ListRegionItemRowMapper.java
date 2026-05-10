package com.rosles.forestcrops.mapper;

import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import com.rosles.forestcrops.utils.ResultSetHelper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ListRegionItemRowMapper implements RowMapper<ListRegionItem> {
    @Override
    public ListRegionItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        ListRegionItem listRegionItem = new ListRegionItem();

        listRegionItem.setId(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l));
        listRegionItem.setNumber(ResultSetHelper.getNullableObject(rs, "id", Long.class, 0l).toString());
        listRegionItem.setDate(ResultSetHelper.getNullableObject(rs, "date_examination", LocalDate.class, null));
        listRegionItem.setDacha(ResultSetHelper.getNullableObject(rs, "dacha", String.class, ""));
        listRegionItem.setNameQuarter(ResultSetHelper.getNullableObject(rs, "name_quarter", String.class, ""));
        listRegionItem.setSampleRegion(ResultSetHelper.getNullableObject(rs, "sample_region", Double.class, null));
        listRegionItem.setSoilLot(ResultSetHelper.getNullableObject(rs, "soil_lot", String.class, ""));
        listRegionItem.setIdDistrictForestly(ResultSetHelper.getNullableObject(rs, "id_district_forestly_id", Long.class, 0l));
        listRegionItem.setIdForestly(ResultSetHelper.getNullableObject(rs, "id_forestly", Long.class, 0l));
        listRegionItem.setIdSubject(ResultSetHelper.getNullableObject(rs, "id_subject_rf", Long.class, 0l));

        return listRegionItem;
    }
}
