var groups =[];//use for storage data of Group
var currentPage = 1;//use for paging
var sizePage = 5;//use for paging
// Default to sort by field
var sortField = "modifiedDate";//use for sorting
var isAsc = false;//use for sorting
var minCreateDate ="";//use for filtering
var maxCreateDate ="";//use for filtering
var oldName;//use for updating

$(function(){
    if(!isLogin()){
        //redirect to login page
        window.location.replace("http://127.0.0.1:5502/html/login.html");
    }
    document.getElementById("fullName").innerHTML = storage.getItem("FULL_NAME");
    if(storage.getItem("ROLE") == "User"){
        document.getElementById("viewListAccount").style.display = "none";
        document.getElementById("viewListGroup").style.display = "none";
    }
});

function clickHomePage(){
    $(".container-fluid").load("home.html");
}

function clickAccountManagement(){
    $(".container-fluid").load("tables.html");

}

function clickGroupManagement(){
    $(".container-fluid").load("groupList.html",function(){
        buildTable();
    });
}

function isLogin(){
    if(storage.getItem("ID")){
        return true;
    }
    return false;
}

function logout(){
    storage.removeItem("ID");
    storage.removeItem("FULL_NAME");
    storage.removeItem("USERNAME");
    storage.removeItem("PASSWORD");

    //redirect to login page
    window.location.replace("http://127.0.0.1:5502/html/login.html");
}

function openModal(){
    $('#myModal').modal('show');
}

function hideModal(){
    $('#myModal').modal('hide');
}

function buildTable(){
    $('tbody').empty();
    getListGroups();
}

function getListGroups() {

    var URL = 'http://localhost:8080/api/v1/groups';

    URL += '?page=' + currentPage + '&size = ' + sizePage;
    URL += '&sort=' + sortField + ',' +(isAsc ? "asc" : "desc"); 
    
    var search = document.getElementById("input-search-groups").value;
    if(search){
        URL += "&search=" + search;
    }


    if (minCreateDate){
        URL += "&minDate=" +minCreateDate;
    }

    if (maxCreateDate){
        URL += "&maxDate=" + maxCreateDate;
    }
    // call API from server
    $.ajax({
        url: URL,
        type: 'GET',
        contentType: "application/json",//type of body(json, xml ,text)
        dataType: 'json',//datatype return
        beforeSend: function (xhr){
            console.log(storage.getItem("TOKEN"))
            xhr.setRequestHeader("Authorization", "Bearer " + storage.getItem("TOKEN"));        
        },
        success: function(data, textStatus, xhr) {
             // reset list employees
            groups = [];

            // success
            groups = data.content;
            fillGroupToTable();
            resetDeleteCheckbox();
            pagingTable(data.totalPages);
            renderSortUI();
        },
        error(jqXHR, textStatus, errorThrown){
            if(jqXHR.status == 403){
                window.location.href = "http://127.0.0.1:5502/html/forbidden.html";
            }
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }        
    });
}

function pagingTable(pageAmount){
    
    var pagingStr = "";

    if(pageAmount > 1&&currentPage > 1){
        pagingStr += 
        '<li class="page-item">' +
            '<a class="page-link" onclick="prevPaging()">Previous</a>' + 
        '</li>';
    }

    for (let i = 0; i < pageAmount; i++) {
        pagingStr += 
        '<li class="page-item '+ (currentPage==(i+1)?'active':'') + '">' + 
            '<a class="page-link" onclick="changePage(' + (i+1) + ')">'+ ( i + 1 ) +'</a>' + 
        '</li>';        
    }

    if(pageAmount > 1 && currentPage<pageAmount){
        pagingStr += 
        '<li class="page-item">' + 
            '<a class="page-link" onclick="nextPaging()">Next</a>' + 
        '</li>';
    }

    $('#pagination').empty();
    $('#pagination').append(pagingStr);
}

function resetPaging(){
    currentPage =1;
    sizePage = 5;
}

function prevPaging(){
    changePage(currentPage -1);
}

function nextPaging(){
    changePage(currentPage +1);
}

function changePage(page){
    if(page == currentPage){
        return;
    }
    currentPage = page;
    buildTable();
}

