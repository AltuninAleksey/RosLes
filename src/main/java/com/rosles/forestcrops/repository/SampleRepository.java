package com.rosles.forestcrops.repository;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import com.rosles.forestcrops.dto.listregion.response.ListRegionList;
import com.rosles.forestcrops.dto.request.ListIntegerRequest;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.dto.sample.request.SaveForestCropsItem;
import com.rosles.forestcrops.dto.sample.request.SaveMolodForestCropsItem;
import com.rosles.forestcrops.dto.sample.request.SavePlantsForestCropsItem;
import com.rosles.forestcrops.dto.sample.request.SaveSampleRequest;
import com.rosles.forestcrops.dto.sample.response.*;
import com.rosles.forestcrops.mapper.*;
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

    public SampleInfoResponse getSampleInfo(Integer id) throws ServiceException {
        try {

            SampleInfoResponse sampleInfoResponse = new SampleInfoResponse();


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
                    "    where id in (select id_listregion_id from \"public\".\"djangoForest_app_fc_sample\" where id = :id)\n" +
                    ") list_region\n" +
                    "left join \"public\".\"djangoForest_districtforestly\" districtforestly\n" +
                    "    on districtforestly.id = list_region.id_district_forestly_id\n" +
                    "left join \"public\".\"djangoForest_forestly\" forestly\n" +
                    "    on forestly.id = districtforestly.id_forestly_id ";


            List<ListRegionItem> listRegionItemList = template.query(sql, params, new ListRegionItemRowMapper());

            if(!listRegionItemList.isEmpty()) {
                sampleInfoResponse.setListRegion(listRegionItemList.get(0));
            } else {
                sampleInfoResponse.setListRegion(new ListRegionItem());
            }

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
                sampleInfoResponse.setSample(sampleItemResponseList.get(0));
            } else {
                sampleInfoResponse.setSample(new SampleItemResponse());
            }

            return sampleInfoResponse;
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


    public SampleItemResponse updateSample(SaveSampleRequest saveSampleRequest) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();
            params.put("id", saveSampleRequest.getId());
            params.put("length", saveSampleRequest.getLength());
            params.put("width", saveSampleRequest.getWidth());


            String sql = "\n" +
                    "update \"public\".\"djangoForest_app_fc_sample\" set\n" +
                    "    length = :length,\n" +
                    "    width = :width\n" +
                    "where id = :id";

            template.update(sql, params);

            params = new HashMap<>();
            params.put("id", saveSampleRequest.getId());

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




    public ForestCropsList getForestCropsList(Integer idSample, Integer offset,
                                                              Integer size) throws ServiceException {

        try {

            ForestCropsList forestCropsList = new ForestCropsList();

            HashMap<String, Object> params = new HashMap<>();
            params.put("limit", size);
            params.put("offset", offset);
            params.put("id", idSample);

            String sql = "\n" +
                    "select * from \"public\".\"djangoForest_app_fc_forest_crops_rows\" where id_sample_id = :id \n";


            String sqlCount = "select count(*) from (\n" + sql + ") count";

            Integer count = template.queryForObject(sqlCount, params, Integer.class);

            sql += "order by id desc offset :offset limit :limit ";

            List<ForestCropsItem> forestCropsItemList = template.query(sql, params, new ForestCropsItemRowMapper());

            forestCropsList.setCount(count);
            forestCropsList.setData(forestCropsItemList);

            return forestCropsList;

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse deleteForestCrops(ListIntegerRequest listIntegerRequest) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_rows\" where id = :id \n";

            for (Integer id : listIntegerRequest.getValues()) {

                params = new HashMap<>();
                params.put("id", id);

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse createForestCrops(SaveForestCropsItem saveForestCropsItem) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "insert into \"djangoForest_app_fc_forest_crops_rows\" (\n" +
                    "    count_dead,\n" +
                    "    count_living,\n" +
                    "    id_breed_id,\n" +
                    "    id_sample_id,\n" +
                    "    \"number\")\n" +
                    "values (\n" +
                    "    :count_dead,\n" +
                    "    :count_living,\n" +
                    "    :id_breed_id,\n" +
                    "    :id_sample_id,\n" +
                    "    1)";

            for (ForestCropsItem item : saveForestCropsItem.getValues()) {

                params = new HashMap<>();
                params.put("count_dead", item.getCountDead());
                params.put("count_living", item.getCountLiving());
                params.put("id_breed_id", item.getIdBreed());
                params.put("id_sample_id", saveForestCropsItem.getIdSample());

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse updateForestCrops(SaveForestCropsItem saveForestCropsItem) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "update \"public\".\"djangoForest_app_fc_forest_crops_rows\" set\n" +
                    "    count_dead = :count_dead,\n" +
                    "    count_living = :count_living,\n" +
                    "    id_breed_id = :id_breed_id\n" +
                    "where id = :id";

            for (ForestCropsItem item : saveForestCropsItem.getValues()) {

                params = new HashMap<>();
                params.put("id", item.getId());
                params.put("count_dead", item.getCountDead());
                params.put("count_living", item.getCountLiving());
                params.put("id_breed_id", item.getIdBreed());

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public PlantsForestCropsList getPlantsForestCropsList( Integer idSample, Integer offset, Integer size) throws ServiceException {
        try {

            PlantsForestCropsList plantsForestCropsList = new PlantsForestCropsList();

            HashMap<String, Object> params = new HashMap<>();
            params.put("limit", size);
            params.put("offset", offset);
            params.put("id", idSample);

            String sql = "\n" +
                    "select * from \"public\".\"djangoForest_app_fc_forest_crops_plants\" where id_sample_id = :id \n";


            String sqlCount = "select count(*) from (\n" + sql + ") count";

            Integer count = template.queryForObject(sqlCount, params, Integer.class);

            sql += "order by id desc offset :offset limit :limit ";

            List<PlantsForestCropsItem> plantsForestCropsItemList = template.query(sql, params, new PlantsForestCropsItemRowMapper());

            plantsForestCropsList.setCount(count);
            plantsForestCropsList.setData(plantsForestCropsItemList);

            return plantsForestCropsList;

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse deletePlansForestCrops(ListIntegerRequest listIntegerRequest) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_plants\" where id = :id \n";

            for (Integer id : listIntegerRequest.getValues()) {

                params = new HashMap<>();
                params.put("id", id);

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse createPlantsForestCrops(SavePlantsForestCropsItem savePlantsForestCropsItem) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "insert into \"djangoForest_app_fc_forest_crops_plants\" (\n" +
                    "    \"diameter\",\n" +
                    "    \"height\",\n" +
                    "    id_breed_id,\n" +
                    "    id_sample_id,\n" +
                    "    \"number\")\n" +
                    "values (\n" +
                    "    :diameter,\n" +
                    "    :height,\n" +
                    "    :id_breed_id,\n" +
                    "    :id_sample_id,\n" +
                    "    1)";

            for (PlantsForestCropsItem item : savePlantsForestCropsItem.getValues()) {

                params = new HashMap<>();
                params.put("diameter", item.getDiameter());
                params.put("height", item.getHeight());
                params.put("id_breed_id", item.getIdBreed());
                params.put("id_sample_id", savePlantsForestCropsItem.getIdSample());

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse updatePlantsForestCrops(SavePlantsForestCropsItem savePlantsForestCropsItem) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "update \"public\".\"djangoForest_app_fc_forest_crops_plants\" set\n" +
                    "    diameter = :diameter,\n" +
                    "    height = :height,\n" +
                    "    id_breed_id = :id_breed_id\n" +
                    "where id = :id";

            for (PlantsForestCropsItem item : savePlantsForestCropsItem.getValues()) {

                params = new HashMap<>();
                params.put("id", item.getId());
                params.put("diameter", item.getDiameter());
                params.put("height", item.getHeight());
                params.put("id_breed_id", item.getIdBreed());

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public MolodForestCropsList getMolodForestCropsList(Integer idSample, Integer offset, Integer size) throws ServiceException {
        try {

            MolodForestCropsList molodForestCropsList = new MolodForestCropsList();

            HashMap<String, Object> params = new HashMap<>();
            params.put("limit", size);
            params.put("offset", offset);
            params.put("id", idSample);

            String sql = "\n" +
                    "select * from \"public\".\"djangoForest_app_fc_forest_crops_molod\" where id_sample_id = :id \n";


            String sqlCount = "select count(*) from (\n" + sql + ") count";

            Integer count = template.queryForObject(sqlCount, params, Integer.class);

            sql += "order by id desc offset :offset limit :limit ";

            List<MolodForestCropsItem> molodForestCropsItemList = template.query(sql, params, new MolodForestCropsItemRowMapper());

            molodForestCropsList.setCount(count);
            molodForestCropsList.setData(molodForestCropsItemList);

            return molodForestCropsList;

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse deleteMolodForestCrops(ListIntegerRequest listIntegerRequest) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "delete from \"public\".\"djangoForest_app_fc_forest_crops_molod\" where id = :id \n";

            for (Integer id : listIntegerRequest.getValues()) {

                params = new HashMap<>();
                params.put("id", id);

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse createMolodForestCrops(SaveMolodForestCropsItem saveMolodForestCropsItem) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "insert into \"djangoForest_app_fc_forest_crops_molod\" (\n" +
                    "    \"from0_6To1_5\",\n" +
                    "    from1_5,\n" +
                    "    id_breed_id,\n" +
                    "    id_sample_id,\n" +
                    "    max_height,\n" +
                    "    \"number\",\n" +
                    "    to0_5)\n" +
                    "values (\n" +
                    "    :from0_6To1_5,\n" +
                    "    :from1_5,\n" +
                    "    1,\n" +
                    "    4,\n" +
                    "    1,\n" +
                    "    1,\n" +
                    "    :to0_5)";

            for (MolodForestCropsItem item : saveMolodForestCropsItem.getValues()) {

                params = new HashMap<>();
                params.put("from0_6To1_5", item.getFrom0_6To1_5());
                params.put("from1_5", item.getFrom1_5());
                params.put("to0_5", item.getTo0_5());
                params.put("id_breed_id", item.getIdBreed());
                params.put("id_sample_id", saveMolodForestCropsItem.getIdSample());

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.createMolodForestCrops", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = ServiceException.class)
    public StatusResponse updateMolodForestCrops(SaveMolodForestCropsItem saveMolodForestCropsItem) throws ServiceException {
        try {

            HashMap<String, Object> params = new HashMap<>();

            String sql = "\n" +
                    "update \"public\".\"djangoForest_app_fc_forest_crops_molod\" set\n" +
                    "    \"from0_6To1_5\" = :from0_6To1_5,\n" +
                    "    from1_5 = :from1_5,\n" +
                    "    to0_5 = :to0_5,\n" +
                    "    id_breed_id = :id_breed_id\n" +
                    "where id = :id";

            for (MolodForestCropsItem item : saveMolodForestCropsItem.getValues()) {

                params = new HashMap<>();
                params.put("id", item.getId());
                params.put("from0_6To1_5", item.getFrom0_6To1_5());
                params.put("from1_5", item.getFrom1_5());
                params.put("to0_5", item.getTo0_5());
                params.put("id_breed_id", item.getIdBreed());

                template.update(sql, params);
            }

            return new StatusResponse(0, "success");

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.getForestCropsList", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
