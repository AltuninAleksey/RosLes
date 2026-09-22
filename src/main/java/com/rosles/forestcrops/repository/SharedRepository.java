package com.rosles.forestcrops.repository;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.shared.response.BreedItem;
import com.rosles.forestcrops.dto.shared.response.BreedItemList;
import com.rosles.forestcrops.dto.shared.response.Item;
import com.rosles.forestcrops.dto.shared.response.ItemList;
import com.rosles.forestcrops.mapper.BreedItemRowMapper;
import com.rosles.forestcrops.mapper.ItemListRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@Slf4j
public class SharedRepository {

    @Autowired
    private final NamedParameterJdbcTemplate template;

    public SharedRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    public ItemList getAllDacha() throws ServiceException {

        try {

            String sql = "select id, name_side as name from \"public\".\"djangoForest_dacha\"";

            HashMap<String, Object> params = new HashMap<>();

            List<Item> items = template.query(sql, params, new ItemListRowMapper());

            ItemList itemList = new ItemList();

            itemList.setData(items);
            itemList.setCount(items.size());

            return itemList;


        } catch (Exception e) {
            log.error(e.getMessage());

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ItemList getDacha(Integer idDistrictForestly) throws ServiceException {

        try {

            String sql = "select id, name_side as name from \"public\".\"djangoForest_dacha\" where district_forestly_id = :idDistrictForestly";

            HashMap<String, Object> params = new HashMap<>();
            params.put("idDistrictForestly", idDistrictForestly);

            List<Item> items = template.query(sql, params, new ItemListRowMapper());

            ItemList itemList = new ItemList();

            itemList.setData(items);
            itemList.setCount(items.size());

            return itemList;


        } catch (Exception e) {
            log.error(e.getMessage());

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public BreedItemList getBreed() throws ServiceException {

        try {

            String sql = "select id, name_breed, short_name from \"public\".\"djangoForest_breed\"";

            HashMap<String, Object> params = new HashMap<>();

            List<BreedItem> items = template.query(sql, params, new BreedItemRowMapper());

            BreedItemList itemList = new BreedItemList();

            itemList.setData(items);
            itemList.setCount(items.size());

            return itemList;


        } catch (Exception e) {
            log.error(e.getMessage());

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
