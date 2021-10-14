$(function(){
    var isRememberMe = storage.getRememberMe();
    document.getElementById("remember-me").checked = isRememberMe;
})

function handleKeyupEventForLogin(event){
    //Number 13 is the "Enter" key on the keyboard
    if(event.keyCode === 13){
        event.preventDefault();
        login();
    }
}

function login(){
    //get username & password
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    //Validate
    if(!username){
        showNameErrorMessage("Please input username!");
        return;
    }

    if(!password){
        showNameErrorMessage("Please input password!");
        return;
    }

    // validate username 6 -> 30 characters
    if (username.length < 6 || username.length > 50 || password.length < 6 || password.length > 50) {
        // show error message
        showNameErrorMessage("Login fail!");
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/login',
        type: 'POST',
        data:{
            username:username,
            password:password
        },
        success: function(data, textStatus, xhr) {
            console.log(data);
            // save remember me
            var isRememberMe = document.getElementById("remember-me").checked;
            storage.saveRememberMe(isRememberMe);
            // //save data to storage
            storage.setItem("ID", data.id);
            storage.setItem("FULL_NAME", data.fullName);
            storage.setItem("ROLE", data.role);
            storage.setItem("TOKEN", data.token);
            storage.setItem("REFRESH_TOKEN", data.refreshToken);
            storage.setItem("STATUS", data.status);
            //redirect to home page
            window.location.replace("http://127.0.0.1:5502/html/index.html")
        },
        error(jqXHR, textStatus, errorThrown){
            if(jqXHR.status == 401){
                showNameErrorMessage("Login fail!")
            }else{
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        }
    });
}

function showNameErrorMessage(message){
    document.getElementById("nameErrorMessage").style.display ="block"
    document.getElementById("nameErrorMessage").innerHTML = message;
}

function hideNameErrorMessage(){
    document.getElementById("nameErrorMessage").style.display ="none"
}