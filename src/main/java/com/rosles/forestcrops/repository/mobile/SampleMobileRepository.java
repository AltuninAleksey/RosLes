package com.rosles.forestcrops.repository.mobile;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.sample.request.SaveSampleItemMobile;
import com.rosles.forestcrops.dto.mobile.sample.response.SampleItemMobile;
import com.rosles.forestcrops.dto.mobile.sample.response.SampleListMobile;
import com.rosles.forestcrops.mapper.mobile.SampleItemMobileRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class SampleMobileRepository {

    @Autowired
    private final NamedParameterJdbcTemplate template;

    public SampleMobileRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    public SampleListMobile getSampleList(Integer idProfile) throws ServiceException {

        try {

            String sql = "\n" +
                    "select \n" +
                    "    id,\n" +
                    "    number,\n" +
                    "    length, \n" +
                    "    width,\n" +
                    "    uuid,\n" +
                    "    parent_uuid\n" +
                    "from \"public\".\"djangoForest_app_fc_sample\" \n" +
                    "where id_listregion_id in (\n" +
                    "    select \n" +
                    "        id\n" +
                    "    from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                    "    where \n" +
                    "        id_profile_id = :idProfile\n" +
                    ")";

            HashMap<String, Object> params = new HashMap<>();
            params.put("idProfile", idProfile);

            List<SampleItemMobile> list = template.query(sql, params, new SampleItemMobileRowMapper());

            SampleListMobile sampleListMobile = new SampleListMobile();

            sampleListMobile.setData(list);
            sampleListMobile.setCount(list.size());

            return sampleListMobile;


        } catch (Exception e) {
            log.error("SampleMobileRepository.getSampleList: ", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void updateSample(SaveSampleItemMobile saveSampleItemMobiles) {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("uuid", saveSampleItemMobiles.getUuid());

            String sql = "select count(*) from \"public\".\"djangoForest_app_fc_sample\" where uuid = :uuid";

            Integer countLine = template.queryForObject(sql, params, Integer.class);

            if(countLine == null || countLine == 0) {


                sql = "select \n" +
                        "        id\n" +
                        "    from \"public\".\"djangoForest_app_fc_list_region\"\n" +
                        "    where \n" +
                        "        uuid = :parent_uuid limit 1";

                params.put("parent_uuid", saveSampleItemMobiles.getUuidListRegion());

                Integer idListRegion = null;

                try {
                    idListRegion = template.queryForObject(sql, params, Integer.class);
                } catch (Exception e) {
                    log.error("Error: SampleMobileRepository.updateSample list region npt found for uuid: {}",
                            saveSampleItemMobiles.getUuidListRegion(), e.getMessage());
                }

                if(idListRegion == null) {
                    return;
                }

                sql = "insert into \"djangoForest_app_fc_sample\" (\n" +
                        "    id_listregion_id,\n" +
                        "    \"length\",\n" +
                        "    \"number\",\n" +
                        "    parent_uuid,\n" +
                        "    \"uuid\",\n" +
                        "    \"width\")\n" +
                        "values (\n" +
                        "    :id_listregion_id,\n" +
                        "    :length,\n" +
                        "    :number,\n" +
                        "    :parent_uuid,\n" +
                        "    :uuid,\n" +
                        "    :width)";

                params.put("id_listregion_id", idListRegion);
                params.put("width", saveSampleItemMobiles.getWidth());
                params.put("length", saveSampleItemMobiles.getLength());
                params.put("number", saveSampleItemMobiles.getNumber());

                template.update(sql, params);

            } else {

                params.put("width", saveSampleItemMobiles.getWidth());
                params.put("length", saveSampleItemMobiles.getLength());
                params.put("number", saveSampleItemMobiles.getNumber());

                sql = "\n" +
                        "update \"public\".\"djangoForest_app_fc_sample\" set\n" +
                        "    length = :length,\n" +
                        "    width = :width,\n" +
                        "    number = :number\n" +
                        "where uuid = :uuid";

                template.update(sql, params);

            }

        } catch (Exception e) {
            log.error("Error: SampleMobileRepository.updateSample", e);
        }
    }


}
