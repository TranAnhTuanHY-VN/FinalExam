var storage = {
    saveRememberMe(isRememberMe){
        localStorage.setItem("IS_REMEMBER_ME",isRememberMe);
    },

    getRememberMe(){
        var rememberMeStr = localStorage.getItem("IS_REMEMBER_ME");
        if(rememberMeStr == null){
            return true;
        }
        return JSON.parse(rememberMeStr.toLowerCase());
    },

    setItem(key,value){
        (this.getRememberMe())?localStorage.setItem(key,value):sessionStorage.setItem(key,value);
    },
    
    getItem(key){
        return (this.getRememberMe())?localStorage.getItem(key):sessionStorage.getItem(key);
    },

    removeItem(key){
        return (this.getRememberMe())?localStorage.removeItem(key):sessionStorage.removeItem(key);
    }
}