function renderSortUI(){
    var sortTypeClass = isAsc ? "fa-sort-asc" : "fa-sort-desc";

    switch (sortField) {
        case 'Name':
            changeIconSort("heading-name",sortTypeClass);
            changeIconSort("heading-creator","fa-sort");
            changeIconSort("heading-createDate","fa-sort");
            changeIconSort("heading-members","fa-sort");
            break;
        case 'memberNum':
            changeIconSort("heading-members",sortTypeClass);
            changeIconSort("heading-creator","fa-sort");
            changeIconSort("heading-createDate","fa-sort");
            changeIconSort("heading-name","fa-sort");
            break;
        case 'creator.fullName':
            changeIconSort("heading-creator",sortTypeClass);
            changeIconSort("heading-name","fa-sort");
            changeIconSort("heading-createDate","fa-sort");
            changeIconSort("heading-members","fa-sort");
            break;
        case 'createDate':
            changeIconSort("heading-createDate",sortTypeClass);
            changeIconSort("heading-name","fa-sort");
            changeIconSort("heading-creator","fa-sort");
            changeIconSort("heading-members","fa-sort");
            break;
        default:
            changeIconSort("heading-name","fa-sort");
            changeIconSort("heading-creator","fa-sort");
            changeIconSort("heading-createDate","fa-sort");
            changeIconSort("heading-members","fa-sort");
            break;
    }
}

function changeIconSort(id,sortTypeClass){
    document.getElementById(id).classList.remove("fa-sort","fa-sort-asc","fa-sort-desc")
    document.getElementById(id).classList.add(sortTypeClass);
}

function changeSort(field){
    if(field == sortField){
        isAsc = !isAsc;
    }else{
        sortField = field;
        isAsc = true;
    }
    buildTable();
}

function resetSort(){
    sortField = "modifiedDate";
    isAsc = false;
}

function resetDeleteCheckbox(){
    //reset delete all checkbox
    document.getElementById("checkbox-all").checked = false;
    //reset item
    let i =0;
    while(true){
        let checkboxItem = document.getElementById("checkbox-" + i);
        if(checkboxItem!==undefined && checkboxItem !== null){
            checkboxItem.checked = false;
            i++;
        }else{
            break;
        }
    }
}

function resetSearch(){
    document.getElementById("input-search-groups").value = "";
}

function resetTable(){
    resetPaging();
    resetSort();
    resetSearch();
    resetFilter();
    resetDeleteCheckbox();
}

function handleKeyupEventForSearching(event){
    //Number 13 is the "Enter" key on the keyboard
    if(event.keyCode === 13){
        event.preventDefault();
        handleSearchGroup();
    }
}

function handleSearchGroup(){
    resetPaging();
    resetSort();
    resetDeleteCheckbox();
    buildTable();
}

function changeMinCreateDate(event){
    minCreateDate = event.target.value;
    resetPaging();
    resetSort();
    resetDeleteCheckbox();
    buildTable();
}

function changeMaxCreateDate(event){
    maxCreateDate  = event.target.value;
    resetPaging();
    resetSort();
    resetDeleteCheckbox();
    buildTable();
}

function resetFilter(){
    minCreateDate = "";
    maxCreateDate = "";
    document.getElementById("minCreateDate").value = "";
    document.getElementById("maxCreateDate").value = "";
}

function refreshTable(){
    resetTable();
    buildTable();
}

function fillGroupToTable() {
    groups.forEach(function(item,index) {
        $('tbody').append(
            '<tr>' +
                '<td> <input type="checkbox" id = "checkbox-'+ index +'" onclick ="onChangeCheckboxItem()"> </td>' +
                '<td>' + (index+1) + '</td>' +
                '<td>' + item.name + '</td>' +
                '<td>' + item.memberNum + '</td>' +
                '<td>' + item.creator.fullName + '</td>' +
                '<td>' + item.createDate + '</td>' +
                '<td>' +
                    '<a class="edit" title="Edit" data-toggle="tooltip" onclick="openUpdateModal(' + item.id + ')"><i class="fas fa-pencil-alt"></i></i></a>' +
                    '<a class="delete" title="Delete" data-toggle="tooltip" onClick="openConfirmDelete(' + item.id + ')"><i class="far fa-trash-alt"></a>' +
                '</td>' +
            '</tr>')
    });
}

