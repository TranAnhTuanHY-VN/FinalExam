package com.vti.service;

import com.vti.entity.Account;
import com.vti.entity.Group;
import com.vti.form.GroupFormForCreating;
import com.vti.form.GroupFormForFilter;
import com.vti.form.GroupFormForUpdating;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IGroupRepository;
import com.vti.specification.GroupSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupService implements IGroupService {

    @Autowired
    private IGroupRepository groupRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public Page<Group> getAllGroups(Pageable pageable, String search, GroupFormForFilter filter) {
        Specification<Group> where = null;

        if (!StringUtils.isEmpty(search)){
            GroupSpecification nameSpecification = new GroupSpecification("name","LIKE",search);
            GroupSpecification authorSpecification = new GroupSpecification("creator.fullname","LIKE",search);
            where = Specification.where(nameSpecification).or(authorSpecification);
        }

        if (filter != null && filter.getMinDate() != null){
            GroupSpecification minDateSpecification = new GroupSpecification("createDate",">=",filter.getMinDate());
            if (where == null){
                where = Specification.where(minDateSpecification);
            }else {
                where = where.and(minDateSpecification);
            }
        }

        if (filter != null && filter.getMaxDate() != null){
            GroupSpecification maxDateSpecification = new GroupSpecification("createDate","<=",filter.getMaxDate());
            if (where == null){
                where = Specification.where(maxDateSpecification);
            }else {
                where = where.and(maxDateSpecification);
            }
        }

        return groupRepository.findAll(where,pageable);
    }

    @Override
    public Group getGroupByID(short id) {
        return groupRepository.findById(id).get();
    }

    @Override
    public Group getGroupByName(String name) {
        return groupRepository.findByName(name);
    }

    @Override
    public void createGroup(GroupFormForCreating form) {
        //Convert form --> Entity
        //get author
        Account creator = accountRepository.findById(form.getCreatorID()).get();

        Group group = new Group(form.getName());
        group.setCreator(creator);

        groupRepository.save(group);
    }

    @Override
    public void updateGroup(short id, GroupFormForUpdating form) {
        Group group = getGroupByID(id);
        group.setName(form.getName());
        group.setModifiedDate(new Date());//While update, modifiedDate is update is now
        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(short id) {
        groupRepository.deleteById(id);
    }

    @Override
    public void deleteGroups(List<Short> ids) {
        groupRepository.deleteByIds(ids);
    }

    @Override
    public boolean isGroupExistsByID(short id) {
        return groupRepository.existsById(id);
    }

    @Override
    public boolean isGroupExistsByName(String name) {
        return groupRepository.existsByName(name);
    }
}
