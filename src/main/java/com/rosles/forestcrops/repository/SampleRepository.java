package com.rosles.forestcrops.repository;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import com.rosles.forestcrops.dto.listregion.response.ListRegionList;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.dto.sample.request.SaveSampleRequest;
import com.rosles.forestcrops.dto.sample.response.SampleItemResponse;
import com.rosles.forestcrops.dto.sample.response.SampleListResponse;
import com.rosles.forestcrops.mapper.ListRegionItemRowMapper;
import com.rosles.forestcrops.mapper.SampleItemResponseRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class SampleRepository {

    @Autowired
    private final NamedParameterJdbcTemplate template;

    public SampleRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public SampleListResponse getSampleList(Integer idListRegion, Integer offset,
                                                   Integer size) throws ServiceException {

        try {

            SampleListResponse sampleListResponse = new SampleListResponse();

            HashMap<String, Object> params = new HashMap<>();
            params.put("limit", size);
            params.put("offset", offset);
            params.put("idListRegion", idListRegion);

            String sql = "\n" +
                    "select * from (\n" +
                    "    select \n" +
                    "        id,\n" +
                    "        number,\n" +
                    "        id_listregion_id as id_list_region,\n" +
                    "        length,\n" +
                    "        width\n" +
                    "    from \"public\".\"djangoForest_app_fc_sample\" \n" +
                    "    where id_listregion_id = :idListRegion \n" +
                    ") as sample\n";


            String sqlCount = "select count(*) from (\n" + sql + ") count";

            Integer count = template.queryForObject(sqlCount, params, Integer.class);

            sql += "order by id desc offset :offset limit :limit ";

            List<SampleItemResponse> sampleItemResponseList = template.query(sql, params, new SampleItemResponseRowMapper());

            sampleListResponse.setCount(count);
            sampleListResponse.setData(sampleItemResponseList);

            return sampleListResponse;

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public SampleItemResponse createSample(SaveSampleRequest saveSampleRequest) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("idListRegion", saveSampleRequest.getIdListRegion());
            params.put("length", saveSampleRequest.getLength());
            params.put("width", saveSampleRequest.getWidth());


            String sql = "\n" +
                    "insert into \"djangoForest_app_fc_sample\" (\n" +
                    "    id_listregion_id,\n" +
                    "    \"length\",\n" +
                    "    \"number\",\n" +
                    "    \"width\")\n" +
                    "values (\n" +
                    "    :idListRegion,\n" +
                    "    :length,\n" +
                    "    COALESCE(\n" +
                    "        (SELECT MAX(number) FROM \"public\".\"djangoForest_app_fc_sample\" WHERE id_listregion_id = :idListRegion)::integer,\n" +
                    "        0\n" +
                    "    ) + 1,\n" +
                    "    :width) returning id";


            Integer id = template.queryForObject(sql, params, Integer.class);

            params = new HashMap<>();
            params.put("id", id);

            sql = "\n" +
                    "select \n" +
                    "    id,\n" +
                    "    number,\n" +
                    "    id_listregion_id as id_list_region,\n" +
                    "    length,\n" +
                    "    width\n" +
                    "from \"public\".\"djangoForest_app_fc_sample\" \n" +
                    "where id = :id";

            List<SampleItemResponse> sampleItemResponseList = template.query(sql, params, new SampleItemResponseRowMapper());

            if(!sampleItemResponseList.isEmpty()) {
                return sampleItemResponseList.get(0);
            } else {
                return new SampleItemResponse();
            }

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse deleteSample(Integer id) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);

            String sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_rows\" \n" +
                    "where id_sample_id = :id";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_molod\" \n" +
                    "where id_sample_id = :id";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_plants\" \n" +
                    "where id_sample_id = :id";

            template.update(sql, params);

            sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_sample\" where id = :id";

            template.update(sql, params);

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.deleteListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
