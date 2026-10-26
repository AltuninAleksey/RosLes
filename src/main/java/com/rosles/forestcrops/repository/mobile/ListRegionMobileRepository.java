package com.rosles.forestcrops.repository.mobile;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.listregion.request.SaveListRegionMobileRequest;
import com.rosles.forestcrops.dto.mobile.listregion.response.ListRegionItemMobile;
import com.rosles.forestcrops.dto.mobile.listregion.response.ListRegionListMobile;
import com.rosles.forestcrops.mapper.ListRegionItemMobileRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class ListRegionMobileRepository {

    @Autowired
    private final NamedParameterJdbcTemplate template;

    public ListRegionMobileRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public ListRegionListMobile getListRegion(Integer idProfile) throws ServiceException {
        try {

            ListRegionListMobile listRegionList = new ListRegionListMobile();

            HashMap<String, Object> params = new HashMap<>();
            params.put("idProfile", idProfile);

            String sql = "" +
                    "select \n" +
                    "    *\n" +
                    "from (\n" +
                    "    select\n" +
                    "        list_region.*,\n" +
                    "        districtforestly.id_forestly_id as id_forestly,\n" +
                    "        forestly.id_subject_rf_id as id_subject_rf\n" +
                    "    from (\n" +
                    "        select \n" +
                    "            *\n" +
                    "        from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                    "        where \n" +
                    "            id_profile_id = :idProfile\n" +
                    "    ) list_region\n" +
                    "    left join \"public\".\"djangoForest_districtforestly\" districtforestly\n" +
                    "        on districtforestly.id = list_region.id_district_forestly_id\n" +
                    "    left join \"public\".\"djangoForest_forestly\" forestly\n" +
                    "        on forestly.id = districtforestly.id_forestly_id\n" +
                    ") list_region";

            String sqlCount = "select count(*) from (\n" + sql + ") count";

            Integer count = template.queryForObject(sqlCount, params, Integer.class);


            List<ListRegionItemMobile> listRegionItems = template.query(sql, params, new ListRegionItemMobileRowMapper());

            listRegionList.setCount(count);
            listRegionList.setData(listRegionItems);

            return listRegionList;

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateListRegion(SaveListRegionMobileRequest saveListRegionRequest, Integer IdProfile) {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("uuid", saveListRegionRequest.getUuid());

            String sql = "select * from \"public\".\"djangoForest_app_fc_list_region\" where uuid = :uuid";

            List<Map<String, Object>> result = template.queryForList(sql, params);

            if(result.isEmpty()) {

                params.put("date_examination", saveListRegionRequest.getDate());
                params.put("id_district_forestly_id", saveListRegionRequest.getIdDistrictForestly());
                params.put("id_profile_id", IdProfile);
                params.put("name_quarter", saveListRegionRequest.getNameQuarter());
                params.put("sample_region", saveListRegionRequest.getSampleRegion());
                params.put("soil_lot", saveListRegionRequest.getSoilLot());

                if(saveListRegionRequest.getDacha() == null) {
                    params.put("dacha", "");
                } else {
                    params.put("dacha", saveListRegionRequest.getDacha());
                }

                if(saveListRegionRequest.getIdDacha() == null) {
                    params.put("id_dacha_id", 8823);
                } else {
                    params.put("id_dacha_id", saveListRegionRequest.getIdDacha());
                }

                sql = "\n" +
                        "insert into \"djangoForest_app_fc_list_region\" (\n" +
                        "    number,\n" +
                        "    dacha,\n" +
                        "    date_create,\n" +
                        "    date_examination,\n" +
                        "    id_district_forestly_id,\n" +
                        "    id_profile_id,\n" +
                        "    name_quarter,\n" +
                        "    sample_region,\n" +
                        "    id_dacha_id,\n" +
                        "    soil_lot, \n" +
                        "    uuid)\n" +
                        "values (\n" +
                        "    '1',\n" +
                        "    :dacha,\n" +
                        "    CURRENT_DATE,\n" +
                        "    :date_examination,\n" +
                        "    :id_district_forestly_id,\n" +
                        "    :id_profile_id,\n" +
                        "    :name_quarter,\n" +
                        "    :sample_region,\n" +
                        "    :id_dacha_id,\n" +
                        "    :soil_lot, \n" +
                        "    :uuid) returning id";

                template.queryForObject(sql, params, Integer.class);

            } else {

                params.put("date", saveListRegionRequest.getDate());
                params.put("id_district_forestly_id", saveListRegionRequest.getIdDistrictForestly());
                params.put("name_quarter", saveListRegionRequest.getNameQuarter());
                params.put("sample_region", saveListRegionRequest.getSampleRegion());
                params.put("soil_lot", saveListRegionRequest.getSoilLot());

                if(saveListRegionRequest.getDacha() == null) {
                    params.put("dacha", "");
                } else {
                    params.put("dacha", saveListRegionRequest.getDacha());
                }

                if(saveListRegionRequest.getIdDacha() == null) {
                    params.put("id_dacha_id", 8823);
                } else {
                    params.put("id_dacha_id", saveListRegionRequest.getIdDacha());
                }


                sql = "\n" +
                        "update \"public\".\"djangoForest_app_fc_list_region\" set\n" +
                        "    dacha = :dacha,\n" +
                        "    date_examination = :date,\n" +
                        "    id_district_forestly_id = :id_district_forestly_id,\n" +
                        "    name_quarter = :name_quarter,\n" +
                        "    sample_region = :sample_region,\n" +
                        "    id_dacha_id = :id_dacha_id,\n" +
                        "    soil_lot = :soil_lot\n" +
                        "where uuid = :uuid";


                template.update(sql, params);

            }

        } catch (Exception e) {
            log.error("Error: ListRegionMobileRepository.updateListRegion", e);
        }
    }


}
