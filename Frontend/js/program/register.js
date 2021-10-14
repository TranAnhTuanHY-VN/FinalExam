function register(){
    //get username & password
    var firstName = document.getElementById("firstName").value;
    var username = document.getElementById("username").value;
    var lastName = document.getElementById("lastName").value;
    var email = document.getElementById("inputEmail").value;
    var password = document.getElementById("inputPassword").value;
    var repeatPassword = document.getElementById("repeatPassword").value;


    //Validate
    if(!username){
        showNameErrorMessage("Please input username!");
        return;
    }

    if(!firstName){
        showNameErrorMessage("Please input first name!");
        return;
    }

    if(!lastName){
        showNameErrorMessage("Please input last name!");
        return;
    }

    if(!email){
        showNameErrorMessage("Please input email!");
        return;
    }

    if(!password){
        showNameErrorMessage("Please input password!");
        return;
    }

    if(!repeatPassword){
        showNameErrorMessage("Please input repeatPassword!");
        return;
    }

    if(password!==repeatPassword){
        showNameErrorMessage("Passwords are not the same! Please re-enter!")
    }

    // validate username 6 -> 30 characters
    if (username.length < 6 || username.length > 50) {
        // show error message
        showNameErrorMessage("Length of username about 6-50 character!");
        return;
    }

    if (password.length < 6 || password.length > 50) {
        // show error message
        showNameErrorMessage("Length of password about 6-50 character!");
        return;
    }

    if(!validateEmail(email)){
        // show error message
        showNameErrorMessage("Email Incorrect format!");
        return;
    }

    var account = {
        username:username,
        email:email,
        password:password,
        firstName: firstName,
        lastName: lastName,
        role: "User"//Default role is User
    }

    $.ajax({
        url: 'http://localhost:8080/api/v1/accounts',
        type: 'POST',
        data: JSON.stringify(account),//body
        contentType: "application/json",//type of body
        success: function(data, textStatus, xhr) {
            console.log(data);
            
            //redirect to home page
            window.location.replace("http://127.0.0.1:5502/html/login.html")
        },
        error(jqXHR, textStatus, errorThrown){
            if(jqXHR.status == 401){
                showNameErrorMessage("Register fail!")
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

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}