function openAddModal(){
    openModal();
    resetFormAdd();
}

function resetFormAdd(){
    document.getElementById("modal-title").innerHTML = "Add Group";
    document.getElementById("id").value = "";
    document.getElementById("name").value = "";
    document.getElementById("creator").style.display ="none";
    document.getElementById("creatorLabel").style.display ="none";
    document.getElementById("createDate").style.display ="none";
    document.getElementById("createDateLabel").style.display ="none";
    hideNameErrorMessage();
}

function showNameErrorMessage(message){
    document.getElementById("nameErrorMessage").style.display ="block"
    document.getElementById("nameErrorMessage").innerHTML = message;
}

function hideNameErrorMessage(){
    document.getElementById("nameErrorMessage").style.display ="none"
}

function addGroup(){
    
    var name = document.getElementById("name").value;
    
    //VALIDATE
    //Validate name 6-30 characters
    if(!name||name.length<6||name.length>30){
        //Show error message
        showNameErrorMessage("Group name must be from 6 - 30 characters!");
        return;
    }

    //Validate unique name
    $.ajax({
        url: "http://localhost:8080/api/v1/groups/name/" + name +"/exists",
        type: 'GET',
        contentType: "application/json",//type of body(json, xml ,text)
        dataType: 'json',//datatype return
        beforeSend: function (xhr){
            xhr.setRequestHeader("Authorization", "Bearer " + storage.getItem("TOKEN"));        
        },
        success: function(data, textStatus, xhr) {
             if(data==true){
                showNameErrorMessage("Group name is exists!");
            }else{
                //Call API create Group
                var group = {
                    name: name,
                    creatorID: storage.getItem("ID")
                };
                $.ajax({
                    url: 'http://localhost:8080/api/v1/groups',
                    type: 'POST',
                    data: JSON.stringify(group),//body
                    contentType: "application/json",//type of body
                    //datatype: json, datatype return
                    beforeSend: function (xhr){
                        xhr.setRequestHeader("Authorization", "Bearer " + storage.getItem("TOKEN"));
                    },
                    success: function(data,textStatus,xhr) {
                        console.log(data);
                        // success
                        hideModal();
                        showSuccessAlert();
                        resetTable();
                        buildTable();
                    },
                    error(jqXHR, textStatus, errorThrown){
                        alert("Error when loading data");
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                    }
                });
            }
        },
        error(jqXHR, textStatus, errorThrown){
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }        
    });
}

