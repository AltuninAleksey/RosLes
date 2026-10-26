package com.rosles.forestcrops.repository.mobile;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.forestcropsrow.request.SaveForestCropsRowItem;
import com.rosles.forestcrops.dto.mobile.forestcropsrow.response.ForestCropsRowItem;
import com.rosles.forestcrops.dto.mobile.forestcropsrow.response.ForestCropsRowList;
import com.rosles.forestcrops.mapper.mobile.ForestCropsRowItemRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class ForestCropsRowMobileRepository {

    @Autowired
    private final NamedParameterJdbcTemplate template;

    public ForestCropsRowMobileRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public ForestCropsRowList getForestCropsList(Integer idProfile) throws ServiceException {

        try {

            String sql = "\n" +
                    "select \n" +
                    "    id,\n" +
                    "    number,\n" +
                    "    count_living,\n" +
                    "    count_dead,\n" +
                    "    id_breed_id,\n" +
                    "    uuid,\n" +
                    "    parent_uuid\n" +
                    "from \"public\".\"djangoForest_app_fc_forest_crops_rows\" \n" +
                    "where id_sample_id in (\n" +
                    "    select \n" +
                    "        id\n" +
                    "    from \"public\".\"djangoForest_app_fc_sample\" \n" +
                    "    where id_listregion_id in (\n" +
                    "        select \n" +
                    "            id\n" +
                    "        from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                    "        where \n" +
                    "            id_profile_id = :idProfile\n" +
                    "    )\n" +
                    ")";

            HashMap<String, Object> params = new HashMap<>();
            params.put("idProfile", idProfile);


            List<ForestCropsRowItem> forestCropsRowItems =
                    template.query(sql, params, new ForestCropsRowItemRowMapper());

            ForestCropsRowList forestCropsRowList = new ForestCropsRowList();

            forestCropsRowList.setData(forestCropsRowItems);
            forestCropsRowList.setCount(forestCropsRowItems.size());

            return forestCropsRowList;

        } catch (Exception e) {
            log.error("ForestCropsRowMobileRepository.getSampleList: ", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void updateForestCropsRow(SaveForestCropsRowItem saveForestCropsItem)  {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("uuid", saveForestCropsItem.getUuid());

            String sql = "select count(*) from \"public\".\"djangoForest_app_fc_forest_crops_rows\" where uuid =  :uuid";

            Integer countLine = template.queryForObject(sql, params, Integer.class);

            if(countLine == null || countLine == 0) {

                sql = "select \n" +
                        "        id\n" +
                        "    from \"public\".\"djangoForest_app_fc_sample\" \n" +
                        "    where \n" +
                        "        uuid = :parent_uuid limit 1";

                params.put("parent_uuid", saveForestCropsItem.getUuidSample());

                Integer idSample = null;

                try {
                    idSample = template.queryForObject(sql, params, Integer.class);
                } catch (Exception e) {
                    log.error("Error: ForestCropsRowMobileRepository.updateForestCropsRow sample not found for uuid: {}",
                            saveForestCropsItem.getUuidSample(), e.getMessage());
                }

                if(idSample == null) {
                    return;
                }

                sql = "insert into \"djangoForest_app_fc_forest_crops_rows\" (\n" +
                        "    count_dead,\n" +
                        "    count_living,\n" +
                        "    id_breed_id,\n" +
                        "    id_sample_id,\n" +
                        "    \"number\",\n" +
                        "    parent_uuid,\n" +
                        "    \"uuid\")\n" +
                        "values (\n" +
                        "    :count_dead,\n" +
                        "    :count_living,\n" +
                        "    :id_breed_id,\n" +
                        "    :id_sample_id,\n" +
                        "    :number,\n" +
                        "    :parent_uuid,\n" +
                        "    :uuid)";

                params.put("count_dead", saveForestCropsItem.getCountDead());
                params.put("count_living", saveForestCropsItem.getCountLiving());
                params.put("id_breed_id", saveForestCropsItem.getIdBreed());
                params.put("id_sample_id", idSample);
                params.put("number", saveForestCropsItem.getNumber());
                params.put("parent_uuid", saveForestCropsItem.getUuidSample());
                params.put("uuid", saveForestCropsItem.getUuid());

                template.update(sql, params);

            } else {

                params.put("count_dead", saveForestCropsItem.getCountDead());
                params.put("count_living", saveForestCropsItem.getCountLiving());
                params.put("id_breed_id", saveForestCropsItem.getIdBreed());
                params.put("number", saveForestCropsItem.getNumber());

                sql = "\n" +
                        "update \"public\".\"djangoForest_app_fc_forest_crops_rows\" set\n" +
                        "    count_dead = :count_dead,\n" +
                        "    count_living = :count_living,\n" +
                        "    id_breed_id = :id_breed_id,\n" +
                        "    number = :number\n" +
                        "where uuid = :uuid";

                template.update(sql, params);

            }

        } catch (Exception e) {
            log.error("Error: ForestCropsRowMobileRepository.updateForestCropsRow", e);
        }
    }


}