package com.vti.service;

import com.vti.entity.Group;
import com.vti.form.GroupFormForCreating;
import com.vti.form.GroupFormForFilter;
import com.vti.form.GroupFormForUpdating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGroupService {
    public Page<Group> getAllGroups(Pageable pageable, String search, GroupFormForFilter filter);//JPA permission to use

    public Group getGroupByID(short id);

    public Group getGroupByName(String name);

    public void createGroup(GroupFormForCreating form);

    public void updateGroup(short id, GroupFormForUpdating form);

    public void deleteGroup(short id);

    public void deleteGroups(List<Short> ids);

    public boolean isGroupExistsByID(short id);

    public boolean isGroupExistsByName(String name);
}