function openUpdateModal(id){
    $.ajax({
        url: "http://localhost:8080/api/v1/groups/" + id,
        type: 'GET',
        contentType: "application/json",//type of body(json, xml ,text)
        dataType: 'json',//datatype return
        beforeSend: function (xhr){
            xhr.setRequestHeader("Authorization","Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function(data, textStatus, xhr) {
            // success
            openModal();
            resetFormUpdate();

            oldName = data.name;
            //fill data
            document.getElementById("id").value = data.id;
            document.getElementById("name").value = data.name;
            document.getElementById("creator").value = data.creator.fullName;
            document.getElementById("createDate").value = data.createDate;
        },
        error(jqXHR, textStatus, errorThrown){
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }        
    });
}


function resetFormUpdate(){
    document.getElementById("modal-title").innerHTML = "Update Group";
    document.getElementById("creator").style.display ="block";
    document.getElementById("creatorLabel").style.display ="block";
    document.getElementById("createDate").style.display ="block";
    document.getElementById("createDateLabel").style.display ="block";
    hideNameErrorMessage();
}

function save(){
    var id = document.getElementById("id").value;

    if(id == null || id ==""){
        addGroup();
    }else{
        updateGroup();
    }
}

function updateGroup(){
    var id = document.getElementById("id").value;
    var name = document.getElementById("name").value;

    // VALIDATE
    //Validate name 6-30 characters
    if(!name||name.length<6||name.length>30){
        //Show error message
        showNameErrorMessage("Group name must be from 6 - 30 characters!");
        return;
    }

    //Validate unique name
    if(name === oldName){
        // success
        hideModal();
        showSuccessAlert();
        resetTable();
        buildTable();
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/groups/name/" + name +"/exists",
        type: 'GET',
        contentType: "application/json",//type of body(json, xml ,text)
        dataType: 'json',//datatype return
        beforeSend: function (xhr){
            xhr.setRequestHeader("Authorization","Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function(data, textStatus, xhr) {
            if(data==true){
                showNameErrorMessage("Group name is exists!");
            }else{
                var group = {
                    name: name
                };
            
                $.ajax({
                    url: 'http://localhost:8080/api/v1/groups/' + id,
                    type: 'PUT',
                    data: JSON.stringify(group),//body
                    contentType: "application/json",//type of body(json, xml ,text)
                    //datatype "json",//datatype return
                    beforeSend: function (xhr){
                        xhr.setRequestHeader("Authorization","Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
                    },
                    success: function(data, textStatus, xhr) {
                        console.log(data);
            
                        // success
                        hideModal();
                        showSuccessAlert();
                        resetTable();
                        buildTable();
                    },
                    error(jqXHR, textStatus, errorThrown){
                        alert("Error when loading data");
                        console.log(jqXHR);
                        console.log(textStatus);
                        console.log(errorThrown);
                    }
                });
            }
        },
        error(jqXHR, textStatus, errorThrown){
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown);
        }        
    });
}

function openConfirmDelete(id){
    // get index from Group's id
    var index = groups.findIndex(x => x.id == id);
    var name = groups[index].name;
    
    var result = confirm("Want to delete " + name + "?");
    if (result) {
        deleteGroup(id);
    }
}

function deleteGroup(id){
    // TODO validate


    // showSuccessAlert();
    // buildTable();
    $.ajax({
        url: 'http://localhost:8080/api/v1/groups/' + id,
        type: 'DELETE',
        beforeSend: function (xhr){
            xhr.setRequestHeader("Authorization","Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
        },
        success: function(result) {

            // success
            showSuccessAlert();
            resetTable();
            buildTable();
        }
    });
}

function onChangeCheckboxItem(){
    let i =0;
    while(true){
        let checkboxItem = document.getElementById("checkbox-" + i);
        if(checkboxItem!==undefined && checkboxItem !== null){
            if(!checkboxItem.checked){//Co item chua checked thi set lai check-all
                document.getElementById("checkbox-all").checked = false;
                return;
            }
            i++;
        }else{
            break;
        }
    }
    //tat ca checked thi set lai check all 
    document.getElementById("checkbox-all").checked = true;
}

function onChangeCheckboxAll(){
    let i =0;
    while(true){
        let checkboxItem = document.getElementById("checkbox-" + i);
        if(checkboxItem!==undefined && checkboxItem !== null){
            checkboxItem.checked = document.getElementById("checkbox-all").checked
            // if(document.getElementById("checkbox-all").checked){
            //     checkboxItem.checked = true;
            // }else{
            //     checkboxItem.checked = false;
            // }
            i++;
        }else{
            break;
        }
    }
}

function deleteAllGroup(){
    //get checked
    let ids = [];
    let names = [];

    var i =0;
    while(true){
        let checkboxItem = document.getElementById("checkbox-" + i);
        if(checkboxItem!==undefined && checkboxItem !== null){
            if(checkboxItem.checked){
                ids.push(groups[i].id);
                names.push(groups[i].name);
            }
            i++;
        }else{
            break;
        }
    }

    //Open confirm   ==> Xac nhan xoa
    var result = confirm("Want to delete " + names + "?");
    if (result) {
        //Call API
        $.ajax({
            url: 'http://localhost:8080/api/v1/groups?ids=' + ids,
            type: 'DELETE',
            beforeSend: function (xhr){
                xhr.setRequestHeader("Authorization","Basic " + btoa(storage.getItem("USERNAME") + ":" + storage.getItem("PASSWORD")));
            },
            success: function(result) {
                // success
                showSuccessAlert();
                resetTable();
                buildTable();
            }
        });
    }
}

function showSuccessAlert() {
    $("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
        $("#success-alert").slideUp(500);
    });
}