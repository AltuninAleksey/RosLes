package com.rosles.forestcrops.repository;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.listregion.request.SaveListRegionRequest;
import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import com.rosles.forestcrops.dto.listregion.response.ListRegionList;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.mapper.ListRegionItemRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class ListRegionRepository {

    @Autowired
    private final NamedParameterJdbcTemplate template;

    public ListRegionRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public ListRegionList getListRegion(
            Integer idSubjectFilet, Integer idDistrictForestlyFilet,
            Integer idForestlyFilet, String soilLotFilter, String nameQuarterFilter,
            Integer idSubjectRf, Integer offset, Integer size) {
        try {

            ListRegionList listRegionList = new ListRegionList();

            HashMap<String, Object> params = new HashMap<>();
            params.put("limit", size);
            params.put("offset", offset);
            params.put("subjectRf", idSubjectRf);

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
                    "            id_district_forestly_id in (\n" +
                    "                select \n" +
                    "                    id \n" +
                    "                from \"public\".\"djangoForest_districtforestly\" \n" +
                    "                where \n" +
                    "                    id_forestly_id in (\n" +
                    "                        select \n" +
                    "                            id \n" +
                    "                        from \"public\".\"djangoForest_forestly\" \n" +
                    "                        where \n" +
                    "                            id_subject_rf_id in (\n" +
                    "                                select \n" +
                    "                                    id_subject_id \n" +
                    "                                from \"public\".\"djangoForest_czl\" \n" +
                    "                                where \n" +
                    "                                    id_main_subject_id = (\n" +
                    "                                        select \n" +
                    "                                            id_main_subject_id \n" +
                    "                                        from \"public\".\"djangoForest_czl\" \n" +
                    "                                        where id_subject_id = :subjectRf\n" +
                    "                                    )\n" +
                    "                                union\n" +
                    "                                select \n" +
                    "                                    id_main_subject_id \n" +
                    "                                from \"public\".\"djangoForest_czl\" \n" +
                    "                                where id_subject_id = :subjectRf\n" +
                    "                            )\n" +
                    "                    )\n" +
                    "            )\n" +
                    "    ) list_region\n" +
                    "    left join \"public\".\"djangoForest_districtforestly\" districtforestly\n" +
                    "        on districtforestly.id = list_region.id_district_forestly_id\n" +
                    "    left join \"public\".\"djangoForest_forestly\" forestly\n" +
                    "        on forestly.id = districtforestly.id_forestly_id\n";

                    if(idSubjectFilet != null || idForestlyFilet != null || idDistrictForestlyFilet != null
                        || soilLotFilter != null || nameQuarterFilter != null) {
                        sql += "    where \n";
                    }

                    int countParam = 0;

                    if(idSubjectFilet != null) {
                        sql += " forestly.id_subject_rf_id = " + idSubjectFilet + " \n";
                        countParam++;
                    }

                    if(idDistrictForestlyFilet != null) {
                        if(countParam > 0) {
                            sql += "        and ";
                        }

                        sql += " id_district_forestly_id = " + idDistrictForestlyFilet + " \n";
                        countParam++;
                    }

                    if(idForestlyFilet != null) {
                        if(countParam > 0) {
                            sql += "        and ";
                        }

                        sql += " districtforestly.id_forestly_id = " + idForestlyFilet + " \n";
                        countParam++;
                    }

                    if(soilLotFilter != null) {
                        if(countParam > 0) {
                            sql += "        and ";
                        }

                        sql += " lower(soil_lot) like '%" + soilLotFilter.toLowerCase() + "%' \n";
                        countParam++;
                    }

                    if(nameQuarterFilter != null) {
                        if(countParam > 0) {
                            sql += "        and ";
                        }

                        sql += " lower(name_quarter) like '%" + nameQuarterFilter.toLowerCase() + "%' \n";
                    }

                    sql +=
                    ") list_region\n";

                    String sqlCount = "select count(*) from (\n" + sql + ") count";

                    Integer count = template.queryForObject(sqlCount, params, Integer.class);

                    sql +=
                    "order by list_region.id desc limit :limit offset :offset";

            List<ListRegionItem> listRegionItems = template.query(sql, params, new ListRegionItemRowMapper());

            listRegionList.setCount(count);
            listRegionList.setData(listRegionItems);

            return listRegionList;

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getListRegion", e);
            return new ListRegionList();
        }
    }

    public ListRegionItem getInfo(Integer id) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);

            String sql = "\n" +
                    "select\n" +
                    "    list_region.*,\n" +
                    "    districtforestly.id_forestly_id as id_forestly,\n" +
                    "    forestly.id_subject_rf_id as id_subject_rf\n" +
                    "from (\n" +
                    "    select \n" +
                    "        *\n" +
                    "    from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                    "    where id = :id \n" +
                    ") list_region\n" +
                    "left join \"public\".\"djangoForest_districtforestly\" districtforestly\n" +
                    "    on districtforestly.id = list_region.id_district_forestly_id\n" +
                    "left join \"public\".\"djangoForest_forestly\" forestly\n" +
                    "    on forestly.id = districtforestly.id_forestly_id";

            List<ListRegionItem> listRegionItems = template.query(sql, params, new ListRegionItemRowMapper());

            if(!listRegionItems.isEmpty()) {
                return listRegionItems.get(0);
            } else {
                return new ListRegionItem();
            }

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ListRegionItem createListRegion(SaveListRegionRequest saveListRegionRequest, Integer IdProfile) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("dacha", saveListRegionRequest.getDacha());
            params.put("date_examination", saveListRegionRequest.getDate());
            params.put("id_district_forestly_id", saveListRegionRequest.getIdDistrictForestly());
            params.put("id_profile_id", IdProfile);
            params.put("name_quarter", saveListRegionRequest.getNameQuarter());
            params.put("sample_region", saveListRegionRequest.getSampleRegion());
            params.put("soil_lot", saveListRegionRequest.getSoilLot());

            String sql = "\n" +
                    "insert into \"djangoForest_app_fc_list_region\" (\n" +
                    "    number,\n" +
                    "    dacha,\n" +
                    "    date_create,\n" +
                    "    date_examination,\n" +
                    "    id_district_forestly_id,\n" +
                    "    id_profile_id,\n" +
                    "    name_quarter,\n" +
                    "    sample_region,\n" +
                    "    soil_lot)\n" +
                    "values (\n" +
                    "    '1',\n" +
                    "    :dacha,\n" +
                    "    CURRENT_DATE,\n" +
                    "    :date_examination,\n" +
                    "    :id_district_forestly_id,\n" +
                    "    :id_profile_id,\n" +
                    "    :name_quarter,\n" +
                    "    :sample_region,\n" +
                    "    :soil_lot) returning id";

            Integer id = template.queryForObject(sql, params, Integer.class);


            params = new HashMap<>();
            params.put("id", id);

            sql = "\n" +
                    "select\n" +
                    "    list_region.*,\n" +
                    "    districtforestly.id_forestly_id as id_forestly,\n" +
                    "    forestly.id_subject_rf_id as id_subject_rf\n" +
                    "from (\n" +
                    "    select \n" +
                    "        *\n" +
                    "    from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                    "    where id = :id \n" +
                    ") list_region\n" +
                    "left join \"public\".\"djangoForest_districtforestly\" districtforestly\n" +
                    "    on districtforestly.id = list_region.id_district_forestly_id\n" +
                    "left join \"public\".\"djangoForest_forestly\" forestly\n" +
                    "    on forestly.id = districtforestly.id_forestly_id";

            List<ListRegionItem> listRegionItems = template.query(sql, params, new ListRegionItemRowMapper());

            if(!listRegionItems.isEmpty()) {
                return listRegionItems.get(0);
            } else {
                return new ListRegionItem();
            }

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.createListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ListRegionItem updateListRegion(SaveListRegionRequest saveListRegionRequest) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("dacha", saveListRegionRequest.getDacha());
            params.put("date", saveListRegionRequest.getDate());
            params.put("id_district_forestly_id", saveListRegionRequest.getIdDistrictForestly());
            params.put("name_quarter", saveListRegionRequest.getNameQuarter());
            params.put("sample_region", saveListRegionRequest.getSampleRegion());
            params.put("soil_lot", saveListRegionRequest.getSoilLot());
            params.put("id", saveListRegionRequest.getId());

            String sql = "\n" +
                    "update \"public\".\"djangoForest_app_fc_list_region\" set\n" +
                    "    dacha = :dacha,\n" +
                    "    date_examination = :date,\n" +
                    "    id_district_forestly_id = :id_district_forestly_id,\n" +
                    "    name_quarter = :name_quarter,\n" +
                    "    sample_region = :sample_region,\n" +
                    "    soil_lot = :soil_lot\n" +
                    "where id = :id";

            template.update(sql, params);


            params = new HashMap<>();
            params.put("id", saveListRegionRequest.getId());

            sql = "\n" +
                    "select\n" +
                    "    list_region.*,\n" +
                    "    districtforestly.id_forestly_id as id_forestly,\n" +
                    "    forestly.id_subject_rf_id as id_subject_rf\n" +
                    "from (\n" +
                    "    select \n" +
                    "        *\n" +
                    "    from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                    "    where id = :id \n" +
                    ") list_region\n" +
                    "left join \"public\".\"djangoForest_districtforestly\" districtforestly\n" +
                    "    on districtforestly.id = list_region.id_district_forestly_id\n" +
                    "left join \"public\".\"djangoForest_forestly\" forestly\n" +
                    "    on forestly.id = districtforestly.id_forestly_id";

            List<ListRegionItem> listRegionItems = template.query(sql, params, new ListRegionItemRowMapper());

            if(!listRegionItems.isEmpty()) {
                return listRegionItems.get(0);
            } else {
                return new ListRegionItem();
            }

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.createListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse deleteListRegion(Integer id) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);

            String sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_rows\" \n" +
                    "where id_sample_id in (select id from \"public\".\"djangoForest_app_fc_sample\" where id_listregion_id = :id)";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_molod\" \n" +
                    "where id_sample_id in (select id from \"public\".\"djangoForest_app_fc_sample\" where id_listregion_id = :id)";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_plants\" \n" +
                    "where id_sample_id in (select id from \"public\".\"djangoForest_app_fc_sample\" where id_listregion_id = :id)";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_sample\" where id_listregion_id = :id";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_list_region\" where id = :id";

            template.update(sql, params);

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.deleteListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
