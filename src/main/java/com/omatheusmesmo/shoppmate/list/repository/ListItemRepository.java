package com.omatheusmesmo.shoppmate.list.repository;

import com.omatheusmesmo.shoppmate.list.entity.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListItemRepository extends JpaRepository<ListItem, Long> {

    List<ListItem> findByShoppListId(Long shopplistId);

}
