var username = document.getElementById("username");
var password = document.getElementById("password");
var message = document.getElementById("validation-message");
var btnReg = document.querySelector(".btnSubmit ");

btnReg.addEventListener("click",(e)=>{
    e.preventDefault();
    console.log("test");
    console.log(username.value + password.value);
    loginForm(username.value,password.value);
})

function loginForm(username,password){
    fetch('/login',{
        method:'POST',
        headers:{
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: new URLSearchParams({
            username:username,
            password:password
        })
    })
    .then(response => response.text())
    .then(data => {
        console.log('Server Response:', data);
        responseValidation(data);
    })
}

function responseValidation(data) {
    try {
        const response = JSON.parse(data);
          if(response.status === 'success'){
               message.textContent = response.message;
          }else if(response.status === 'failed'){
               message.textContent = response.message
          }else{
                message.textContent = "Invalid Inputs";
          }
    } catch (e) {
        console.error('Invalid JSON from server:', data);
        message.textContent = "Unexpected server response.";
    }
}

